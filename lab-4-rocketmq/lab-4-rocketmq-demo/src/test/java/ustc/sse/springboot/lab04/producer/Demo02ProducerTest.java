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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author MonoSirius
 * @date 2023/6/10
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class Demo02ProducerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo02Producer demo02Producer;

    @Test
    void testSendBatch() throws InterruptedException {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        SendResult result = demo02Producer.sendBatch(ids);
        logger.info("[testSendBatch][发送编号:[{}] 发送结果:[{}]]", ids, result);

        new CountDownLatch(1).await();
    }
}
