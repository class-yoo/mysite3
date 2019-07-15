package com.cafe24.mysite.validator.constraint;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cafe24.mysite.validator.GenderValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
@Constraint(validatedBy=GenderValidator.class)
public @interface ValidGender {
	//결과에대한 출력메세지
	String message() default "Invalid Gender" ;
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
