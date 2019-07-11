package com.cafe24.mysite.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.service.GuestbookService2;
import com.cafe24.mysite.vo.GuestbookVo;

@RestController("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService2 guestbookService2;
	
	@RequestMapping(value="/list/{lastNo}", method=RequestMethod.GET)
	public JSONResult list(@PathVariable(value="lastNo") Long lastNo) {
		List<GuestbookVo> list = guestbookService2.getList(lastNo);
		return JSONResult.success(list);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult add(@RequestBody GuestbookVo guestbookVo) {
		guestbookService2.addContents(guestbookVo);
		// 새로생성된 게시물을 DB에 저장시킨후에 다시 가져와서 client로 보내주면 된다.
		return JSONResult.success(guestbookVo);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public JSONResult delete(@RequestBody GuestbookVo vo) {
		System.out.println("vo= "+vo.getNo());
		boolean result = guestbookService2.deleteGuestbook(vo);
		System.out.println("result= "+ result);
		return JSONResult.success(result ? vo.getNo() : -1);
	}
	
}
