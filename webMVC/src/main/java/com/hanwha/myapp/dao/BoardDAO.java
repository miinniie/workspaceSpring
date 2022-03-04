package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.BoardVO;
import com.hanwha.myapp.vo.pagingVO;

public interface BoardDAO {
	public int boardWriteOk(BoardVO vo);
	public int totalRecord(pagingVO pVo); //�ѷ��ڵ�����ϴ� �޼ҵ�
	public List<BoardVO> boardList(pagingVO pVO);//�ش������� ���ڵ� ����
	public int boardHitCount(int no);//��ȸ�� ����
	public BoardVO boardView(int no);//�ش� ���ڵ� ����
	public int boardEditOk(BoardVO vo);//����
	public int boardDel(int no, String userid);//����
}
