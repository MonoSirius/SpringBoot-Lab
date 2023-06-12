package ustc.sse.springboot.lab04.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo06Message;

/**
 * @author MonoSirius
 * @date 2023/6/12
 */
@Component
@RocketMQMessageListener(
        topic = Demo06Message.TOPIC,
        consumerGroup = "demo06-consumer-group-" + Demo06Message.TOPIC
)
public class Demo06Consumeer implements RocketMQListener<Demo06Message> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onMessage(Demo06Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);

        // sleep 2秒
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
