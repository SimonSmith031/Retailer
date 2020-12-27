package com.example.retailer.config;

import com.example.retailer.service.AccountService;
import com.example.retailer.service.BookService;
import com.example.retailer.service.DealService;
import com.example.retailer.service.ItemService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan("com.example.retailer.dao")
public class RetailerConfig {

    @Bean
    public BookService bookService() {
        return new BookService();
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    public ItemService itemService() {
        return new ItemService();
    }

    @Bean
    public DealService dealService() {
        return new DealService();
    }

    // 跨域，最开始是成功了不知道为什么后来又不能够成功了
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET","POST", "PUT", "DELETE")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers");
            }
        };
    }
}
