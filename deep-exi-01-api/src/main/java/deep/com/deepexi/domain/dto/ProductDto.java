package deep.com.deepexi.domain.dto;

import javax.validation.constraints.*;
import javax.ws.rs.QueryParam;

/**
 * Created by donh on 2019/2/18.
 */
public class ProductDto {

    @NotEmpty(message = "商品名称不能为空") //名字不能为空，而且长度必须在2和30之间
    @Size(min=2, max=30, message = "商品名长度必须在2和30之间")
    @QueryParam("name")
    private String name;

    @NotNull(message = "商品类型不能为空")
    @QueryParam("type")
    private Integer type;

    /**
     * 这里没加@QueryParam，get请求绑定参数时，即使tag有值也会为null
     */
    private String tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}