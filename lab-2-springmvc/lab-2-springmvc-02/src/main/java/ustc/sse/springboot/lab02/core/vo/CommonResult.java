package ustc.sse.springboot.lab02.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */
public class CommonResult<T> implements Serializable {
    public static final Integer CODE_OK = 200;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 对result进行 泛型转换
     * @param result 源泛型result
     * @return 目标泛型result
     * @param <T> 目标泛型
     */
    public static <T> CommonResult<T> fail(CommonResult<?> result) {
        return fail(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> ok(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(CODE_OK);
        result.setData(data);
        result.setMsg("");
        return result;
    }

    public static <T> CommonResult<T> fail(Integer code, String msg) {
        Assert.isTrue(!CODE_OK.equals(code), "code必须是错误的!");
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    @JsonIgnore
    public boolean isOk() {
        return CODE_OK.equals(code);
    }

    @JsonIgnore // 避免Jackson序列化给前端
    public boolean isFail() {
        return !isOk();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
