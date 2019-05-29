package com.cafe24.mysite.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;


@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public Boolean deleteGuestbook(GuestbookVo guestbookVo) {
		
		return 1 == sqlSession.delete("guestbook.delete", guestbookVo);
	}

	public Boolean insertGuestbook(GuestbookVo guestbookVo) {

		return 1 == sqlSession.insert("guestbook.insert", guestbookVo);
		
	}

	public List<GuestbookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}
	
}
