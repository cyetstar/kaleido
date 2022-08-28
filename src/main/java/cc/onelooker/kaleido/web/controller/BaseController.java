package cc.onelooker.kaleido.web.controller;

import com.zjjcnt.common.core.dto.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author xiadawei
 * @Date 2021-08-12 14:22:00
 * @Description TODO
 */
public abstract class BaseController {

    protected String getUrlPrefix() {
        RequestMapping requestMapping = getClass().getAnnotation(RequestMapping.class);
        String urlPrefix = requestMapping != null && requestMapping.value().length > 0
                ? requestMapping.value()[0] : StringUtils.EMPTY;
        urlPrefix = StringUtils.removeStart(urlPrefix, "/");
        return urlPrefix;
    }

    protected String getViewPath(String operation) {
        Path path = Paths.get(getUrlPrefix(), getUrlPrefix() + StringUtils.capitalize(operation));
        return path.toString();
    }

    protected HttpEntity<CommonResult> getHttpEntity(HttpEntityHandler handler) {
        try {
            return handler.handle();
        } catch (Exception e) {
            CommonResult responseResultDTO = CommonResult.error(1, e.getMessage());
            ResponseEntity<CommonResult> responseEntity =
                    new ResponseEntity<>(responseResultDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
    }

    /**
     * 异步请求处理接口。
     */
    protected interface HttpEntityHandler {
        /**
         * 处理岂不请求的业务逻辑。
         *
         * @return 返回请求结果
         * @throws Exception Exception
         */
        HttpEntity<CommonResult> handle() throws Exception;
    }
}
