package com.hanwha.myapp.vo;

public class pagingVO {
	private int onePageRecord =5;//���������� ǥ���� ���ڵ���� �����ϴ� ���� 
	private int pageNum =1;//���� ������
	private int totalRecord;//�ѷ��ڵ��
	private int totalPage;//����������
	private int onePageCount=5; //�ѹ��� ����� ��������
	private int startPageNum=1;//����������
	
	
	private int selectRecord;//�������� �ش��ϴ� ���ڵ� ����
	private int pickRecord;//������ ���ڵ� ��
	
	//--�˻���, �˻�Ű
	private String searchKey;
	private String searchWord;
	
	
	public int getOnePageRecord() {
		return onePageRecord;
	}
	public void setOnePageRecord(int onePageRecord) {
		this.onePageRecord = onePageRecord;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		//���������� ����ϱ�
		//				(����������-1)/��������*��������+1
		startPageNum = (pageNum-1)/onePageCount*onePageCount+1;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		//���������� ���ϱ�
		if(totalRecord%onePageRecord==0) {//���� ���ڵ尡 ������(��������..�������� �����)
			totalPage = totalRecord/onePageRecord;
		}else {//���� ���ڵ尡 ������
			totalPage = (totalRecord/onePageRecord)+1;
		}
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getSelectRecord() {
		selectRecord = pageNum * onePageRecord;
		return selectRecord;
	}
	public void setSelectRecord(int selectRecord) {
		this.selectRecord = selectRecord;
	}
	public int getOnePageCount() {
		return onePageCount;
	}
	public void setOnePageCount(int onePageCount) {
		this.onePageCount = onePageCount;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public int getPickRecord() {
		int modRecord = totalRecord%onePageRecord;
		if(pageNum==totalPage && modRecord!=0 ) {
			pickRecord = modRecord;
		}else {
			pickRecord = onePageRecord;
		}
		return pickRecord;
	}
	public void setPickRecord(int pickRecord) {
		this.pickRecord = pickRecord;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	
}
