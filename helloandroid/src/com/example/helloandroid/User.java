package com.example.helloandroid;

public class User {
	private String username;
	private String password;
	private String phonenum;
	public void setusername(String temp) {
		username = temp;
	}
	public void setpassword(String temp) {
		password = temp;
	}
	public void setphonenum(String temp) {
		phonenum = temp;
	}
	
	public String getusername() {
		return username;
	}
	public String getpassword() {
		return password;
	}
	public String getphonenum() {
		return phonenum;
	}
	public User() {
		this.username = "";
		this.password = "";
		this.phonenum = "";
	}
	public User(String userName, String passWord, String pn) {
			this.username = userName;
			this.password = passWord;
			this.phonenum = pn;
			}
	@Override
	public String toString() {
	// TODO Auto-generated method stub
	return "{\"username\":\""+this.username+"\", \"password\":\""+this.password+"\", \"phonenum\":\""
	+this.phonenum+"\"}";
	}
}