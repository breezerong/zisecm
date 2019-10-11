package com.ecm.portal;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortalConfig {
//	@Bean
//	public MultipartConfigElement multipartConfigElement(
//			@Value("${spring.http.multipart.maxFileSize}") String maxFileSize,
//			@Value("${spring.http.multipart.maxRequestSize}") String maxRequestSize) {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		// 单个文件最大
//		factory.setMaxFileSize(maxFileSize);
//		// 设置总上传数据总大小
//		factory.setMaxRequestSize(maxRequestSize);
//		return factory.createMultipartConfig();
//	}
}
