# 实验笔记
对实验过程中踩的坑进行记录
[toc]

## lab1 mapStruct
踩坑:

mapstruct在生成转换代码时找不到属性，生成转换代码时lombok还没有生成setter、getter相应方法，所以找不到属性

解决:

添加
```xml
<path>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-mapstruct-binding</artifactId>
    <version>0.2.0</version>
</path>
```

[解决参考](https://blog.csdn.net/weixin_42272869/article/details/122337269)

## lab02 SpringMVC
### [使用Junit5进行单元测试]()

集成测试: 
@ExtendWith是@RunWith的替代
```java
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
```
> 在类上，我们添加了 @AutoConfigureMockMvc 注解，
> 用于自动化配置我们稍后注入的 MockMvc Bean 对象 mvc 。
> 在后续的测试中，我们会看到都是通过 mvc 调用后端 API 接口。
> 而每一次调用后端 API 接口，都会执行真正的后端逻辑。因此，整个逻辑，走的是集成测试，
> **会启动一个真实的 Spring 环境**。

单元测试:

```java
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
```
> @WebMvcTest 注解，是包含了 @AutoConfigureMockMvc 的组合注解，
> 所以它会自动化配置我们稍后注入的 MockMvc Bean 对象 mvc 。
> 在后续的测试中，我们会看到都是通过 mvc 调用后端 API 接口。
> 但是！每一次调用后端 API 接口，**并不会执行真正的后端逻辑，而是走的 Mock 逻辑**。
> 也就是说，整个逻辑，走的是单元测试，**只会启动一个 Mock 的 Spring 环境**

```java
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
```

- 每次 API 接口的请求，都通过 `MockMvcRequestBuilders` 来构建。构建完成后，通过 mvc 执行请求，返回 `ResultActions` 结果。
- 执行完请求后，通过调用 `ResultActions` 的 `andExpect(ResultMatcher matcher)` 方法，添加对结果的预期，相当于做断言。如果不符合预期，则会抛出异常，测试不通过。



### [不建议使用基于字段注入]()

推荐的方法是使用基于构造函数和基于setter的依赖注入。 

- 对于必需的依赖，建议使用基于构造函数的注入，设置它们为不可变的，并防止它们为null。
- 对于可选的依赖项，建议使用基于sett的注入。

[为什么不建议使用基于字段注入?](https://mp.weixin.qq.com/s/nPd1Gk-k1b7-fv19PML1Cw)

### [统一返回结果]() 
```java
/**
 * @author MonoSirius
 * @date 2023/5/31
 */
public class CommonResult<T> implements Serializable {
    public static final Integer CODE_OK = 200;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 对result进行 泛型转换
     * @param result 源泛型result
     * @return 目标泛型result
     * @param <T> 目标泛型
     */
    public static <T> CommonResult<T> fail(CommonResult<?> result) {
        return fail(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> ok(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(CODE_OK);
        result.setData(data);
        result.setMsg("");
        return result;
    }

    public static <T> CommonResult<T> fail(Integer code, String msg) {
        Assert.isTrue(!CODE_OK.equals(code), "code必须是错误的!");
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    @JsonIgnore
    public boolean isOk() {
        return CODE_OK.equals(code);
    }

    @JsonIgnore // 避免Jackson序列化给前端
    public boolean isFail() {
        return !isOk();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
```

### [全局统一异常]()
参考: https://www.kancloud.cn/onebase/ob/484204

一共 10 位，分成四段

第一段，1 位，类型
*    1 - 业务级别异常
*    2 - 系统级别异常

第二段，3 位，系统类型 
* 001 - 用户系统 
* 002 - 商品系统 
* 003 - 订单系统 
* 004 - 支付系统 
* 005 - 优惠劵系统

第三段，3 位，模块

不限制规则。

一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子： 
* 001 - OAuth2 模块
* 002 - User 模块
* 003 - MobileCode 模块

第四段，3 位，错误码

不限制规则。

一般建议，每个模块自增。

### [Servlet、Filter、Listener]()

在 Servlet3.0 的新特性里，提供了三个注解，方便配置 Servlet、Filter、Listener 。
- `@WebServlet`
- `@WebFilter`
- `@WebListener` 

> 💡而在 SpringBoot 中，我们仅需要在 Application 类上，添加 @ServletComponentScan 注解，
> 开启对 @WebServlet、@WebFilter、@WebListener 注解的扫描。
> 不过要注意，当且仅当使用内嵌的 Web Server 才会生效。

## [lab03 Validation]()
### [@Valid 和 @Validated]()

- @Valid 注解，是 Bean Validation 所定义，可以添加在普通方法、构造方法、方法参数、方法返回、成员变量上，表示它们需要进行约束校验。

- @Validated 注解，是 Spring Validation 锁定义，可以添加在类、方法参数、普通方法上，表示它们需要进行约束校验。
同时，@Validated 有 value 属性，支持分组校验。

💡总结:

总的来说，绝大多数场景下，我们使用 @Validated 注解即可。

而在有嵌套校验的场景，我们使用 @Valid 注解添加到成员属性上。

### [自定义InEnum约束]()

作用:校验参数值是否在枚举范围内

> 开发自定义约束一共只要两步：
> - 1）编写自定义约束的注解；
> - 2）编写自定义的校验器 ConstraintValidator。

