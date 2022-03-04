package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.ReplyVO;

public interface ReplyDAO {
	public int replyInsert(ReplyVO vo);
	public List<ReplyVO> replyAllSelect(int no);//댓글가져오기
	public int replyUpdate(ReplyVO vo);//댓글수정하기
	public int replyDelete(int replyno);//댓글삭제하기
}
