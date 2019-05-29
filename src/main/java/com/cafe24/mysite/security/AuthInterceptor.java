package com.cafe24.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	// spring-servlet.xml에 	<mvc:interceptors> <mvc:mapping path="/**"/> 에 명시되어서 모든 url 요청이 여기에 들린다. 
	// 만약 preHandle이 실행된 후 리턴값이 false이면 컨트롤러로 안가고, true이면 컨트롤러로 요청을 전달한다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			System.out.println(handler);
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. 메소드의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Method에 @Auth 없으면
		// Class(Type)에 @Auth를 받아오기 -- 과제
//		if(auth == null) {
//			return true;
//		}

		// 5. @Auth가 안 붙어 있는 경우

		if (auth == null) {
			return true;
		}
		
		// 6. @Auth(class또는 method에)가 붙어 있기 때문에
		// 인증 여부 체크
		HttpSession session = request.getSession();
		if (session == null) { // 인증이 안되어 있음
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) { // 인증이 안되어 있음
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 7. Role 가져오기
		Auth.Role role = auth.role();
		
		// 8. role이 Auth.Role.USER 라면,
		// 인증된 모든 사용자는 접근 가능
		if(role == Auth.Role.USER) {
			return true;
		}
		
		// 9. ADMIN Role 권한 체크
		//
		// authIser.getRole().equals("ADMIN")
		
		
		return false;
	}

}
