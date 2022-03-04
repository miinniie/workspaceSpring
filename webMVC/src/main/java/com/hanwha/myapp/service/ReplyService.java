package com.hanwha.myapp.service;

import java.util.List;

import com.hanwha.myapp.vo.ReplyVO;

public interface ReplyService {
	public int replyInsert(ReplyVO vo);
	public List<ReplyVO> replyAllSelect(int no);
	public int replyUpdate(ReplyVO vo);
	public int replyDelete(int replyno);
}
