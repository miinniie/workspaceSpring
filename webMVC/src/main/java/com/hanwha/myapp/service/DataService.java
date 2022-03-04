package com.hanwha.myapp.service;

import java.util.List;

import com.hanwha.myapp.vo.DataVO;

public interface DataService {
	
	public List<DataVO> dataAllList();
	public int dataInsert(DataVO vo);
	public DataVO dataSelect(int no);
	public DataVO fileSelect(int no);
	public int dataDelete(int no);
	public DataVO filenameSelect(int no);
	public int dataUpdate(DataVO vo);
}
