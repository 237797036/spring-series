问题：
Spring中国际化怎么用？
国际化如何处理资源文件变化的问题？
国际化资源配置放在db中如何实现？


国际化就是做这个事情的，根据不同的语言显示不同的信息。

得先知道选择的是哪种地区的哪种语言，java中使用java.util.Locale来表示地区语言这个对象，内部包含了国家和语言的信息。

public Locale(String language, String country) {
    this(language, country, "");
}
比如language的值：zh表示中文，en表示英语，而中文可能很多地区在用，比如大陆地区：CN；新加坡用：SG；
英语也有很多国家用，GB表示英国，CA表示加拿大

国家语言简写格式：
language-country：zh-CN（中文【中国】），zh-SG（中文【新加坡】），en-GB（英语【英国】），en-CA（英语【加拿大】）

static public final Locale SIMPLIFIED_CHINESE = createConstant("zh", "CN"); //zh_CN
static public final Locale UK = createConstant("en", "GB"); //en_GB
static public final Locale US = createConstant("en", "US"); //en_US
static public final Locale CANADA = createConstant("en", "CA"); //en_CA

页面中显示姓名对应的标签，需要根据一个key及Locale信息来获取对应的国际化信息

org.springframework.context.MessageSource接口
内部有3个常用的方法用来获取国际化信息

public interface MessageSource {

    /**
     * 获取国际化信息
     * @param code 表示国际化资源中的属性名；
     * @param args用于传递格式化串占位符所用的运行期参数；
     * @param defaultMessage 当在资源找不到对应属性名时，返回defaultMessage参数所指定的默认信息；
     * @param locale 表示本地化对象
     */
    @Nullable
    String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale);

    /**
     * 与上面的方法类似，只不过在找不到资源中对应的属性名时，直接抛出NoSuchMessageException异常
     */
    String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException;

    /**
     * @param MessageSourceResolvable 将属性名、参数数组以及默认信息封装起来，它的功能和第一个方法相同
     */
    String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;

}

常见3个实现类：
ResourceBundleMessageSource
这个是基于Java的ResourceBundle基础类实现，允许仅通过资源名加载国际化资源

ReloadableResourceBundleMessageSource
这个功能和第一个类的功能类似，多了定时刷新功能，允许在不重启系统的情况下，更新资源的信息

StaticMessageSource
它允许通过编程的方式提供国际化信息，一会我们可以通过这个来实现db中存储国际化信息的功能。


通常使用spring时，都会使用带有ApplicationContext的spring容器，这些容器一般继承AbstractApplicationContext接口，
而这个接口实现了国际化接口MessageSource，所以通常用到的ApplicationContext类型的容器都自带了国际化的功能。


通常我们在ApplicationContext类型的容器中使用国际化的3个步骤：

步骤一：创建国际化文件

步骤二：向容器中注册一个MessageSource类型的bean，bean名称必须为：messageSource

步骤三：调用AbstractApplicationContext中的getMessage来获取国际化信息，其内部交给第二步中注册的messageSource的bean进行处理


1 创建国际化文件
国际化文件命名格式：名称_语言_地区.properties

message.properties文件：
name=您的姓名
personal_introduction=默认个人介绍:{0},{1}

这个文件名称没有指定Local信息，当系统找不到的时候会使用这个默认的

message_cn_ZH.properties：中文【中国】
name=姓名
personal_introduction=个人介绍:{0},{1},{0}

message_en_GB.properties：英文【英国】
name=Full name
personal_introduction=personal_introduction:{0},{1},{0}

2 动态参数使用
配置文件中的personal_introduction，个人介绍，包含了{0},{1},{0}，这个是动态参数
调用getMessage时，通过第二个参数传递过去


3 spring中注册国际化的bean
注意必须是MessageSource类型的，bean名称必须为messageSource

指定国际化配置文件的位置，格式：路径/文件名称,注意不包含【语言_国家.properties】这部分


4 监控国际化文件的变化
用ReloadableResourceBundleMessageSource这个类
可以监控国际化资源文件变化的功能，有个方法用来设置缓存时间。按秒设置缓存时间的方法setCacheSeconds

5 国际化信息存在db中
StaticMessageSource，这个类它允许通过编程的方式提供国际化信息，我们通过这个类来实现从db中获取国际化信息的功能。

public void addMessage(String code, Locale locale, String msg);
public void addMessages(Map<String, String> messages, Locale locale);
通过这两个方法来添加国际化配置信息。


6 bean名称为什么必须是messageSource
上面我容器启动的时候会调用refresh方法，过程如下：

org.springframework.context.support.AbstractApplicationContext#refresh
内部会调用
org.springframework.context.support.AbstractApplicationContext#initMessageSource
这个方法用来初始化MessageSource,方法内部会查找当前容器中是否有messageSource名称的bean，如果有就将其作为处理国际化的对象
如果没有找到，此时会注册一个名称为messageSource的MessageSource（空的）


7 自定义bean中使用国际化
自定义的bean如果想使用国际化，只需实现MessageSourceAware这个接口，spring容器会自动调用这个方法，将MessageSource注入到bean，
然后就可以使用MessageSource获取国际化信息。





































