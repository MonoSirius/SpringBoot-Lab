package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ustc.sse.springboot.lab04.message.Demo07Message;

/**
 * @author MonoSirius
 * @date 2023/6/12
 */
@Component
public class Demo07Producer {
    public static final String TX_PRODUCER_GROUP = "demo07-producer-group";
    private final RocketMQTemplate rocketMQTemplate;


    public Demo07Producer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public TransactionSendResult sendMessageInTransaction(Integer id) {
        Message<Demo07Message> message = MessageBuilder.withPayload(new Demo07Message().setId(id)).build();
        // 方法参数 arg ，后续我们调用本地事务方法的时候，会传入该 arg 。
        // 如果要传递多个方法参数给本地事务的方法，
        // 可以通过数组，例如说 Object[]{arg1, arg2, arg3} 这样的形式。
        return rocketMQTemplate.sendMessageInTransaction(TX_PRODUCER_GROUP, Demo07Message.TOPIC, message, id);
    }


    @RocketMQTransactionListener(txProducerGroup = TX_PRODUCER_GROUP)
    public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
            logger.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", message, arg);
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

            logger.info("[checkLocalTransaction][回查消息：{}]", message);
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
