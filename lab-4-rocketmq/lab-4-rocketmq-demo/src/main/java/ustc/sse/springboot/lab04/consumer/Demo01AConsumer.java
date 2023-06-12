package ustc.sse.springboot.lab04.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo01Message;

/**
 * @author MonoSirius
 * @date 2023/6/8
 */
@Component
@RocketMQMessageListener(
        topic = Demo01Message.TOPIC,
        consumerGroup = "demo01-A-consumer-group-" + Demo01Message.TOPIC
)
public class Demo01AConsumer implements RocketMQListener<MessageExt> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onMessage(MessageExt message) {
        logger.info("[onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
    }
}
