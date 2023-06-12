package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo03Message;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */
@Component
public class Demo03Producer {


    private final RocketMQTemplate rocketMQTemplate;

    public Demo03Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult syncDelaySend(Integer id, int delayLevel) {
        Message<Demo03Message> message = MessageBuilder.withPayload(new Demo03Message().setId(id)).build();
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo03Message.TOPIC, message, 30 * 1000, delayLevel);

    }

    public void asyncDelaySend(Integer id, int delayLevel, SendCallback callback) {
        Message<Demo03Message> message = MessageBuilder.withPayload(new Demo03Message().setId(id)).build();
        //

        rocketMQTemplate.asyncSend(Demo03Message.TOPIC, message, callback, 30 * 1000, delayLevel);
    }
}
