package ustc.sse.springboot.lab02.core.exception;

import ustc.sse.springboot.lab02.constants.ServiceExceptionEnum;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */
public final class ServiceException extends RuntimeException {
    private final Integer code;

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum) {

        // 使用 父类msg字段
        super(serviceExceptionEnum.getMsg());
        // 设置错误码
        this.code = serviceExceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
