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
	//ȸ�������
	@RequestMapping("/member/memberFrm")
	public String memberFrm() {
		return "member/memberForm";//ȸ���������� �����ϸ� /WEB-INF/views/member/memberForm.jsp
	}
	//ȸ�����									post������� ������ ���ӽ�
	@RequestMapping(value= "/member/memWrite", method=RequestMethod.POST)
	public ModelAndView memberWrite(MemberVO vo) {
		ModelAndView mav = new ModelAndView(); 
		try {
			//DB�� �����͸� insert�ϴ� ����Ŭ������ �޼ҵ� ȣ��
			int result = service.memberInsert(vo);
			//Ŭ���̾�Ʈ���� ���� �����Ϳ� �������� ���� �� �ִ� ��ü�̸�,
			//������������ �ش纯���� ����� �� �ִ�.
			
			if(result>0) {//ȸ����ϼ���
				mav.setViewName("member/loginForm");	
			}else{//ȸ����Ͻ��� > ���о��� ���� memberResult�� �����°���
				mav.setViewName("member/memberResult");
			}
		}catch(Exception e) {
			mav.setViewName("member/memberResult");
		}
		return mav;
	}
	//�α��������� �̵��ϱ�
	@RequestMapping("/member/loginForm")
	public String login() {
		return "member/loginForm";
	}
	//�α����� �ϴ� mapping�� �����
	@RequestMapping(value="/member/loginCheck", method=RequestMethod.POST)
	public ModelAndView loginCheck(MemberVO vo, HttpSession session) {
		System.out.println(session.getId());
		ModelAndView mav = new ModelAndView(); 
		//DB��ȸ (select), logVO�� ��������
		//null�̸� �α��� ����
		//��ü�� ������ �α��� ����
		
		MemberVO logVO = service.memberLogin(vo);
		if(logVO == null) { //�α��� ����
			mav.setViewName("redirect:loginForm");
		}else {//�α��� ����
			//���̵�� �̸�, �α��� ���θ� session��ü�� ����Ͽ� ���� ������ ��� ����������
			//data�� ����� �� �ֵ��� �����Ѵ�.
			//						����,		��
			session.setAttribute("logId", logVO.getUserid());
			session.setAttribute("logName", logVO.getUsername());
			session.setAttribute("logStatus", "Y");
			//���� ���θ޼ҵ忡�� �ٸ� ���θ޼ҵ带 ȣ���ϱ�
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	//�α׾ƿ� ����� �ϴ� mapping�� ���� ����
	@RequestMapping(value="/member/logout", method=RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		
		//session �����: �α��� ���� ��������. ���ο� session�� �Ҵ�ȴ�.
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
				mav.setViewName("redirect:/");
				
		return mav;		
	}
	//ȸ���������� ��
	@RequestMapping("/member/memberEdit")
	public ModelAndView memberEdit(HttpSession session) {
		
		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("logId"); 
		MemberVO vo = service.getMember(userid);
		
		mav.addObject("vo", vo);
		mav.setViewName("member/memberEdit");
		
		return mav;
	}
	//ȸ����������
	@RequestMapping(value="/member/memberEditOk", method=RequestMethod.POST)
	public ModelAndView memberEditOk(MemberVO vo, HttpSession session) {
		//session�� �α��� id�� ���� �Է��� ��й�ȣ�� ��ġ�Ҷ��� ���������ϰ� �Ұ���
		vo.setUserid((String)session.getAttribute("logId"));
		
		service.memberUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:memberEdit");
		
		return mav;
	}
}