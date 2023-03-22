package com.gyb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @date 2023/3/13 - 21:16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket(){

        //创建封面信息对象      使用链式调用
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《KzMall》后端接口说明文档")
                .description("该接口说明了KzMall后端接口规范")
                .version("1.0.0")
                .contact(new Contact("GYB","6666.com","123456@kz.com"));
        ApiInfo apiInofo = apiInfoBuilder.build();

        //配置应用程序接口的位置
        Docket docket = new Docket(DocumentationType.SWAGGER_2)     //指定使用的Swagger版本
                .apiInfo(apiInofo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gyb.controller"))
                .paths(PathSelectors.any())     //限制范围到每个Controller中的根路径下（这里是/user）
                .build();
        return docket;
    }


}
