package com.cafe24.mysite.controller.api;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController("userAPIController")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserService userService;

	// @ResponseBody

	@ApiOperation(value = "이메일 존재 여부")
	@ApiImplicitParams({ @ApiImplicitParam(value = "email", required = true, defaultValue = "") })
	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public JSONResult checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		Boolean exist = userService.existEmail(email);
		System.out.println(email);
		return JSONResult.success(exist);
	}

	// ResponseEntity 사용하면 헤더의 상태메세지도 변경할 수 있다.
	@PostMapping(value = "/join")
	public ResponseEntity<JSONResult> join(@RequestBody @Valid UserVo userVo, BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(error.getDefaultMessage()));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(result));
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<JSONResult> login(@RequestBody UserVo userVo) {
		System.out.println("userVo="+ userVo);
		// 파라미터에 @Valid를 명시해주지 않고 유효성검사를 동적으로 생성해줘서 적용한다.
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		// UserVo의 email 필드에 선언된 validation만 적용한다.
		Set<ConstraintViolation<UserVo>> validatorResults = validator.validateProperty(userVo, "email");
		
		if(validatorResults.isEmpty() == false) {
			for(ConstraintViolation<UserVo> validatorResult : validatorResults ) {
//				String message = validatorResult.getMessage();
				String message = messageSource.getMessage("Email.userVo.email", null, LocaleContextHolder.getLocale());
				JSONResult result =  JSONResult.fail(message);
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(null));
	}
}