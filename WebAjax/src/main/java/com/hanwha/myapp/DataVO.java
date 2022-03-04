package com.hanwha.myapp;

public class DataVO {
	
	private int num;
	private String username;
	private String tel;
	private String addr;
	
	public DataVO() {}
	public DataVO(int num, String username, String tel, String addr) {
		this.num = num;
		this.username = username;
		this.tel = tel;
		this.addr = addr;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
}
