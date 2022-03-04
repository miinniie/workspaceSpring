package com.hanwha.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanwha.myapp.dao.ReplyDAO;
import com.hanwha.myapp.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	ReplyDAO dao;

	@Override
	public int replyInsert(ReplyVO vo) {
		return dao.replyInsert(vo);
	}

	@Override
	public List<ReplyVO> replyAllSelect(int no) {
		return dao.replyAllSelect(no);
	}

	@Override
	public int replyUpdate(ReplyVO vo) {
		return dao.replyUpdate(vo);
	}

	@Override
	public int replyDelete(int replyno) {
		return dao.replyDelete(replyno);
	}
}
