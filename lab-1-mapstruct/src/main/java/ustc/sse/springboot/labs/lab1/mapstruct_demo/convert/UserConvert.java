package ustc.sse.springboot.labs.lab1.mapstruct_demo.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ustc.sse.springboot.labs.lab1.mapstruct_demo.bo.UserBO;
import ustc.sse.springboot.labs.lab1.mapstruct_demo.entity.User;

/**
 * 1. 声明Mapper注解
 * 2.
 * @author MonoSirius
 * @date 2023/5/29
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class); // <2>
    UserBO convert(User user);
}
