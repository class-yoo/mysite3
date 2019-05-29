package com.cafe24.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		if (supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED; // 내가 지원해주는 것이 아니기 때문에 다른 resolver에게 요청하라는뜻
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 안붙어있음
		if(authUser==null) {
			return false;
		}
		
		// 파라미터 타입이 UserVo가 아님
		if(parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		return false;
	}

}
