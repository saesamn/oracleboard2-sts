package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.Board;
import com.example.demo.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;

	// 초기요청
	@RequestMapping("/")
	public String main() {
		return "redirect:boardlist";        // boardlist 요청
	}
	
	// 글작성 폼
	@RequestMapping("boardform")
	public String boardform() {
		return "board/boardform";
	}
	
	// 글작성
	@RequestMapping("boardwrite")
	public String boardwrite(@ModelAttribute Board board, Model model) {
		
		int result = service.insert(board);
		if(result == 1) System.out.println("글작성 성공");
		
		model.addAttribute("result", result);
		
		return "board/insertresult";
	}
	
	// 글목록 : 전체 목록, 검색 목록
	@RequestMapping("boardlist")
	public String boardlist(@RequestParam(value="page", defaultValue="1") int page,
							Board board,
						    Model model) {
		
		System.out.println("search:"+ board.getSearch());
		System.out.println("keyword:"+ board.getKeyword());
		
//		int page = 1;            			// 현재 페이지 번호
		int limit = 10;			 			// 한 페이지에 출력할 데이터 갯수
		
		int listcount = service.count(board);	// 총 데이터 갯수, 검색 데이터 갯수
		System.out.println("listcount:"+ listcount);
		
		int startRow = (page-1) * limit + 1;
		int endRow = page * limit;
		board.setStartRow(startRow);
		board.setEndRow(endRow);
		
		List<Board> boardlist = service.list(board);		// 데이터 목록
		System.out.println("boardlist:"+ boardlist);
		
		// 총 페이지
		int pagecount = listcount / limit + ((listcount%10==0) ? 0 : 1);
		
		int startpage = ((page-1)/10) * limit + 1;      // 1,  11,  21...
		int endpage = startpage + 10 - 1;				//10,  20,  30...
		
		// 마지막 블럭에 실제 존재하는 페이지만 출력 
		if(endpage > pagecount)   endpage = pagecount;
		
		model.addAttribute("page", page);
		model.addAttribute("listcount", listcount);
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("pagecount", pagecount);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);	
		model.addAttribute("search", board.getSearch());	
		model.addAttribute("keyword", board.getKeyword());	
		
		return "board/boardlist";
	}
	
	// 상세 페이지 : 조회수 1증가 + 상세정보 구하기
	@RequestMapping("boardcontent")
	public String boardcontent(@RequestParam("no") int no,
							   @RequestParam("page") String page,
							   Model model) {
		
		service.updatecount(no);				// 조회수 1증가
		Board board = service.content(no);		// 상세정보 구하기
		String content = board.getContent().replace("\n", "<br>");
		
		model.addAttribute("board", board);
		model.addAttribute("content", content);
		model.addAttribute("page", page);
		
		return "board/boardcontent";
	}
	
	// 수정 폼
	@RequestMapping("boardupdateform")
	public String boardupdateform(@RequestParam("no") int no,
								  @RequestParam("page") String page,
								  Model model) {
		
		Board board = service.content(no);		// 상세 정보 구하기
		
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		
		return "board/boardupdateform";
	}
	
	// 글수정
	@RequestMapping("boardupdate")
	public String boardupdate(@ModelAttribute Board board,
							  @RequestParam("page") String page,
							  Model model) {
		
		int result = 0;
		Board db = service.content(board.getNo());    		// 상세정보 구하기
		
		// 비번 비교
		if(db.getPasswd().equals(board.getPasswd())) {     // 비번 일치시
			result = service.update(board);                // update sql문 실행
			
		}else {		// 비번 불일치시
			result = -1;
		}
		model.addAttribute("result", result);
		model.addAttribute("page", page);
		
		return "board/updateresult";
	}
	
	// 글삭제 폼
	@RequestMapping("boarddeleteform")
	public String boarddeleteform(@RequestParam("no") int no,
			  					  @RequestParam("page") String page,
			  					  Model model) {
		
		model.addAttribute("no", no);
		model.addAttribute("page", page);
		
		return "board/boarddeleteform";
	}
	
	// 글삭제
	@RequestMapping("boarddelete")
	public String boarddelete(@ModelAttribute Board board,
						      @RequestParam("page") String page,
						      Model model) {
		int result = 0;
		Board db = service.content(board.getNo());     // 상세정보 구하기
		
		// 비번 비교
		if(db.getPasswd().equals(board.getPasswd())) {   // 비번 일치시
			result = service.delete(board.getNo());      // delete sql문 실행
		}else {			// 비번 불일치시
			result = -1;
		}
		model.addAttribute("result", result);
		model.addAttribute("page", page);
		
		return "board/deleteresult";
	}
	
	
	
	
	
	
	
	
}
