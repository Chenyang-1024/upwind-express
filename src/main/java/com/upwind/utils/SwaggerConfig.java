package com.upwind.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description swagger 配置类
 **/
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.upwind.controller"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.upwind.controller"))
                .paths(PathSelectors.any())
//                .paths(Predicates.or(PathSelectors.ant("/index"),
//                        PathSelectors.ant("/list"),
//                        PathSelectors.ant("/project/{pid}"),
//                        PathSelectors.ant("/comment/{pid}"),
//                        PathSelectors.ant("/case/{pid}"),
//                        PathSelectors.ant("/closestStore/{pid}"),
//                        PathSelectors.ant("/brand/register"),
//                        PathSelectors.ant("/franchisee/register"),
//                        PathSelectors.ant("/login"),
//                        PathSelectors.ant("/getVerifyCode")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
//        Contact contact = new Contact("lisboy", "http://lisboy.top", "lisboy98@163.com");
        return new ApiInfoBuilder()
                .title("逆风物流管理平台")
                .description("upwind")
                .version("1.0.0")
                .build();
    }

}
