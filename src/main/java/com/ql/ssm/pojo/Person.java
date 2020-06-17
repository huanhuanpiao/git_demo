package com.ql.ssm.pojo;

public class Person {
	
	private Integer id;			//#
	private String username;	//用户名
	private Integer account;	//账号
	private String pay;			//支付宝号
	private String numcheck;	//人数验证，是/否
	private Integer number;		//推荐数量
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getNumcheck() {
		return numcheck;
	}
	public void setNumcheck(String numcheck) {
		this.numcheck = numcheck;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", account=" + account + ", pay=" + pay + ", numcheck="
				+ numcheck + ", number=" + number + "]";
	}
	
	
	
	

	
	
	
}
