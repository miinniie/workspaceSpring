package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.DataVO;

public interface DataDAO {
	public List<DataVO> dataAllList(); //목록 전체 선택
	public int dataInsert(DataVO vo);//글추가
	public DataVO dataSelect(int no);//해당레코드 선택
	public DataVO fileSelect(int no);//파일명 구하기
	public int dataDelete(int no);//레코드 삭제
	public DataVO filenameSelect(int no);//Db의 파일명을 선택
	public int dataUpdate(DataVO vo);
}
