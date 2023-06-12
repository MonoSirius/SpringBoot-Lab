package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo06Message;

/**
 * @author MonoSirius
 * @date 2023/6/12
 */
@Component
public class Demo06Producer {

    private final RocketMQTemplate rocketMQTemplate;

    public Demo06Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult syncSendOrderly(Integer id) {
        Demo06Message message = new Demo06Message().setId(id);

        return rocketMQTemplate.syncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }

    public void asyncSendOrderly(Integer id, SendCallback callback) {
        Demo06Message message = new Demo06Message().setId(id);

        rocketMQTemplate.asyncSendOrderly(Demo06Message.TOPIC, message, String.valueOf(id), callback);
    }

    public void onewaySendOrderly(Integer id) {
        Demo06Message message = new Demo06Message().setId(id);

        rocketMQTemplate.sendOneWayOrderly(Demo06Message.TOPIC, message, String.valueOf(id));
    }
}
