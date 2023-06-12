package ustc.sse.springboot.lab04.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ustc.sse.springboot.lab04.Application;

import java.util.concurrent.CountDownLatch;

/**
 * @author MonoSirius
 * @date 2023/6/8
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Demo01ProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private Demo01Producer producer;

    @Test
    void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);

        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待
        new CountDownLatch(1).await();
    }

    @Test
    void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, throwable);
            }
        });

        // 阻塞等待 保证消费
        new CountDownLatch(1).await();
    }

    @Test
    void testOnewaySend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.onewaySend(id);
        logger.info("[testOnewaySend][发送编号：[{}] 发送完成]", id);

        // 阻塞等待 保证消费
        new CountDownLatch(1).await();
    }
}
