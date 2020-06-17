package com.ql.ssm.dao;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ql.ssm.pojo.Admin;
import com.ql.ssm.pojo.PageResult;
import com.ql.ssm.pojo.Person;
import com.ql.ssm.pojo.User;
 
public interface UserMapper {
	
	public PageResult findPage(Integer pageNum, Integer pageSize);
	

	
	
	
	
	
	
	
	
	
	//根据name字段里的任意一个值, 进行模糊查询整条记录
	public List<Admin> mohu(@Param("admini")String admini);
	
	//------------------------------------------------------------------------------------------
	
	// 修改管理员
	public void update(Admin admin); 
	
	// ID 删除管理员
	public void delete(@Param("id")Integer id);
	
	//删除管理员
	public void dela(String admini);
			
	//增加管理员
	public void admin(Admin admin);
	
	//查询所有管理员
	public List<Admin> selall();
	
	//-------------------------------------------------------------------------------------------
	
	//注册person表
	public void saveperson(Person person);
	
	//注册user表
	public void saveP(User user);
		
	//用户登录验证
	String logincheck(@Param("username")String username,@Param("password")String password);
	
	//管理员登录后重定向到这里查出person表所有信息
	public List<Person> selacc();
	
	//查询填写的某推荐人在user表中introducer1字段中出现的次数
	public Integer selnum(@Param("introducer1")String introducer1);
	
	//管理员登录验证
	String admincheck(@Param("admini")String admini,@Param("pass")String pass);
	
	//用户登录后的页面查看自己的信息
	public Person self(@Param("username")String username);
	
	//修改推荐人头数
	public void updatenum(@Param("introducer1")String introducer1);
	
	//查出所填写的推荐人的所有信息
	public Person selcheck(@Param("introducer1")String introducer1);
	
	//拉够三人的numcheck修改为‘是’
	public void updateche(@Param("introducer1")String introducer1);
	
	
	
	
	
}
