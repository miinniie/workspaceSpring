package com.hanwha.myapp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hanwha.myapp.service.BoardService;
import com.hanwha.myapp.vo.BoardVO;
import com.hanwha.myapp.vo.pagingVO;

@Controller
public class BoardController {
	@Inject
	BoardService service;
	
	//리스트(글목록)
	@RequestMapping("/board/list")
	public ModelAndView boardList(pagingVO pVo) {
		System.out.println(pVo.getSearchKey()+", "+pVo.getSearchWord());
		
		ModelAndView mav = new ModelAndView();
		//DB에서 총레코드수 구하기
		pVo.setTotalRecord(service.totalRecord(pVo));
		//해당페이지의 레코드 선택
		List<BoardVO> lst = service.boardList(pVo);
		
		mav.addObject("lst", lst);
		mav.addObject("pVo", pVo);
		mav.setViewName("board/boardList");
		
		return mav;
	}
	//글등록 폼
	@RequestMapping("/board/boardFrm")
	public String boardForm() {
		return "board/boardForm";
	}
	//글등록
	@RequestMapping(value="/board/boardFormOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(HttpServletRequest request) {
		
		BoardVO vo = new BoardVO();
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		
		vo.setIp(request.getRemoteAddr());//글쓴이 IP
		
		HttpSession session = request.getSession(); //session객체의 로그인한 아이디를 얻기위해
		vo.setUserid((String)session.getAttribute("logId"));
	
		int result=0;
		try{
			result = service.boardWriteOk(vo);
			
		}catch(Exception e) {}
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {//글등록->리스트로 이동
			mav.setViewName("redirect:list");
		}else{//글등록실패->글등록 폼
			mav.addObject("msg", "글등록");
			mav.setViewName("board/boardResult");
		}
		return mav;	
	}
	
	//글내용보기
	@RequestMapping("/board/view")
	public String boardView(Model model, int no, pagingVO pVo) {
		
		// no의 레코드 조회수 증가
		service.boardHitCount(no);
		// no의 글내용 선택
		BoardVO vo = service.boardView(no);
		model.addAttribute("vo",vo);
		model.addAttribute("pVo",pVo);
		
		return "board/boardView";
	}
	//글수정폼
	@RequestMapping("/board/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		
		BoardVO vo = service.boardView(no);
		mav.addObject("vo",vo);
		
		mav.setViewName("board/boardEdit");
		return mav;
	}
	//글수정
	@RequestMapping(value="/board/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpSession session) {
		//session의 로그인 아이디를 vo의 userid에 셋팅
		vo.setUserid((String)session.getAttribute("logId"));
		
		int result = 0;
		try{
			result = service.boardEditOk(vo);
		}catch(Exception e) {}	
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {//수정되었을때-> 글내용보기...
			mav.addObject("no",vo.getNo());
			mav.setViewName("redirect:view");
		}else {//수정실패였을때
			mav.addObject("msg", "글수정");
			mav.setViewName("board/boardResult");
		}
		return mav;
	}
	//글삭제
	@RequestMapping("/board/boardDel")
	public ModelAndView boardDel(int no, pagingVO pVo, HttpSession session) {
		String userid = (String)session.getAttribute("logId");
		
		int result = service.boardDel(no, userid);
		
		ModelAndView mav = new ModelAndView();
		
		//mav.addObject("pVo", pVo);
		mav.addObject("pageNum",pVo.getPageNum());
		if(pVo.getSearchWord()!=null) {
			mav.addObject("searchKey",pVo.getSearchKey());
			mav.addObject("searchWord",pVo.getSearchWord());
		}
		
		if(result>0) {//글이 삭제
			mav.setViewName("redirect:list");			
		}else { //삭제 실패
			mav.addObject("no", no);
			mav.setViewName("redirect:view");
		}
		return mav;
	}
}
