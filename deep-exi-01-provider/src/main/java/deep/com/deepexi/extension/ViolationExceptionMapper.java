package deep.com.deepexi.extension;

import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * Created by donh on 2019/2/20.
 * https://stackoverflow.com/questions/32725378/wildfly-exceptionmapper-not-triggered-with-resteasy-jsr-303-bean-validation?rq=1
 * 覆盖resteasy内置的异常映射器，将默认的ViolationReport输出为自定义格式
 */
@Component
@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {

    @Override
    public Response toResponse(ResteasyViolationException e) {
        // 获取校验失败的反馈信息
        String message = "参数不正确";
        for (List<ResteasyConstraintViolation> list : e.getViolationLists()) {
            if (list.size() == 0) {
                continue;
            }
            ResteasyConstraintViolation constraintViolation = list.get(0);
            message = constraintViolation.getMessage();
            break;
        }
        return Response.status(Response.Status.OK).entity(new Payload(null, "400", message)).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}