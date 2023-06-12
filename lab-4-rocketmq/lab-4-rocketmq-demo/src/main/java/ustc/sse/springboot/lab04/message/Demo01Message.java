package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/8
 */
public class Demo01Message {
    public static final String TOPIC = "DEMO_01";

    /**
     * 编号
     */
    private Integer id;

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }

    public Demo01Message(Integer id) {
        this.id = id;
    }

    public Demo01Message() {
    }

    public Integer getId() {
        return id;
    }

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }
}
