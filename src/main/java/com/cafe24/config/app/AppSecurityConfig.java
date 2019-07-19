package com.cafe24.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*Security Filter Chain
 
 V 표시는 DelegatingFilterProxy에 꼭 설정해야하는 Filter
 
	 1. ChannelProcessingFilter
	 2. SecurityContextPersistenceFilter		( auto-config default 	, V) ACL 없을때 기본적으로 URL 차단해주는역할 ? 
	 3. ConcurrentSessionFilter
	 4. LogoutFilter							( auto-config default	, V)
	 5. UsernamePasswordAuthenticationFilter	( auto-config default 	, V)
	 6. DefaultLoginPageGeneratingFilter		( auto-config default )
	 7. CasAuthenticationFilter
	 8. BasicAuthenticationFilter				( auto-config default )
	 9. RequestCacheAwareFilter					( auto-config default )
	10. SecurityContextHolderAwareRequestFilter	( aut	o-config default )
	11. JaasApiIntegrationFilter
	12. RememberMeAuthenticationFilter			(Custom Filter로 설정해줘야됨 , V)
	13. AnonymousAuthenticationFilter			( auto-config default )
	14. SessionManagementFilter					( auto-config default 	, V)
	15. ExceptionTranslationFilter				( auto-config default 	, V )*/


@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	// 스프링 시큐리티 필터 연결
	// WebSecurity 객체
	// SpringSecurityFilterChain 이라는 이름의 DelegatingFilterProxy Bean 객체를 생성
	// DelegatingFilterProxy Bean은 많은 Spring SecurityFilter Chain에 위임한다.
	// DelegatingFilterProxy가 11개의 설정을 가지고있다 ?
	@Override
	public void configure(WebSecurity web) throws Exception {
		// super.configure(web);
		// ACL(AccessControlList)에 등록하지 않을 URL을 설정
		// 권한 및 인증을 확인하지않고 요청을 받는다.
//		web.ignoring().antMatchers("/assets/**");
//		web.ignoring().antMatchers("/favicon.ico");
		web.ignoring().regexMatchers("\\A/assets/.*\\Z");
		web.ignoring().regexMatchers("\\A/favicon.ico\\Z");
	}
	
	// Interceptor URL의 요청을 안전하게 보호(보안)하는 방법
	
	/*
	/user/update  - >(ROLE_USER, ROLE_ADMIN) -> Authenticated
	/user/logout  - >(ROLE_USER, ROLE_ADMIN) -> Authenticated
	/board/write  - >(ROLE_USER, ROLE_ADMIN) -> Authenticated
	/board/delete - >(ROLE_USER, ROLE_ADMIN) -> Authenticated
	/board/modify - >(ROLE_USER, ROLE_ADMIN) -> Authenticated
	/admin/** -> ROLE_ADMIN(Authorized)
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http); ->> 부모클래스가 URL를 차단하고있다.
		
		// 1. ACL 설정
		
		http.authorizeRequests()
		// 인증이 되어있을때(authenticated?)
		.antMatchers("/user/update", "/user/logout").authenticated()
		.antMatchers("/board/write", "/board/delete", "/board/modify").authenticated()
		
//		ADMIN Authorization(ADMIN 권한, ROLE_ADMIN)
//		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
//		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/gallery/upload", "/gallery/delete/**").hasAuthority("ROLE_ADMIN")
		
//		 모두 허용
//		.antMatchers("/**").permitAll()
		.anyRequest().permitAll()
//		 하나씩 순차적으로 인증된다.
		
		// Temporary for Testing
//		http.csrf().disable();
		
		
		// 2. 로그인 설정
		
		.and()
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/user/auth")
		.failureUrl("/user/login?result=fail")
		.defaultSuccessUrl("/", true)
		.usernameParameter("email") // jsp파일의 form에서 email로 설정된 name의 값이 무엇인지
		.passwordParameter("password") // jsp파일의 form에서 패스워드로 설정된 name의 값이 무엇인지

		// 3. 로그아웃 설정
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 어디로 가야 로그아웃 요청인가 ?
		.logoutSuccessUrl("/") // 로그아웃 성공시 요청할 URL
		.invalidateHttpSession(true)
		
		
		// 4. Access Denial Handler 
		.and()
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp")

		//
		// 5. RememberMe 
		//
		.and()
		.rememberMe()
		.key("mysite3")
		.rememberMeParameter("remember-me");

		// Temporary for Testing
		http.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.and().authenticationProvider(authenticationProvider());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
