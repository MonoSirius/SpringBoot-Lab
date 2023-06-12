package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo01Message;

/**
 * 实现三种发送消息的方式
 * @author MonoSirius
 * @date 2023/6/8
 */
@Component
public class Demo01Producer {

    private final RocketMQTemplate rocketMQTemplate;

    public Demo01Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult syncSend(Integer id) {
        Demo01Message message = new Demo01Message(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo01Message.TOPIC, message);
    }

    public void asyncSend(Integer id, SendCallback callback) {
        Demo01Message message = new Demo01Message(id);
        rocketMQTemplate.asyncSend(Demo01Message.TOPIC, message, callback);
    }

    public void onewaySend(Integer id) {
        Demo01Message message = new Demo01Message(id);

        // oneway 发送消息
        rocketMQTemplate.sendOneWay(Demo01Message.TOPIC, message);
    }
}
