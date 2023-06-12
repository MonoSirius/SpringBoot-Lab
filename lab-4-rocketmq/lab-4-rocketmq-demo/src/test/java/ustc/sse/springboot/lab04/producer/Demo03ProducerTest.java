package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ustc.sse.springboot.lab04.Application;

import java.util.concurrent.CountDownLatch;

/**
 * @author MonoSirius
 * @date 2023/6/11
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Demo03ProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private Demo03Producer producer;

    @Test
    void testSyncDelaySend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        // 延迟级别 3 --> 10s
        SendResult result = producer.syncDelaySend(id, 3);
        logger.info("[testSyncSendDelay][发送编号：[{}] 发送结果：[{}]]", id, result);

        new CountDownLatch(1).await();
    }
}
