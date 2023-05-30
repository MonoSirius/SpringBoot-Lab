package ustc.sse.springboot.lab02.dto;

/**
 * @author MonoSirius
 * @date 2023/5/30
 */
public class UserAddDTO {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public UserAddDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAddDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
