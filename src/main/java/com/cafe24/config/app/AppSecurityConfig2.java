package com.cafe24.config.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig2 {
	
	@Bean(name="springSecurityFilterChain") // name이 틀리면 Servlet과 Validation관계가 끊어짐 
	public FilterChainProxy filterChainProxy() {
		
		List<SecurityFilterChain> filterChains = new ArrayList<SecurityFilterChain>();
		
		// filter를 거치지 않게 설정 ? xml 파일이었으면 filter="none"으로 설정
		filterChains.add(new DefaultSecurityFilterChain(
				new AntPathRequestMatcher("/assets/**")));
		filterChains.add(new DefaultSecurityFilterChain(
				new AntPathRequestMatcher("/favicon.ico")));
		filterChains.add(new DefaultSecurityFilterChain(
				new AntPathRequestMatcher("/**")));
		// filter chains
//		// 1. 
//		securityContextPersistenceFilter(),
//		// 2.
//		logoutFilter(),
//		// 3.
//		usernamePasswordAuthenticationFilter(),
//		// 4.
//		exceptiomTranslationFilter(),
//		// 5.
//		filterSecurityInterceptor(),
		
		return new FilterChainProxy(filterChains);
	}
	
	
	@Bean
	public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		
		// Security Context 컨테이너를 누구로 해줄지 파라미터로 넣어줘야한다
		return new SecurityContextPersistenceFilter(
				new HttpSessionSecurityContextRepository());
	}
}
