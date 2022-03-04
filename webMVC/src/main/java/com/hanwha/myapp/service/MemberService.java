package com.hanwha.myapp.service;

import com.hanwha.myapp.vo.MemberVO;

public interface MemberService {
	public int memberInsert(MemberVO vo);
	public MemberVO memberLogin(MemberVO vo);
	public MemberVO getMember(String userid);
	public int memberUpdate(MemberVO vo);
}
