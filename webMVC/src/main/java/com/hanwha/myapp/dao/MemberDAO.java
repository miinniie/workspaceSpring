package com.hanwha.myapp.dao;

import com.hanwha.myapp.vo.MemberVO;

public interface MemberDAO {
	//ȸ�����
	public int memberInsert(MemberVO vo);
	//�α���
	public MemberVO memberLogin(MemberVO vo);
	//ȸ���������� ��
	public MemberVO getMember(String userid);
	//ȸ������ ����
	public int memberUpdate(MemberVO vo);
}
