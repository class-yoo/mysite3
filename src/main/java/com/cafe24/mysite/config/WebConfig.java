package com.cafe24.mysite.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.cafe24.config.web.MVCConfig;
import com.cafe24.config.web.SecurityConfig;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.cafe24.mysite.controller" })
@Import({ MVCConfig.class , SecurityConfig.class, MessageConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		
		return resolver;
	}
	
	// Default Servlet Handler(Dispatcher servlet으로 온 요청중에 핸들러매핑에 등록이 안되어있는 경로를 처리해줌
	// [ex] 이미지, css파일등)
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

//	<mvc:message-converters>
//	<!-- 요청에 대한 응답이 Controller 에서 @Responsebody가 붙어있고 String으로 리턴됐을때 이 컨버터를 
//		거쳐간다 -->
//	<bean
//		class="org.springframework.http.converter.StringHttpMessageConverter">
//		<property name="supportedMediaTypes">
//			<list>
//				<value>text/html; charset=UTF-8</value>
//			</list>
//		</property>
//	</bean>
//	<!-- 요청에 대한 응답이 Controller 에서 @Responsebody가 붙어있고 json으로 리턴됐을때 이 컨버터를 
//		거쳐간다 -->
//	<bean
//		class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
//		<property name="supportedMediaTypes">
//			<list>
//				<value>application/json; charset=UTF-8</value>
//			</list>
//		</property>
//	</bean>
//	</mvc:message-converters>
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {

		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", Charset.forName("utf-8"))));

		return converter;

	}
	
	// Message Converter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		///////////////////////////////////////////////////////
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
		
		
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().indentOutput(true)
				.dateFormat(new SimpleDateFormat("yyyy-MM-dd")).modulesToInstall(new ParameterNamesModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(builder.build());
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("utf-8"))));

		return converter;
	}

}