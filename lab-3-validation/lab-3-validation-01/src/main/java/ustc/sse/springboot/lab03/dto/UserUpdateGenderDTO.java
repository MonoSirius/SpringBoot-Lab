package ustc.sse.springboot.lab03.dto;

import ustc.sse.springboot.lab03.constants.GenderEnum;
import ustc.sse.springboot.lab03.core.validator.InEnum;

import javax.validation.constraints.NotNull;

/**
 * @author MonoSirius
 * @date 2023/6/8
 */
public class UserUpdateGenderDTO {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Integer id;

    /**
     * 性别
     */
    @NotNull(message = "用户性别不能为空")
    @InEnum(value = GenderEnum.class, message = "性别必须是 {value}")
    private Integer gender;

    public Integer getId() {
        return id;
    }

    public UserUpdateGenderDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public UserUpdateGenderDTO setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public UserUpdateGenderDTO(Integer id, Integer gender) {
        this.id = id;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserUpdateGenderDTO{" +
                "id=" + id +
                ", gender=" + gender +
                '}';
    }
}
