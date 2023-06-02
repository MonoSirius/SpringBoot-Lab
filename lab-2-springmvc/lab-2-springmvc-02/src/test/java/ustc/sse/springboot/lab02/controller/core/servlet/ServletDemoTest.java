package ustc.sse.springboot.lab02.controller.core.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ustc.sse.springboot.lab02.core.servlet.ServletDemo;

/**
 * @author MonoSirius
 * @date 2023/6/1
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServletDemo.class)
public class ServletDemoTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testDoGet() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/test/02"));

        resultActions.andDo(MockMvcResultHandlers.print());
    }
}
