package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo04Message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
@Component
public class Demo04Producer {

    private final RocketMQTemplate rocketMQTemplate;

    public Demo04Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult syncSend(Integer id) {
        Demo04Message message = new Demo04Message().setId(id);
        return rocketMQTemplate.syncSend(Demo04Message.TOPIC, message);
    }
}
