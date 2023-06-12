package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/10
 */
public class Demo02Message {
    public static final String TOPIC = "DEMO_02";

    private Integer id;

    public Demo02Message(Integer id) {
        this.id = id;
    }

    public Demo02Message() {
    }

    @Override
    public String toString() {
        return "Demo02Message{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Demo02Message setId(Integer id) {
        this.id = id;
        return this;
    }
}
