package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService2;
import com.cafe24.mysite.vo.GuestbookVo;

@RestController("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestbookService2 guestbookService2;
	
	
	// @ResponseBody
	@RequestMapping(value= "/list/{no}", method=RequestMethod.GET)
	public JSONResult list(@PathVariable(value = "no") int no) {
		
//		GuestbookVo first = new GuestbookVo(1L, "user1","test1", "2019-07-10 12:00:00");
//		GuestbookVo second = new GuestbookVo(2L, "user2","test2", "2019-07-10 12:00:00");
//		
//		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
//		list.add(first);
//		list.add(second);
		
		List<GuestbookVo> list = guestbookService2.getContentsList(1);
		
		return JSONResult.success(list);
	}
	
	@RequestMapping(value= "/add", method=RequestMethod.POST)
	public JSONResult add() {
		
		return JSONResult.success(null);
	}
}
