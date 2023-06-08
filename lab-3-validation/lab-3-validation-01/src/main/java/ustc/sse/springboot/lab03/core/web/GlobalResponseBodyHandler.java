package ustc.sse.springboot.lab03.core.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import ustc.sse.springboot.lab03.core.vo.CommonResult;

/**
 * 全局统一返回处理器
 * @author MonoSirius
 * @date 2023/5/31
 */

/**
 * 因为在项目中，我们可能会引入 Swagger 等库，
 * 也使用 Controller 提供 API 接口，
 * 那么我们显然不应该让 GlobalResponseBodyHandler 去拦截这些接口，
 * 毕竟它们并不需要我们去替它们做全局统一的返回。
 */
@ControllerAdvice(basePackages = "ustc.sse.springboot.lab03.controller")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 拦截所有返回结果
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果包装好了就不用进行二次包装
        if (body instanceof CommonResult) {
            return body;
        }
        // 如果不是则进行包装
        return CommonResult.ok(body);
    }
}
