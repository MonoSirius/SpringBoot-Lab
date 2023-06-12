package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
public class Demo03Message {
    public static final String TOPIC = "DEMO_03";

    private Integer id;

    @Override
    public String toString() {
        return "Demo03Message{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Demo03Message setId(Integer id) {
        this.id = id;
        return this;
    }
}
