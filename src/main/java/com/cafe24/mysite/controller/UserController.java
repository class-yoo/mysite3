package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		
		return "user/join";
	}
	
	// Valid 어노테이션을 명시해주고 해당 Vo 클래스의 필드에 @NotEmpty 어노테이션을 명시해준다. 빈값이 담겨서 요청이 들어오면 에러발생
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo, 
			BindingResult result,
			Model model) {
			
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());// Map 타입의 리턴값을 넣어줌
			
			return "/user/join";
		}
		
		userService.join(userVo);
		
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = true, defaultValue = "") String password, HttpSession session,
			Model model) {
		
		UserVo authUser = userService.getUser(new UserVo(email, password));

		if (authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}

		// session 처리
		session.setAttribute("authUser", authUser);

		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/";

	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, HttpSession session) {

		UserVo userVo = userService.getUser(((UserVo) session.getAttribute("authUser")).getNo());
		
		model.addAttribute("name", userVo.getName());
		model.addAttribute("email", userVo.getEmail());
		model.addAttribute("gender", userVo.getGender());
		
		return "user/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String logout(@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "password", required = true, defaultValue = "") String password,
			@RequestParam(value = "gender", required = true, defaultValue = "") String gender, HttpSession session) {

		UserVo userVo = new UserVo();
		userVo.setName(name);
		userVo.setPassword(password);
		userVo.setGender(gender);

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		userVo.setNo(authUser.getNo());

		userService.updateUser(userVo);
		authUser.setName(name);

		return "redirect:/";
	}
	
	
}
