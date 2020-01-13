//package org.zisecm.extinterface.swagger;
//
//import springfox.documentation.service.Contact;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import springfox.documentation.builders.ApiInfoBuilder;
////import springfox.documentation.builders.PathSelectors;
////import springfox.documentation.builders.RequestHandlerSelectors;
////import springfox.documentation.service.ApiInfo;
////import springfox.documentation.spi.DocumentationType;
////import springfox.documentation.spring.web.plugins.Docket;
////import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Created by bianxh on 2019/1/21.
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerApp {
////	//是否开启swagger，正式环境一般是需要关闭的
////    @Value("${swagger.enabled}")
////    private boolean enableSwagger;
////    @Bean
////    public Docket createRestApi() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .apiInfo(apiInfo())
////                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
////                .enable(enableSwagger)
////                .select()
////                //为当前包路径
////                .apis(RequestHandlerSelectors.basePackage("org.zisecm.extinterface.controller"))
////                .paths(PathSelectors.any())
////                .build();
//////        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
////    }
////    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
////    private ApiInfo apiInfo() {
////        return new ApiInfoBuilder()
////                //页面标题
////                .title("Spring Boot 使用 Swagger2 构建RESTful API")
////                //版本号
////                .version("1.0")
////                //描述
////                .description("API 描述")
////                .build();
////    }
//	
//	@Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).
//                useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.regex("^(?!auth).*$"))
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
//                ;
//    }
//    private List<ApiKey> securitySchemes() {
//    	List<ApiKey> apikeys=new ArrayList<ApiKey>();
//    	apikeys.add(new ApiKey("Authorization", "Authorization", "header"));
//        return apikeys;
//    }
//    private List<SecurityContext> securityContexts() {
//    	List<SecurityContext> apikeys=new ArrayList<SecurityContext>();
//    	apikeys.add(SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                .build());
//        return apikeys;
//    }
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List<SecurityReference> se=new ArrayList<SecurityReference>();
//        se.add(new SecurityReference("Authorization", authorizationScopes));
//        return se;
//    }
//	
//}
