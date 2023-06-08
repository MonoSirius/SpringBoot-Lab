package ustc.sse.springboot.lab03.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ustc.sse.springboot.lab03.dto.UserAddDTO;

/**
 * @author MonoSirius
 * @date 2023/6/6
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {
    @Autowired UserService userService;

    @Test
    void testGet() {
        userService.get(1);
    }

    @Test
    void testAdd() {
        UserAddDTO userAddDTO = new UserAddDTO();
        userService.add(userAddDTO);
    }

    @Test
    void testAdd01() {
        UserAddDTO userAddDTO = new UserAddDTO();
        userService.add01(userAddDTO);
    }

    @Test
    void testAdd02() {
        UserAddDTO userAddDTO = new UserAddDTO();
        userService.add02(userAddDTO);
    }
}
