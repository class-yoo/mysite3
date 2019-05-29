package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.util.Paging;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public Long getTotalBoardCount() {
		return boardDao.getTotalBoardCount();
	}
	
	public List<BoardVo> getBoardList(int startPageNum, int showBoardNum) {
		return boardDao.selectBoardList(startPageNum, showBoardNum);
	}
	
	public Long getTotalSearchBoardCount(String keyword) {
		return boardDao.getTotalSearchBoardCount(keyword);
	}

	public List<BoardVo> getSearchBoardList(String keyword, int startPageNum, int showBoardNum) {
		return boardDao.getSearchBoardList(keyword, startPageNum, showBoardNum);
	}
	
	public BoardVo getBoardByNo(Long boardNo) {
		return boardDao.selectBoardByNo(boardNo);
	}

	public Boolean createBoard(BoardVo boardVo) {

		return 1 == boardDao.insertBoard(boardVo);
	}
	
	public Boolean createReplyBoard(BoardVo boardVo) {
		boardDao.increaseOrderNo(boardVo);
		
		return 1 == boardDao.insertReplyBoard(boardVo);
	}
	
	public Boolean modify(BoardVo boardVo) {

		return 1 == boardDao.updateBoard(boardVo);
	}

	public Boolean removeBoard(Long boardNo) {
		
		return 1 == boardDao.deleteBoard(boardNo);
	}

}
