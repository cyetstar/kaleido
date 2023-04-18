package cc.onelooker.kaleido.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.exception.BaseException;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.exception.SystemErrorCode;
import com.zjjcnt.common.core.exception.UserErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("all")
public class GlobalExceptionHandlerAdvice {

    /**
     * 上传文件太大异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResult maxUploadSizeExceededExceptionHandle(MaxUploadSizeExceededException ex) {
        log.warn("[maxUploadSizeExceededExceptionHandle]", ex);
        return CommonResult.error(20002, "文件超过上传限制").setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 Multipart 文件上传异常
     */
    @ExceptionHandler(MultipartException.class)
    public CommonResult multipartExceptionHandle(MultipartException ex) {
        log.warn("[multipartExceptionHandle]", ex);
        return CommonResult.error(20002, "文件超过上传限制").setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     * <p>
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public CommonResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数缺失:%s", ex.getParameterName())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     * <p>
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[methodArgumentTypeMismatchExceptionHandler]", ex);
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数类型错误:%s", ex.getMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResult serviceException(MethodArgumentNotValidException ex) {
        log.error("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        // 断言，避免告警
        assert fieldError != null;
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public CommonResult bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ValidationException.class)
    public CommonResult validationExceptionHandler(ValidationException ex) {
        log.warn("[validationExceptionHandler]", ex);
        return CommonResult.error(UserErrorCode.USER_REQUEST_PARAM_ERROR.getCode(), String.format("请求参数不正确:%s", ex.getMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     * <p>
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CommonResult.error(UserErrorCode.USER_ERROR.getCode(), String.format("请求方法不正确:%s", ex.getMessage())).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    /**
     * 处理业务异常 ServiceException
     * <p>
     * 例如说，用户手机号已存在。
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler], message=[{}]", ex.getMessage());
        return CommonResult.error(ex.getCode(), ex.getMessage()).setDetailMessage(ex.getDetailMessage());
    }

    /**
     * 处理基本异常 BaseException
     */
    @ExceptionHandler(value = BaseException.class)
    public CommonResult baseExceptionHandler(ServiceException ex) {
        log.info("[baseExceptionHandler], message=[{}]", ex.getMessage());
        return CommonResult.error(ex.getCode(), ex.getMessage()).setDetailMessage(ex.getDetailMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult defaultExceptionHandler(Exception ex) {
        long randomCode = RandomUtils.nextLong(100000, 999999);
        log.error("[defaultExceptionHandler][{}]", randomCode, ex);
        return CommonResult.error(SystemErrorCode.SYSTEM_ERROR).setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult throwableHandler(Throwable ex) {
        long randomCode = RandomUtils.nextLong(100000, 999999);
        log.error("[throwableHandler][{}]", randomCode, ex);
        return CommonResult.error(SystemErrorCode.SYSTEM_ERROR.getCode(), String.format("[%d] %s", randomCode, SystemErrorCode.SYSTEM_ERROR.getMessage())).setDetailMessage(ExceptionUtils.getRootCauseMessage(ex));
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return CommonResult.error(10002, "无权限访问").setDetailMessage(ExceptionUtil.getRootCauseMessage(e));
    }
}