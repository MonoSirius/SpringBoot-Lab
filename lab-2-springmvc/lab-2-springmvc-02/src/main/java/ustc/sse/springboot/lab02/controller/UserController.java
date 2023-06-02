package ustc.sse.springboot.lab02.controller;

import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ustc.sse.springboot.lab02.constants.ServiceExceptionEnum;
import ustc.sse.springboot.lab02.core.exception.ServiceException;
import ustc.sse.springboot.lab02.core.vo.CommonResult;
import ustc.sse.springboot.lab02.vo.UserVO;

import java.io.Serializable;
import java.util.UUID;

import static ustc.sse.springboot.lab02.constants.ServiceExceptionEnum.USER_NOT_FOUND_ERROR;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */
@RestController
@RequestMapping("/user")
public class UserController implements Serializable {

    /**
     * 获得指定用户编号的用户
     *
     * 提供不使用 CommonResult 包装
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public UserVO get(@RequestParam("id") Integer id) {
        return new UserVO().setId(id).setUsername(UUID.randomUUID().toString());
    }

    /**
     * 获得指定用户编号的用户
     *
     * 提供使用 CommonResult 包装
     * @param id
     * @return
     */
    @GetMapping("get2")
    public CommonResult<UserVO> get2(@RequestParam("id") Integer id) {
        UserVO userVO = new UserVO().setId(id).setUsername(UUID.randomUUID().toString());

        return CommonResult.ok(userVO);
    }

    /**
     * 测试抛出 NullPointerException 异常
     */
    @GetMapping("/exception-01")
    public UserVO exception01() {
        throw new NullPointerException("没有粗面鱼丸");
    }

    /**
     * 测试抛出 ServiceException 异常
     */
    @GetMapping("/exception-02")
    public UserVO exception02() {
        throw new ServiceException(USER_NOT_FOUND_ERROR);
    }

    @PostMapping(value = "/add",
            // ↓ 增加 "application/xml"、"application/json" ，针对 Content-Type 请求头
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            // ↓ 增加 "application/xml"、"application/json" ，针对 Accept 请求头
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserVO add(@RequestBody UserVO user) {
        return user;
    }
}
