package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/12
 */
public class Demo07Message {
    public static final String TOPIC = "DEMO_06";

    private Integer id;

    public Integer getId() {
        return id;
    }

    public Demo07Message setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Demo07Message{" +
                "id=" + id +
                '}';
    }
}
