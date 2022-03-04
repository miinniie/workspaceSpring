package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.BoardVO;
import com.hanwha.myapp.vo.pagingVO;

public interface BoardDAO {
	public int boardWriteOk(BoardVO vo);
	public int totalRecord(pagingVO pVo); //총레코드수구하는 메소드
	public List<BoardVO> boardList(pagingVO pVO);//해당페이지 레코드 선택
	public int boardHitCount(int no);//조회수 증가
	public BoardVO boardView(int no);//해당 레코드 선택
	public int boardEditOk(BoardVO vo);//수정
	public int boardDel(int no, String userid);//삭제
}
