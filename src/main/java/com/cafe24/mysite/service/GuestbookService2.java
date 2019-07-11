package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService2 {

	@Autowired
	private GuestbookDao guestbookDao;

	public List<GuestbookVo> getList(Long lastNo) {
		return guestbookDao.getList(lastNo);
	}

	public GuestbookVo addContents(GuestbookVo guestbookVo) {
//		guestbookVo.setNo(10L);
//		guestbookVo.setName("user1");
//		guestbookVo.setContents("test1");
//		guestbookVo.setRegDate("2019-07-10 00:00:00");
			guestbookDao.insertGuestbook(guestbookVo);
		return guestbookVo;
	}

	public Boolean deleteGuestbook(GuestbookVo guestbookVo) {
		return guestbookDao.deleteGuestbook(guestbookVo);
	}

	public void writeContent(GuestbookVo vo) {
		// TODO Auto-generated method stub

	}

}
