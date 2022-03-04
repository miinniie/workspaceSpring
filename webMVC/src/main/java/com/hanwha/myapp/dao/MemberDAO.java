package com.hanwha.myapp.dao;

import com.hanwha.myapp.vo.MemberVO;

public interface MemberDAO {
	//회원등록
	public int memberInsert(MemberVO vo);
	//로그인
	public MemberVO memberLogin(MemberVO vo);
	//회원정보수정 폼
	public MemberVO getMember(String userid);
	//회원정보 수정
	public int memberUpdate(MemberVO vo);
}
