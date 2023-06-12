package ustc.sse.springboot.lab04.message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
public class Demo04Message {
    public static final String TOPIC = "DEMO_04";

    private Integer id;

    @Override
    public String toString() {
        return "Demo04Message{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Demo04Message setId(Integer id) {
        this.id = id;
        return this;
    }
}
