package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
public class Demo05Message {
    public static final String TOPIC = "DEMO_05";

    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo05Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo05Message{" +
                "id=" + id +
                '}';
    }
}
