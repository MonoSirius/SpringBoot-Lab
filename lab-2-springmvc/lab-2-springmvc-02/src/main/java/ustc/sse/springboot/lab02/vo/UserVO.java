package ustc.sse.springboot.lab02.vo;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */
public class UserVO {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;



    public UserVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserVO setUsername(String username) {
        this.username = username;
        return this;
    }
}
