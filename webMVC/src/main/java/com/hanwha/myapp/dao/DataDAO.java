package com.hanwha.myapp.dao;

import java.util.List;

import com.hanwha.myapp.vo.DataVO;

public interface DataDAO {
	public List<DataVO> dataAllList(); //��� ��ü ����
	public int dataInsert(DataVO vo);//���߰�
	public DataVO dataSelect(int no);//�ش緹�ڵ� ����
	public DataVO fileSelect(int no);//���ϸ� ���ϱ�
	public int dataDelete(int no);//���ڵ� ����
	public DataVO filenameSelect(int no);//Db�� ���ϸ��� ����
	public int dataUpdate(DataVO vo);
}
