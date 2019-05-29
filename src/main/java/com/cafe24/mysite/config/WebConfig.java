package com.cafe24.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.cafe24.config.web.MVCConfig;

@Configuration
@EnableWebMvc
@ComponentScan({"com.cafe24.mysite.controller"})
@Import({MVCConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter{
		
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		
		return resolver;
	}

	
	// Default Servlet Handler(Dispatcher servlet으로 온 요청중에 핸들러매핑에 등록이 안되어있는 경로를 처리해줌  [ex] 이미지, css파일등)
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
}