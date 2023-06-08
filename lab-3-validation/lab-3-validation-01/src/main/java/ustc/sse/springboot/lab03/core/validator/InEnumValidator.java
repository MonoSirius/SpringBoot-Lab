package ustc.sse.springboot.lab03.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校验值是否在枚举范围之内
 * @author MonoSirius
 * @date 2023/6/8
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Integer> {

    /**
     * 值数组
     */
    private Set<Integer> values;

    /**
     * 获得 @InEnum 注解的 values() 属性，获得值数组，设置到 values 属性。
     * @param constraintAnnotation
     */
    @Override
    public void initialize(InEnum constraintAnnotation) {
        IntArrayValuable[] values = constraintAnnotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            // boxed 转换成包装类
            this.values = Arrays.stream(values[0].array()).boxed().collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // <2.1> 校验通过
        if (values.contains(value)) {
            return true;
        }

        // <2.2> 校验不通过,自定义提示
        // 禁用默认message
        context.disableDefaultConstraintViolation();
        //
        context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())
        ).addConstraintViolation(); // 重新添加提示语句
        return false;
    }
}
