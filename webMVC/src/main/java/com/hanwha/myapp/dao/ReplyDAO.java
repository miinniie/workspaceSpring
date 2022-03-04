package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.ReplyVO;

public interface ReplyDAO {
	public int replyInsert(ReplyVO vo);
	public List<ReplyVO> replyAllSelect(int no);//��۰�������
	public int replyUpdate(ReplyVO vo);//��ۼ����ϱ�
	public int replyDelete(int replyno);//��ۻ����ϱ�
}
