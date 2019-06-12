#!/bin/bash
cd `dirname $0`

img_mvn="maven:3.3.3-jdk-8"                 # docker image of maven
m2_cache=~/.m2                              # the local maven cache dir
proj_home=$PWD                              # the project root dir
img_output="deepexi/deep-exi-01"         # output image tag

git pull  # should use git clone https://name:pwd@xxx.git

echo "use docker maven"
docker run --rm \
   -v $m2_cache:/root/.m2 \
   -v $proj_home:/usr/src/mymaven \
   -w /usr/src/mymaven $img_mvn mvn clean package -U

sudo mv $proj_home/deep-exi-01-provider/target/deep-exi-01-provider-*.jar $proj_home/deep-exi-01-provider/target/demo.jar # 兼容所有sh脚本
docker build -t $img_output .

mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 删除容器
docker rm -f deep-exi-01 &> /dev/null

version=`date "+%Y%m%d%H"`

spring_datasource_url=jdbc:mysql://localhost:3306/deep-exi-01?useUnicode=true\&characterEncoding=utf-8\&useSSL=false

# 启动镜像
docker run -d --restart=on-failure:5 --privileged=true \
    --net=host \
    -w /home \
    -v $PWD/logs:/home/logs \
    --name deep-exi-01 deepexi/deep-exi-01 \
    java \
        -Djava.security.egd=file:/dev/./urandom \
        -Duser.timezone=Asia/Shanghai \
        -XX:+PrintGCDateStamps \
        -XX:+PrintGCTimeStamps \
        -XX:+PrintGCDetails \
        -XX:+HeapDumpOnOutOfMemoryError \
        -Xloggc:logs/gc_$version.log \
        -jar /home/demo.jar \
          --spring.profiles.active=prod \
          --spring.datasource.url=$spring_datasource_url \
          --spring.datasource.username=root \
          --spring.datasource.password=my-secret-ab \
          --dubbo.registry.address=zookeeper://127.0.0.1:2181