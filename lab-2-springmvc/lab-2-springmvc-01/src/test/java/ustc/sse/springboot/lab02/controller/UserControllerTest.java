package ustc.sse.springboot.lab02.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ustc.sse.springboot.lab02.Application;
import ustc.sse.springboot.lab02.service.UserService;
import ustc.sse.springboot.lab02.vo.UserVO;

/**
 * @author MonoSirius
 * @date 2023/5/30
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void teseGet2() throws Exception {
        // Mock UserService 的 get 方法
        System.out.println("before mock:" + userService.get(1)); // <1.1>
        Mockito.when(userService.get(1)).thenReturn(
                new UserVO().setId(1).setUsername("username:1")); // <1.2>
        System.out.println("after mock:" + userService.get(1)); // <1.3>

        // 查询用户列表
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/user/v2/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"username:1\"\n" +
                "}")); // 响应结果
        // <1> 打印结果
        resultActions.andDo(MockMvcResultHandlers.print());
    }
}
