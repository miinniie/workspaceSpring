package com.hanwha.myapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	@RequestMapping("/ajaxView")
	public String ajaxView() {
		return "ajax/ajaxView";
				
	}
	//클라이언트가 접속할 경우 문자열을 정보로 보낼 맵핑 ==============================
	@RequestMapping(value="/ajaxString",method = RequestMethod.GET, produces="application/text; charset=UTF-8")
	@ResponseBody //ajax는 리턴되는 데이터가 뷰가 아니고 실제 데이터라는 표시
	public String ajaxString(int no, String username) {
		System.out.println("번호 : "+no);
		System.out.println("이름 : "+username);
		//클라이언트에게 정보 보내기 작업
		String sendData = "ajax로 서버에서 받는 문자";
		//view 파일명이 아니라 실제 값이란 표시가 필요
		return sendData;
	}
	//클라이언트에게 Object 객체 보내기 ==============================
	@RequestMapping("/ajaxObject")
	@ResponseBody 
	public DataVO ajaxObject(String tel, String email) {
		System.out.printf("%s,%s\n",tel,email);
	
		DataVO vo = new DataVO();
		vo.setNum(9999);
		vo.setUsername("이순신");
		vo.setAddr("서울시 영등포구 여의도동");
		vo.setTel("010-6666-7777");
		//객체안에있는 한글은 깨지지 않는다.
		return vo;
	}
	@RequestMapping("/ajaxList")
	@ResponseBody
	public List<DataVO> ajaxList(){
		List<DataVO> list=new ArrayList<DataVO>();
		
		list.add(new DataVO(100,"홍길동","010-1234-5678","서울시 영등포구 여의도동"));
		list.add(new DataVO(200,"이순신","010-1234-1234","서울시 마포구 "));
		list.add(new DataVO(300,"세종대왕","010-5678-5678","서울시 마포구 공덕동"));
		list.add(new DataVO(400,"강감찬","010-9999-5678","서울시 강남구 신설동"));
		
		return list;
	}
	@RequestMapping("/ajaxMap")
	@ResponseBody
	public HashMap<String, DataVO> ajaxMap(){
		HashMap<String, DataVO> map = new HashMap<String, DataVO>();
		map.put("name1", new DataVO(1111,"박태환","010-1234-2345","여의도어딘가"));
		map.put("name2", new DataVO(2222,"김연아","010-1241-2975","수원 어딘가"));
		map.put("name3", new DataVO(3333,"박지성","010-1248-2322","제주 어딘가"));
	
		return map;
	}
	
	//자바에서 JSON데이터 형식으로 문자열을 만들어 내보내기 ==============================
	@RequestMapping(value="/ajaxJson", method=RequestMethod.GET, produces="applictaion/text; charset=UTF-8")
	@ResponseBody
	public String ajaxJSON() {
		int num = 12345;
		String username ="세종대왕";
		String tel = "010-1234-5678";
		
		String jsonData = "{\"num\":\""+num+"\",\"username\":\""+username+"\",\"tel\":\""+tel+"\"}";
		
		return jsonData;
	}
	
	//클라이언트가 보낸 post방식의 form데이터를 서버로 request ==============================
	@RequestMapping(value="/ajaxForm", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxForm(int num, String username, String email) {
		System.out.println("num->"+num);
		System.out.println("username->"+username);
		System.out.println("email->"+email);
		
		return "OK";
	}
} 
