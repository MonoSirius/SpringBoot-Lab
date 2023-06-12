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
public class Demo04ProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private Demo04Producer producer;

    @Test
    void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
