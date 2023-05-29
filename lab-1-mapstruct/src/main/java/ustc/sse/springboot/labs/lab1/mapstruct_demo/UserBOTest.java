package ustc.sse.springboot.labs.lab1.mapstruct_demo;

import org.junit.jupiter.api.Test;
import ustc.sse.springboot.labs.lab1.mapstruct_demo.bo.UserBO;
import ustc.sse.springboot.labs.lab1.mapstruct_demo.convert.UserConvert;
import ustc.sse.springboot.labs.lab1.mapstruct_demo.entity.User;

/**
 * @author MonoSirius
 * @date 2023/5/29
 */
public class UserBOTest {
    @Test
    public void testUserBO() {
        User user = new User();
        user.setId(1);
        user.setUsername("mono");
        user.setPassword("123456");
        UserBO userBO = UserConvert.INSTANCE.convert(user);
        System.out.println(user);
        System.out.println(userBO);
    }
}
