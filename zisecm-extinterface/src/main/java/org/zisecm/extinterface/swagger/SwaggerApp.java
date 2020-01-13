package org.zisecm.extinterface.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableSwaggerBootstrapUI
public class SwaggerApp {

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .pathMapping("/")
                .select()
                // 指定当前包路径，这里就添加了两个包，注意方法变成了basePackage，中间加上成员变量splitor
                .apis(RequestHandlerSelectors.basePackage("org.zisecm.extinterface.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                ;
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot整合Swagger")
                .description("SpringBoot整合Swagger，详细信息......")
                .version("9.0")
//                .contact(new Contact("张三", "blog.csdn.net", "zhangsan@gmail.com"))
//                .contact(new Contact("李四", "blog.csdn.net", "zhangsan@gmail.com"))
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com")
                .build();
    }

    /**
     * SecurityScheme 子类 BasicAuth OAuth ApiKey
     * @return
     */
    private List<SecurityScheme> securitySchemes(){
        List<SecurityScheme> list = new ArrayList<>();
        // basicAuth SwaggerBootstrapUI支持的不好,使用swagger原生UI
        list.add(new BasicAuth("basicAuth"));
        return list;
    }
}
