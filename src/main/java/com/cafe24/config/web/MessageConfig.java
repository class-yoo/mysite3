package com.cafe24.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	
	
//	<bean id="messageSource"
//			class="org.springframework.context.support.ResourceBundleMessageSource">
//			<property name="basenames">
//				<list>
//					<value>messages/messages_ko</value>
//				</list>
//			</property>
//	</bean>
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("com/cafe24/config/web/messages/messages_ko");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
	
}
