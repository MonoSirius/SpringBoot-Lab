package ustc.sse.springboot.lab02.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * @author MonoSirius
 * @date 2023/5/31
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testGet() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/user/get?id=1"));

        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testException01() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/user/exception-01"));

        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testException02() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/user/exception-02"));

        resultActions.andDo(MockMvcResultHandlers.print());
    }
}
