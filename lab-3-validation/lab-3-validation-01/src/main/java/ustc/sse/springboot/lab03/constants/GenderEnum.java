package ustc.sse.springboot.lab03.constants;

import ustc.sse.springboot.lab03.core.validator.IntArrayValuable;

import java.util.Arrays;

/**
 * @author MonoSirius
 * @date 2023/6/8
 */
public enum GenderEnum implements IntArrayValuable {
    MALE(1, "男"),
    FEMALE(2,"女")
    ;

    /**
     * 所有枚举值数组
     */
    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(GenderEnum::getValue).toArray();

    /**
     * 性别值
     */
    private final Integer value;

    /**
     * 性别
     */
    private final String gender;

    GenderEnum(Integer value, String gender) {
        this.value = value;
        this.gender = gender;
    }

    public Integer getValue() {
        return value;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
