package com.zxh.common.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) // 用于生成API信息
                .select() // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                .apis(RequestHandlerSelectors.basePackage("com.zxh"))
                .paths(PathSelectors.any()
                )
                .build();
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact(
                "zxh", // 作者姓名
                "zxh", // 作者网址
                "zxh@qq.com"); // 作者邮箱

        return new ApiInfoBuilder()
                .title("zxh项目API") //  可以用来自定义API的主标题
                .description("zxh项目SwaggerAPI管理") // 可以用来描述整体的API
                .contact(contact)
                .build();
    }
}
