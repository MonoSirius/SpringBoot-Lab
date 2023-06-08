package ustc.sse.springboot.lab03.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ustc.sse.springboot.lab03.dto.UserAddDTO;
import ustc.sse.springboot.lab03.dto.UserUpdateGenderDTO;
import ustc.sse.springboot.lab03.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author MonoSirius
 * @date 2023/6/6
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {


    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public void get(@RequestParam("id") @Min(value = 1L, message = "编号必须大于 0") Integer id) {
        logger.info("[get][id:{}]", id);
        userService.get(id);
    }

    /**
     * 嵌套校验 使用@Valid
     *
     * @param userAddDTO
     */
    @PostMapping("/add")
    public void add(@RequestBody @Valid UserAddDTO userAddDTO) {
        logger.info("[add][userAddDTO:{}]", userAddDTO);
        userService.add(userAddDTO);
    }

    @PostMapping("/update_gender")
    public void updateGender(@RequestBody @Valid UserUpdateGenderDTO updateGenderDTO) {
        logger.info("[updateGender][updateGenderDTO: {}]", updateGenderDTO);
    }
}
