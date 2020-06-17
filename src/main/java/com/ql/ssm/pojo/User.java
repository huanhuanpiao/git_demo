package com.ql.ssm.pojo;

public class User {

	private Integer id;			//#
	private Integer account;	//账号
	private String password;	//用户密码
	private String pay;			//支付宝号
	private String username;	//用户名
	private String introducer1;	//推荐人用户名

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIntroducer1() {
		return introducer1;
	}
	public void setIntroducer1(String introducer1) {
		this.introducer1 = introducer1;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", pay=" + pay + ", username="
				+ username + ", introducer1=" + introducer1 + "]";
	}

}
