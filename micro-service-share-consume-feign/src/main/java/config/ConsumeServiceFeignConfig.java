package config;

import feign.Logger;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;


/**
 * A custom <code>@Configuration</code> for the feign client. Can contain override
 * <code>@Bean</code> definition for the pieces that make up the client, for instance
 * {@link feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
 *
 * Feign Client的配置类，注意：
 *  1. 该类可以独立出去；
 *  2. 该类上也可添加@Configuration声明是一个配置类；
 *  配置类上也可添加@Configuration注解，声明这是一个配置类；
 *  但此时千万别将该放置在主应用程序上下文@ComponentScan所扫描的包中，
 *  否则，该配置将会被所有Feign Client共享，无法实现细粒度配置！
 *  个人建议，不加@Configuration注解
 * @see FeignClientsConfiguration for the defaults
 */
public class ConsumeServiceFeignConfig {

    public static final int CONNECT_TIMEOUT_MILLIS = 1000;

    public static final int READ_TIMEOUT_MILLIS = 1000;
    /**
     * Feign的性能中等，可能官方对自己的性能也是知道的，索性全部关闭日志了
     * 这里打开日志
     * @return
     */
    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(CONNECT_TIMEOUT_MILLIS, READ_TIMEOUT_MILLIS);
    }

   /* feign:
        client:
        config:
        feignName:
            connectTimeout: 5000  # 相当于Request.Options
            readTimeout: 5000     # 相当于Request.Options
                # 配置Feign的日志级别，相当于代码配置方式中的Logger
            loggerLevel: full
                # Feign的错误解码器，相当于代码配置方式中的ErrorDecoder
            errorDecoder: com.example.SimpleErrorDecoder
                # 配置重试，相当于代码配置方式中的Retryer
            retryer: com.example.SimpleRetryer
                # 配置拦截器，相当于代码配置方式中的RequestInterceptor
            requestInterceptors:
                 - com.example.FooRequestInterceptor
                  - com.example.BarRequestInterceptor
            decode404: false
    */


}
