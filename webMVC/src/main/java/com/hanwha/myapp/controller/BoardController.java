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
	
	//����Ʈ(�۸��)
	@RequestMapping("/board/list")
	public ModelAndView boardList(pagingVO pVo) {
		System.out.println(pVo.getSearchKey()+", "+pVo.getSearchWord());
		
		ModelAndView mav = new ModelAndView();
		//DB���� �ѷ��ڵ�� ���ϱ�
		pVo.setTotalRecord(service.totalRecord(pVo));
		//�ش��������� ���ڵ� ����
		List<BoardVO> lst = service.boardList(pVo);
		
		mav.addObject("lst", lst);
		mav.addObject("pVo", pVo);
		mav.setViewName("board/boardList");
		
		return mav;
	}
	//�۵�� ��
	@RequestMapping("/board/boardFrm")
	public String boardForm() {
		return "board/boardForm";
	}
	//�۵��
	@RequestMapping(value="/board/boardFormOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(HttpServletRequest request) {
		
		BoardVO vo = new BoardVO();
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		
		vo.setIp(request.getRemoteAddr());//�۾��� IP
		
		HttpSession session = request.getSession(); //session��ü�� �α����� ���̵� �������
		vo.setUserid((String)session.getAttribute("logId"));
	
		int result=0;
		try{
			result = service.boardWriteOk(vo);
			
		}catch(Exception e) {}
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {//�۵��->����Ʈ�� �̵�
			mav.setViewName("redirect:list");
		}else{//�۵�Ͻ���->�۵�� ��
			mav.addObject("msg", "�۵��");
			mav.setViewName("board/boardResult");
		}
		return mav;	
	}
	
	//�۳��뺸��
	@RequestMapping("/board/view")
	public String boardView(Model model, int no, pagingVO pVo) {
		
		// no�� ���ڵ� ��ȸ�� ����
		service.boardHitCount(no);
		// no�� �۳��� ����
		BoardVO vo = service.boardView(no);
		model.addAttribute("vo",vo);
		model.addAttribute("pVo",pVo);
		
		return "board/boardView";
	}
	//�ۼ�����
	@RequestMapping("/board/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		
		BoardVO vo = service.boardView(no);
		mav.addObject("vo",vo);
		
		mav.setViewName("board/boardEdit");
		return mav;
	}
	//�ۼ���
	@RequestMapping(value="/board/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpSession session) {
		//session�� �α��� ���̵� vo�� userid�� ����
		vo.setUserid((String)session.getAttribute("logId"));
		
		int result = 0;
		try{
			result = service.boardEditOk(vo);
		}catch(Exception e) {}	
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {//�����Ǿ�����-> �۳��뺸��...
			mav.addObject("no",vo.getNo());
			mav.setViewName("redirect:view");
		}else {//�������п�����
			mav.addObject("msg", "�ۼ���");
			mav.setViewName("board/boardResult");
		}
		return mav;
	}
	//�ۻ���
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
		
		if(result>0) {//���� ����
			mav.setViewName("redirect:list");			
		}else { //���� ����
			mav.addObject("no", no);
			mav.setViewName("redirect:view");
		}
		return mav;
	}
}
