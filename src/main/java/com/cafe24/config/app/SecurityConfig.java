package com.cafe24.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*Security Filter Chain
 
 V 표시는 DelegatingFilterProxy에 꼭 설정해야하는 Filter
 
	 1. ChannelProcessingFilter
	 2. SecurityContextPersistenceFilter		( auto-config default 	, V) ACL 없을때 기본적으로 URL 차단해주는역할 ? 
	 3. ConcurrentSessionFilter
	 4. LogoutFilter							( auto-config default	, V)
	 5. UsernamePasswordAuthenticationFilter	( auto-config default 	, V)
	 6. DefaultLoginPageGeneratingFilter		( auto-config default )
	 7. CasAuthenticationFilter
	 8. BasicAuthenticationFilter				( auto-config default 	, V )
	 9. RequestCacheAwareFilter					( auto-config default )
	10. SecurityContextHolderAwareRequestFilter	( auto-config default )
	11. JaasApiIntegrationFilter
	12. RememberMeAuthenticationFilter			(Custom Filter로 설정해줘야됨 , V)
	13. AnonymousAuthenticationFilter			( auto-config default )
	14. SessionManagementFilter					( auto-config default 	, V)
	15. ExceptionTranslationFilter				( auto-config default 	, V )*/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 스프링 시큐리티 필터 연결
	// WebSecurity 객체
	// SpringSecurityFilterChain 이라는 이름의 DelegatingFilterProxy Bean 객체를 생성
	// DelegatingFilterProxy Bean은 많은 Spring SecurityFilter Chain에 위임한다.
	// DelegatingFilterProxy가 11개의 설정을 가지고있다 ?
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		// ACL(AccessControlList)에 등록하지 않은 URL을 설정
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http); ->> 부모클래스가 URL를 차단하고있다.
		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}

}
