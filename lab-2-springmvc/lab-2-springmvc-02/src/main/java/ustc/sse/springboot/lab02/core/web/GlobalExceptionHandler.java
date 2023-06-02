package ustc.sse.springboot.lab02.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ustc.sse.springboot.lab02.core.exception.ServiceException;
import ustc.sse.springboot.lab02.core.vo.CommonResult;

import javax.servlet.http.HttpServletRequest;

import static ustc.sse.springboot.lab02.constants.ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR;
import static ustc.sse.springboot.lab02.constants.ServiceExceptionEnum.SYS_ERROR;

/**
 * @author MonoSirius
 * @date 2023/6/1
 */
@ControllerAdvice(basePackages = "ustc.sse.springboot.lab02.controller")
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理ServiceException异常
     * @param req 请求体
     * @param ex 拦截异常
     * @return 包装成CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        logger.debug("[ServiceExceptionHandler]", ex);
        return CommonResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理请求参数缺失异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterHandler(HttpServletRequest req, MissingServletRequestParameterException ex) {
        logger.debug("[missingServletRequestParameterHandler]", ex);

        return CommonResult.fail(
                MISSING_REQUEST_PARAM_ERROR.getCode(),
                MISSING_REQUEST_PARAM_ERROR.getMsg());
    }

    /**
     * 处理其他异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> exceptionHandler(HttpServletRequest req, Exception ex) {
        logger.debug("[exceptionHandler]", ex);

        //
        return CommonResult.fail(SYS_ERROR.getCode(), SYS_ERROR.getMsg());
    }
}
