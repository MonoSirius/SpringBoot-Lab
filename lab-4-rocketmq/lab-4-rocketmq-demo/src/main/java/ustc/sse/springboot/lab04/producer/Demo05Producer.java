package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo05Message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
@Component
public class Demo05Producer {

    private final RocketMQTemplate rocketMQTemplate;

    public Demo05Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
    public SendResult syncSend(Integer id) {
        Demo05Message message = new Demo05Message().setId(id);

        return rocketMQTemplate.syncSend(Demo05Message.TOPIC, message);
    }

}
