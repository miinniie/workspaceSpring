package com.hanwha.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hanwha.myapp.service.MemberService;
import com.hanwha.myapp.vo.MemberVO;

@Controller
public class MemberController {
	@Inject
	MemberService service;
	//회원등록폼
	@RequestMapping("/member/memberFrm")
	public String memberFrm() {
		return "member/memberForm";//회원가입폼의 뷰파일명 /WEB-INF/views/member/memberForm.jsp
	}
	//회원등록									post방식으로 서버에 접속시
	@RequestMapping(value= "/member/memWrite", method=RequestMethod.POST)
	public ModelAndView memberWrite(MemberVO vo) {
		ModelAndView mav = new ModelAndView(); 
		try {
			//DB에 데이터를 insert하는 서비스클래스의 메소드 호출
			int result = service.memberInsert(vo);
			//클라이언트에게 보낼 데이터와 뷰파일을 담을 수 있는 객체이며,
			//뷰페이지에서 해당변수를 사용할 수 있다.
			
			if(result>0) {//회원등록성공
				mav.setViewName("member/loginForm");	
			}else{//회원등록실패 > 실패앴을 때는 memberResult로 보내는거임
				mav.setViewName("member/memberResult");
			}
		}catch(Exception e) {
			mav.setViewName("member/memberResult");
		}
		return mav;
	}
	//로그인폼으로 이동하기
	@RequestMapping("/member/loginForm")
	public String login() {
		return "member/loginForm";
	}
	//로그인을 하는 mapping을 만든다
	@RequestMapping(value="/member/loginCheck", method=RequestMethod.POST)
	public ModelAndView loginCheck(MemberVO vo, HttpSession session) {
		System.out.println(session.getId());
		ModelAndView mav = new ModelAndView(); 
		//DB조회 (select), logVO는 실행결과값
		//null이면 로그인 실패
		//객체가 있으면 로그인 성공
		
		MemberVO logVO = service.memberLogin(vo);
		if(logVO == null) { //로그인 실패
			mav.setViewName("redirect:loginForm");
		}else {//로그인 성공
			//아이디와 이름, 로그인 여부를 session객체에 기록하여 현재 서버의 모든 페이지에서
			//data를 사용할 수 있도록 설정한다.
			//						변수,		값
			session.setAttribute("logId", logVO.getUserid());
			session.setAttribute("logName", logVO.getUsername());
			session.setAttribute("logStatus", "Y");
			//현재 매핑메소드에서 다른 매핑메소드를 호출하기
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	//로그아웃 기능을 하는 mapping을 만들 것임
	@RequestMapping(value="/member/logout", method=RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		
		//session 지우기: 로그인 정보 없어진다. 새로운 session이 할당된다.
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
				mav.setViewName("redirect:/");
				
		return mav;		
	}
	//회원정보수정 폼
	@RequestMapping("/member/memberEdit")
	public ModelAndView memberEdit(HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("logId"); 
		MemberVO vo = service.getMember(userid);
		
		mav.addObject("vo", vo);
		mav.setViewName("member/memberEdit");
		
		return mav;
	}
	//회원정보수정
	@RequestMapping(value="/member/memberEditOk", method=RequestMethod.POST)
	public ModelAndView memberEditOk(MemberVO vo, HttpSession session) {
		//session의 로그인 id와 새로 입력한 비밀번호가 일치할때만 수정가능하게 할것임
		vo.setUserid((String)session.getAttribute("logId"));
		
		service.memberUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:memberEdit");
		
		return mav;
	}
}