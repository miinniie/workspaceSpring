package com.hanwha.myapp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanwha.myapp.dao.DataDAO;
import com.hanwha.myapp.vo.DataVO;

@Service
public class DataServiceImpl implements DataService {

	@Inject
	DataDAO dao;
	
	@Override
	public List<DataVO> dataAllList() {
		return dao.dataAllList();
	}

	@Override
	public int dataInsert(DataVO vo) {
		return dao.dataInsert(vo);
	}

	@Override
	public DataVO dataSelect(int no) {
		return dao.dataSelect(no);
	}

	@Override
	public DataVO fileSelect(int no) {
		return dao.fileSelect(no);
	}

	@Override
	public int dataDelete(int no) {
		return dao.dataDelete(no);
	}

	@Override
	public DataVO filenameSelect(int no) {
		return dao.filenameSelect(no);
	}

	@Override
	public int dataUpdate(DataVO vo) {
		return dao.dataUpdate(vo);
	}

}
