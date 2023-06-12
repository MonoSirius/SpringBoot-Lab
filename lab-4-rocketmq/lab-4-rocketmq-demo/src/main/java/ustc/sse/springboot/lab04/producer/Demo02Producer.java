package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo02Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MonoSirius
 * @date 2023/6/10
 */
@Component
public class Demo02Producer {

    private final RocketMQTemplate rocketMQTemplate;

    public Demo02Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult sendBatch(Collection<Integer> ids) {
        //
        List<Message> messages = new ArrayList<>(ids.size());

        for (Integer id : ids) {
            //
            Demo02Message message = new Demo02Message(id);
            // 构建 Spring Messaging 定义的 Message 消息
            messages.add(MessageBuilder.withPayload(message).build());
        }
        return rocketMQTemplate.syncSend(Demo02Message.TOPIC, messages, 30 * 1000L);
    }
}
