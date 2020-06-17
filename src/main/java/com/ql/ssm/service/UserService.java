package com.ql.ssm.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ql.ssm.dao.UserMapper;
import com.ql.ssm.pojo.Admin;
import com.ql.ssm.pojo.Person;
import com.ql.ssm.pojo.User;

@Service
@Transactional
public class UserService {

	@Autowired
	@Resource
	private UserMapper usermapper;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Admin> mohu(String admini){
        return usermapper.mohu(admini);
    }
	
	//---------------------------------------------------------------------------------------------------
	
	// 修改一条记录
	public void update(Admin admin) {	
		
		usermapper.update(admin);
	}
	
	// ID 删除管理员
	public void delete(Integer id) {		
		usermapper.delete(id);
	}
	
	//删除管理员
	public void dela(String admini) {		
		usermapper.dela(admini);
	}
	
	// 增加管理员
	public void admin(Admin admin) {		
		admin.setAdmini("废物"+admin.getAdmini());
		System.out.println(admin);
		usermapper.admin(admin);
	}
	
	// 查询所有管理员
	public List<Admin> selall() {
		return usermapper.selall();
	}
	
	//查询分页对象
	/*public PageResult findPage(Integer pageNum, Integer pageSize){
	    //分页插件 
	    PageHelper.startPage(pageNum, pageSize);
	    //分页插件会将原sql变为select * from 表   limit 开始行,每页数
	    //查询所有品牌结果集
	    Page<Admin> page =  (Page<Admin>) usermapper.selall();
	    return new PageResult(page.getTotal(), page.getResult());
	}*/
	
	
	//------------------------------------------------------
	
	// 注册person表
	public void saveperson(Person person) {		
		usermapper.saveperson(person);
	}
	
	// 注册user表
	public void saveP(User user) {		
		usermapper.saveP(user);
	}

	//用户登录验证
	public String logincheck(String username,String password) {
		
		return usermapper.logincheck(username,password);
	}
	
	//管理员登录后重定向到这里查出person表所有信息
	public List<Person> selacc() {

		return usermapper.selacc();
	}
	
	//查询introducer1的推荐数量（查询填写的某推荐人名字在user表中introducer1字段出现的次数）
	public Integer selnum(String introducer1) {
		
		return usermapper.selnum(introducer1);
	}
	
	// 管理员登录验证
	public String admincheck(String admini,String pass) {

		return usermapper.admincheck(admini,pass);
	}
	
	//用户登录后的页面查看自己的信息
	public Person self(String username) {
		Person person = null;
		person = usermapper.self(username);
		return person;
	}
	
	//推荐人头数+1
	public void updatenum(String introducer1) {       
		
	    usermapper.updatenum(introducer1);	   
	}
	
	//查出填写的推荐人的详细信息
	public Person selcheck(String introducer1) {
		Person person = null;
		person = usermapper.selcheck(introducer1);
		return person;
	}
	
	//拉够三人的numcheck修改为‘是’
	public void updateche(String introducer1) {       
		
	    usermapper.updateche(introducer1);	   
	}
	
	

}
