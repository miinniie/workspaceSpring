package com.hanwha.myapp.service;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanwha.myapp.dao.BoardDAO;
import com.hanwha.myapp.vo.BoardVO;
import com.hanwha.myapp.vo.pagingVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	BoardDAO dao;
	
	@Override
	public int boardWriteOk(BoardVO vo) {
		return dao.boardWriteOk(vo);
	}

	@Override
	public int totalRecord(pagingVO pVo) {
		return dao.totalRecord(pVo);
	}

	@Override
	public List<BoardVO> boardList(pagingVO pVO) {
		return dao.boardList(pVO);
	}

	@Override
	public int boardHitCount(int no) {
		return dao.boardHitCount(no);
	}

	@Override
	public BoardVO boardView(int no) {
		return dao.boardView(no);
	}

	@Override
	public int boardEditOk(BoardVO vo) {
		return dao.boardEditOk(vo);
	}

	@Override
	public int boardDel(int no, String userid) {
		return dao.boardDel(no, userid);
	}
	
}
