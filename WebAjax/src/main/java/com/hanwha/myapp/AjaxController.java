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
	//Ŭ���̾�Ʈ�� ������ ��� ���ڿ��� ������ ���� ���� ==============================
	@RequestMapping(value="/ajaxString",method = RequestMethod.GET, produces="application/text; charset=UTF-8")
	@ResponseBody //ajax�� ���ϵǴ� �����Ͱ� �䰡 �ƴϰ� ���� �����Ͷ�� ǥ��
	public String ajaxString(int no, String username) {
		System.out.println("��ȣ : "+no);
		System.out.println("�̸� : "+username);
		//Ŭ���̾�Ʈ���� ���� ������ �۾�
		String sendData = "ajax�� �������� �޴� ����";
		//view ���ϸ��� �ƴ϶� ���� ���̶� ǥ�ð� �ʿ�
		return sendData;
	}
	//Ŭ���̾�Ʈ���� Object ��ü ������ ==============================
	@RequestMapping("/ajaxObject")
	@ResponseBody 
	public DataVO ajaxObject(String tel, String email) {
		System.out.printf("%s,%s\n",tel,email);
	
		DataVO vo = new DataVO();
		vo.setNum(9999);
		vo.setUsername("�̼���");
		vo.setAddr("����� �������� ���ǵ���");
		vo.setTel("010-6666-7777");
		//��ü�ȿ��ִ� �ѱ��� ������ �ʴ´�.
		return vo;
	}
	@RequestMapping("/ajaxList")
	@ResponseBody
	public List<DataVO> ajaxList(){
		List<DataVO> list=new ArrayList<DataVO>();
		
		list.add(new DataVO(100,"ȫ�浿","010-1234-5678","����� �������� ���ǵ���"));
		list.add(new DataVO(200,"�̼���","010-1234-1234","����� ������ "));
		list.add(new DataVO(300,"�������","010-5678-5678","����� ������ ������"));
		list.add(new DataVO(400,"������","010-9999-5678","����� ������ �ż���"));
		
		return list;
	}
	@RequestMapping("/ajaxMap")
	@ResponseBody
	public HashMap<String, DataVO> ajaxMap(){
		HashMap<String, DataVO> map = new HashMap<String, DataVO>();
		map.put("name1", new DataVO(1111,"����ȯ","010-1234-2345","���ǵ����"));
		map.put("name2", new DataVO(2222,"�迬��","010-1241-2975","���� ���"));
		map.put("name3", new DataVO(3333,"������","010-1248-2322","���� ���"));
	
		return map;
	}
	
	//�ڹٿ��� JSON������ �������� ���ڿ��� ����� �������� ==============================
	@RequestMapping(value="/ajaxJson", method=RequestMethod.GET, produces="applictaion/text; charset=UTF-8")
	@ResponseBody
	public String ajaxJSON() {
		int num = 12345;
		String username ="�������";
		String tel = "010-1234-5678";
		
		String jsonData = "{\"num\":\""+num+"\",\"username\":\""+username+"\",\"tel\":\""+tel+"\"}";
		
		return jsonData;
	}
	
	//Ŭ���̾�Ʈ�� ���� post����� form�����͸� ������ request ==============================
	@RequestMapping(value="/ajaxForm", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxForm(int num, String username, String email) {
		System.out.println("num->"+num);
		System.out.println("username->"+username);
		System.out.println("email->"+email);
		
		return "OK";
	}
} 
