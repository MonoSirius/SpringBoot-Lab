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

## [lab04 RocketMQ]()

### [启动RocketMQ]()
在启动Broker时报错: `java.lang.IllegalAccessError: class org.apache.rocketmq.common.UtilAll (in unnamed module @0x2c8b1039) cannot access class sun.nio.ch.DirectBuffer (in module java.base) 
because module java.base does not export sun.nio.ch to unnamed module @0x2c8b1039`

💡解决:  
`vim bin/runbroker.sh`  
添加: `$JAVA ${JAVA_OPT} --add-exports=java.base/sun.nio.ch=ALL-UNNAMED $@`



### [@RocketMQMessageListener]() 注解

💡常用属性如下:
```java
/**
 * Consumer 所属消费者分组
 *
 * Consumers of the same role is required to have exactly same subscriptions and consumerGroup to correctly achieve
 * load balance. It's required and needs to be globally unique.
 *
 * See <a href="http://rocketmq.apache.org/docs/core-concept/">here</a> for further discussion.
 */
String consumerGroup();

/**
 * 消费的 Topic
 *
 * Topic name.
 */
String topic();

/**
 * 选择器类型。默认基于 Message 的 Tag 选择。
 *
 * Control how to selector message.
 *
 * @see SelectorType
 */
SelectorType selectorType() default SelectorType.TAG;
/**
 * 选择器的表达式。
 * 设置为 * 时，表示全部。
 *
 * 如果使用 SelectorType.TAG 类型，则设置消费 Message 的具体 Tag 。
 * 如果使用 SelectorType.SQL92 类型，可见 https://rocketmq.apache.org/rocketmq/filter-messages-by-sql92-in-rocketmq/ 文档
 *
 * Control which message can be select. Grammar please see {@link SelectorType#TAG} and {@link SelectorType#SQL92}
 */
String selectorExpression() default "*";

/**
 * 消费模式。可选择并发消费，还是顺序消费。
 *
 * Control consume mode, you can choice receive message concurrently or orderly.
 */
ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

/**
 * 消息模型。可选择是集群消费，还是广播消费。
 *
 * Control message mode, if you want all subscribers receive message all message, broadcasting is a good choice.
 */
MessageModel messageModel() default MessageModel.CLUSTERING;

/**
 * 消费的线程池的最大线程数
 *
 * Max consumer thread number.
 */
int consumeThreadMax() default 64;

/**
 * 消费单条消息的超时时间
 *
 * Max consumer timeout, default 30s.
 */
long consumeTimeout() default 30000L;
```

### [顺序消息]()

在 RocketMQ 中，Producer 可以根据定义 MessageQueueSelector 消息队列选择策略，选择 Topic 下的队列。目前提供三种策略：

- SelectMessageQueueByHash ，基于 hashKey 的哈希值取余，选择对应的队列。  
- SelectMessageQueueByRandom ，基于随机的策略，选择队列。  
- SelectMessageQueueByMachineRoom ，😈 有点看不懂，目前是空的实现，暂时无视吧。  
未使用 MessageQueueSelector 时，采用轮询的策略，选择队列。  

RocketMQTemplate 在发送顺序消息时，默认采用 SelectMessageQueueByHash 策略。如此，相同的 hashKey 的消息，就可以发送到相同的 Topic 的对应队列中。

> 使用订单号作为hashkey时,同一订单的消息就会发送到同一个队列中


### [事务消息]()

```java
// Demo07Producer.java

@RocketMQTransactionListener(txProducerGroup = TX_PRODUCER_GROUP)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return rollback, commit or unknown
        logger.info("[executeLocalTransaction][执行本地事务，消息：{} arg：{}]", msg, arg);
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // ... check transaction status and return rollback, commit or unknown
        logger.info("[checkLocalTransaction][回查消息：{}]", msg);
        return RocketMQLocalTransactionState.COMMIT;
    }

}
```

- 实现 #executeLocalTransaction(...) 方法，实现执行本地事务。
  - 此时消息已经发送,但是会根据该方法的返回结果决定提交还是回滚.
  
- 实现 #checkLocalTransaction(...) 方法，检查本地事务。
  - 在事务消息长事件未被提交或回滚时，RocketMQ 会回查事务消息对应的生产者分组下的 Producer ，获得事务消息的状态。此时，该方法就会被调用。

事务回查:  
- 通过 msg 消息，获得某个业务上的标识或者编号，然后去数据库中查询业务记录，从而判断该事务消息的状态是提交还是回滚。
- 记录 msg 的事务编号，与事务状态到数据库中。
  - 第一步, 先存储一条id为msg编号的事务和其状态到数据库中
  - 第二步, 调用带有事务的业务 Service 的方法。在该 Service 方法中，在逻辑都执行成功的情况下，更新 id 为 msg 的事务编号，状态变更为 RocketMQLocalTransactionState.COMMIT 。这样，我们就可以伴随这个事务的提交，更新 id 为 msg 的事务编号的记录的状为 RocketMQLocalTransactionState.COMMIT
  - 第三步, 我们在 #executeLocalTransaction(...) 方法中，就可以通过查找数据库，id 为 msg 的事务编号的记录的状态，然后返回

> tip:  
> 要以 try-catch 的方式，调用业务 Service 的方法。如此，如果发生异常，回滚事务的时候，可以在 catch 中，
> 更新 id 为 msg 的事务编号的记录的状态为 RocketMQLocalTransactionState.ROLLBACK