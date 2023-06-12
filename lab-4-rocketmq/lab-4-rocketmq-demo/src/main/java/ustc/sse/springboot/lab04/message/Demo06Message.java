package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/12
 */
public class Demo06Message {
    public static final String TOPIC = "DEMO_06";

    private Integer id;

    @Override
    public String toString() {
        return "Demo06Message{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Demo06Message setId(Integer id) {
        this.id = id;
        return this;
    }
}
