package ustc.sse.springboot.lab02.constants;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */
public enum ServiceExceptionEnum {
    // ======== System ========
    OK(200, "成功"),
    SYS_ERROR(2001001000, "服务端发生异常"),
    MISSING_REQUEST_PARAM_ERROR(2001001001, "参数缺失"),

    // ======== User ========
    USER_NOT_FOUND_ERROR(1001002000, "用户不存在"),

    // ======== Order ========

    // ======== Product ========

    ;

    /**
     * 服务异常
     *
     * 参考 https://www.kancloud.cn/onebase/ob/484204 文章
     *
     * 一共 10 位，分成四段
     *
     * 第一段，1 位，类型
     *      1 - 业务级别异常
     *      2 - 系统级别异常
     * 第二段，3 位，系统类型
     *      001 - 用户系统
     *      002 - 商品系统
     *      003 - 订单系统
     *      004 - 支付系统
     *      005 - 优惠劵系统
     *      ... - ...
     * 第三段，3 位，模块
     *      不限制规则。
     *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
     *          001 - OAuth2 模块
     *          002 - User 模块
     *          003 - MobileCode 模块
     * 第四段，3 位，错误码
     *       不限制规则。
     *       一般建议，每个模块自增。
     */

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误提示
     */
    private String msg;
    ServiceExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public ServiceExceptionEnum setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ServiceExceptionEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
