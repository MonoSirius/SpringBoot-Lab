package ustc.sse.springboot.lab02.service;

import org.springframework.stereotype.Service;
import ustc.sse.springboot.lab02.vo.UserVO;

/**
 * @author MonoSirius
 * @date 2023/5/30
 */
@Service
public class UserService {
    public UserVO get(Integer id) {
        return new UserVO().setId(id).setUsername("test");
    }
}
