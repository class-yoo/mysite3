package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("/list")
	public String getGuestbookList(Model model) {
		
		List<GuestbookVo> guestbookVoList =guestbookService.getGuestbookList();		
		model.addAttribute("list", guestbookVoList);
		
		return "guestbook/list";
	}
	
	
	@RequestMapping("/delete")
	public String deleteGuestbook(@RequestParam(value = "no", required = true, defaultValue = "0") Long no,
			Model model) {
		
		model.addAttribute("no", no);
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteGuestbook(@RequestParam(value = "no", required = true, defaultValue = "0") Long no,
			@RequestParam(value = "password", required = true, defaultValue = "") String password
			) {
		
		guestbookService.deleteGuestbook(new GuestbookVo(no, password));
		
		return "redirect:/guestbook/list";
	}
	
	
	@RequestMapping("/create")
	public String deleteGuestbook(@RequestParam(value = "name", required = true, defaultValue = "0") String name,
			@RequestParam(value = "password", required = true, defaultValue = "") String password,
			@RequestParam(value = "contents", required = true, defaultValue = "") String contents
			) {
		
		guestbookService.createGuestbook(new GuestbookVo(name, password, contents));
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/spa")
	public String ajax() {
		return "guestbook/index-spa";
	}
}
