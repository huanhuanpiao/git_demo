package com.ql.ssm.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
	
	private Integer id;		//#
	
	@Column(name = "admini")
	private String admini;	//管理员登录名
	
	@Column(name = "pass")
	private String pass;	//管理员登录密码
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAdmini() {
		return admini;
	}
	public void setAdmini(String admini) {
		this.admini = admini;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "Admin [id=" + id + ", admini=" + admini + ", pass=" + pass + "]";
	}
	
	
}
