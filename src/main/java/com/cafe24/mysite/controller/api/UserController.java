package com.cafe24.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.UserService;

@RestController("userAPIController")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//@ResponseBody
	@RequestMapping(value= "/checkemail", method= RequestMethod.POST)
	public JSONResult checkEmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		Boolean exist = userService.existEmail(email);
		return JSONResult.success(exist);
	}
}