package com.hanwha.myapp.service;

import java.util.List;

import com.hanwha.myapp.vo.BoardVO;
import com.hanwha.myapp.vo.pagingVO;

public interface BoardService {
		public int boardWriteOk(BoardVO vo);
		public int totalRecord(pagingVO pVo);
		public List<BoardVO> boardList(pagingVO pVO);
		public int boardHitCount(int no);
		public BoardVO boardView(int no);
		public int boardEditOk(BoardVO vo);
		public int boardDel(int no, String userid);
}

