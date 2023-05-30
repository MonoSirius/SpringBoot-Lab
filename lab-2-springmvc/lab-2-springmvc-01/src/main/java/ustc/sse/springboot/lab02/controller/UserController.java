package ustc.sse.springboot.lab02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ustc.sse.springboot.lab02.dto.UserAddDTO;
import ustc.sse.springboot.lab02.dto.UserUpdateDTO;
import ustc.sse.springboot.lab02.service.UserService;
import ustc.sse.springboot.lab02.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MonoSirius
 * @date 2023/5/30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    // 通过构造函数注入
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    @GetMapping("")
    public List<UserVO> list() {
        // 查询列表
        List<UserVO> result = new ArrayList<>();
        result.add(new UserVO().setId(1).setUsername("yudaoyuanma"));
        result.add(new UserVO().setId(2).setUsername("woshiyutou"));
        result.add(new UserVO().setId(3).setUsername("chifanshuijiao"));
        // 返回列表
        return result;
    }

    /**
     * 获得指定用户编号的用户
     *
     * @param id 用户编号
     * @return 用户
     */
    @GetMapping("/{id}")
    public UserVO get(@PathVariable("id") Integer id) {
        // 查询并返回用户
        return new UserVO().setId(id).setUsername("username:" + id);
    }


    /**
     * 获得指定用户编号的用户
     *
     * @param id 用户编号
     * @return 用户
     */
    @GetMapping("/v2/{id}")
    public UserVO get2(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    /**
     * 添加用户
     *
     * @param addDTO 添加用户信息 DTO
     * @return 添加成功的用户编号
     */
    @PostMapping("")
    public Integer add(UserAddDTO addDTO) {
        // 插入用户记录，返回编号
        Integer returnId = 1;
        // 返回用户编号
        return returnId;
    }

    /**
     * 更新指定用户编号的用户
     *
     * @param id 用户编号
     * @param updateDTO 更新用户信息 DTO
     * @return 是否修改成功
     */
    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Integer id, UserUpdateDTO updateDTO) {
        // 将 id 设置到 updateDTO 中
        updateDTO.setId(id);
        // 更新用户记录
        Boolean success = true;
        // 返回更新是否成功
        return success;
    }

    /**
     * 删除指定用户编号的用户
     *
     * @param id 用户编号
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Integer id) {
        // 删除用户记录
        Boolean success = false;
        // 返回是否更新成功
        return success;
    }
}
