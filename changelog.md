# 实验笔记
对实验过程中踩的坑进行记录

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
### 使用Junit5进行单元测试
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



### 不建议使用基于字段注入

推荐的方法是使用基于构造函数和基于setter的依赖注入。 

- 对于必需的依赖，建议使用基于构造函数的注入，设置它们为不可变的，并防止它们为null。
- 对于可选的依赖项，建议使用基于sett的注入。

[为什么不建议使用基于字段注入?](https://mp.weixin.qq.com/s/nPd1Gk-k1b7-fv19PML1Cw)