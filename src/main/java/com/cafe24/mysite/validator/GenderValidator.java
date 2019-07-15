package com.cafe24.mysite.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cafe24.mysite.validator.constraint.ValidGender;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
	private Pattern pattern = Pattern.compile("M|F|NONE"); 
	
	@Override
	public void initialize(ValidGender constraintAnnotation) {
	}
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.length() == 0 || "".contentEquals(value)) {
			return false;
		}
		return pattern.matcher(value).matches();
	}
	
}
