package com.hanwha.myapp.vo;

public class pagingVO {
	private int onePageRecord =5;//한페이지당 표시할 레코드수를 저장하는 변수 
	private int pageNum =1;//현재 페이지
	private int totalRecord;//총레코드수
	private int totalPage;//총페이지수
	private int onePageCount=5; //한번에 출력할 페이지수
	private int startPageNum=1;//시작페이지
	
	
	private int selectRecord;//페이지에 해당하는 레코드 선택
	private int pickRecord;//선택할 레코드 수
	
	//--검색어, 검색키
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
		//시작페이지 계산하기
		//				(현재페이지-1)/페이지수*페이지수+1
		startPageNum = (pageNum-1)/onePageCount*onePageCount+1;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		//총페이지수 구하기
		if(totalRecord%onePageRecord==0) {//남은 레코드가 없을때(한페이지..나머지가 없어야)
			totalPage = totalRecord/onePageRecord;
		}else {//남은 레코드가 있을때
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
