package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.security.Auth;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.util.Paging;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService BoardService;
	
//	@RequestMapping("list")
//	public String list(Model model, 
//			@RequestParam(value="curPageNum", required=true, defaultValue="1") int curPageNum,
//			@RequestParam(value="showBoardNum", required=true, defaultValue="5") int showBoardNum
//			) {
//		
//		Paging paging = new Paging();
//		Long totalBoardCount = BoardService.getTotalBoardCount();
//		paging.pagingSetting(totalBoardCount, showBoardNum , curPageNum);
//		
//		List<BoardVo> list = BoardService.getBoardList(paging.getStartPageNum(), showBoardNum);
//		model.addAttribute("paging", paging);
//		model.addAttribute("list", list);
//		
//		return "board/list";
//	}
	
	@RequestMapping("list")
	public String listSearch(Model model, 
			@RequestParam(value="keyword", required=true, defaultValue="") String keyword,
			@RequestParam(value="curPageNum", required=true, defaultValue="1") int curPageNum,
			@RequestParam(value="showBoardNum", required=true, defaultValue="5") int showBoardNum
			) {
		
		Paging paging = new Paging();
		Long totalBoardCount = BoardService.getTotalSearchBoardCount(keyword);
		paging.pagingSetting(totalBoardCount, showBoardNum , curPageNum);
		
		List<BoardVo> list = BoardService.getSearchBoardList(keyword, paging.getStartPageNum(), showBoardNum);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		
		return "board/list";
	}
	
	
	
	@RequestMapping("modify")
	public String modify(
			Model model,
			@RequestParam(value="no", required=true, defaultValue="0") Long boardNo) {
				
				BoardVo boardVo = BoardService.getBoardByNo(boardNo);
				model.addAttribute("vo", boardVo);
				
		return "board/modify";
	}
	
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify( @ModelAttribute BoardVo boardVo) {
				
		BoardService.modify(boardVo);
		
		return "redirect:/board/view?no="+boardVo.getNo();
	}
	
	@RequestMapping("view")
	public String view(
			Model model,
			@RequestParam(value="no", required=true, defaultValue="0") Long boardNo) {
		
		BoardVo boardVo = BoardService.getBoardByNo(boardNo);
		model.addAttribute("vo", boardVo);
		
		return "board/view";
	}
	
	
	// 인증하고 들어왔을때는 write 화면이 나와야하는데  인증이 안되어있으면 로그인 화면으로 리다이렉트
	
//	@Auth
	@RequestMapping("write")
	public String write(
			Model model,
			@RequestParam(value = "groupNo", required = true, defaultValue = "-1") int groupNo,
			@RequestParam(value = "orderNo", required = true, defaultValue = "0") int orderNo,
			@RequestParam(value = "depth", required = true, defaultValue = "0") int depth,
			@RequestParam(value = "no", required = true, defaultValue = "0") int parentNo
			) {
		
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("depth", depth);
		model.addAttribute("parentNo", parentNo);
		
		return "board/write";
	}
	
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String write(
			Model model, 
			@ModelAttribute BoardVo boardVo,
			HttpSession session) {
		
		Long userNo = ((UserVo) session.getAttribute("authUser")).getNo();
		boardVo.setUserNo(userNo);
		if(boardVo.getGroupNo() == -1) {
			BoardService.createBoard(boardVo);
		}else {
			BoardService.createReplyBoard(boardVo);
		}
		
		return "redirect:/board/list";
		
	}
	
	@RequestMapping("remove")
	public String remove(
			@RequestParam(value="no", required=true, defaultValue="0") Long boardNo) {
			// 삭제했을때 자신이 삭제했던페이지로 돌아가야한다.
		 	BoardService.removeBoard(boardNo);
		 	
		return "redirect:/board/list";
	}
}
