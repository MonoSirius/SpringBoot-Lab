package ustc.sse.springboot.lab02.vo;

/**
 * @author MonoSirius
 * @date 2023/5/30
 */
public class UserVO {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;

    public Integer getId() {
        return id;
    }

    public UserVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserVO setUsername(String username) {
        this.username = username;
        return this;
    }

}
