package ustc.sse.springboot.labs.lab1.mapstruct_demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author MonoSirius
 * @date 2023/5/29
 */

@Data
@Accessors(chain = true)
public class User {

    /**
     * 编号
     */
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public User() {
    }
}
