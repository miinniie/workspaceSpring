package com.hanwha.myapp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanwha.myapp.dao.MemberDAO;
import com.hanwha.myapp.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberDAO dao;
	
	@Override
	public int memberInsert(MemberVO vo) {
		return dao.memberInsert(vo);
	}

	@Override
	public MemberVO memberLogin(MemberVO vo) {
		return dao.memberLogin(vo);
	}

	@Override
	public MemberVO getMember(String userid) {
		return dao.getMember(userid);
	}

	@Override
	public int memberUpdate(MemberVO vo) {
		return dao.memberUpdate(vo);
	}

}
