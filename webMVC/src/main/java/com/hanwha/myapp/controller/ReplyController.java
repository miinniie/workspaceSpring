package com.hanwha.myapp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanwha.myapp.service.ReplyService;
import com.hanwha.myapp.vo.ReplyVO;

@Controller
public class ReplyController {
	@Inject
	ReplyService service;
	
	//´ñ±Ûµî·Ï, ajax´Â responsebodyÇØÁà¾ßÇÔ
	@RequestMapping(value="/replyWrite", method=RequestMethod.POST)
	@ResponseBody
	public int replyWrite(ReplyVO vo, HttpServletRequest request, HttpSession session) {
		vo.setUserid((String)session.getAttribute("logId"));
		vo.setIp(request.getRemoteAddr());		
		int result = service.replyInsert(vo);		
		return result;
	}
	//´ñ±Û¸ñ·Ï ¼±ÅÃÇÏ±â
	@RequestMapping("/replyAllList")
	@ResponseBody
	public List<ReplyVO> replyAllSelect(int no) {
		return service.replyAllSelect(no);
	}
	@RequestMapping(value="/replyEditOk", method=RequestMethod.POST)
	@ResponseBody
	public int replyUpdate(ReplyVO vo) {
		System.out.println(vo.getReplyno());
		System.out.println(vo.getNo());
		System.out.println(vo.getComent());
		return service.replyUpdate(vo);
	}
	
	//´ñ±Û »èÁ¦ÇÏ±â
	@RequestMapping("/replyDelete")
	@ResponseBody
	public int replyDelete(int replyno) {
		return service.replyDelete(replyno);
	}
}
