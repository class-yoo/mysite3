package com.cafe24.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
// @Target 어노테이션을 어디에 명시에서 사용할지 정하는것
// ElementType.TYPE은 클래스에 명시할 수 있는것  ElementType.METHOD는 메소드에 명시할 수 있는것
@Target({ElementType.TYPE ,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // reflection으로 접근가능  
public @interface Auth {
	
	public enum Role{
		USER, ADMIN
	}
	
	public Role role() default Role.USER;
	
//	String value() default "USER";
	
}
