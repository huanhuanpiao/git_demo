package com.ql.ssm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/*import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;*/
import com.ql.ssm.pojo.Admin;
import com.ql.ssm.pojo.Person;
import com.ql.ssm.pojo.User;
import com.ql.ssm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// admin对象转JSON字符串 :  JSON.toJSONString(admin) :
	// JSON字符串转admin对象 :  JSON.parseObject(str, Admin.class)
	// JSON字符串转list对象 : List<Admin> admin = JSON.parseArray(str, Admin.class)

	// ----------------------------------------------------------------------------
	// JS 中的
		
	// 如何判断 Json字符串和Json对象 : Json字符串的 KEY 都加了双引号，Json对象(数组)的 KEY 没有双引号

	// Json对象(数组也是对象)转Json字符串: JSON.stringify(Json数组名/Json对象名)
	// Json字符串转Json对象(数组也是对象): JSON.parse(Json字符串)
	
	
	
	// map集合里添加list，返回map到AJAX
	@ResponseBody
	@RequestMapping(value = "/toParticulars",produces = "text/html; charset=UTF-8")
    public String toParticulars(@RequestParam Integer id, @RequestParam String admini, HttpServletRequest request, Model model, HttpServletResponse response){//参数传入对象
		
		System.out.println(id);
		System.out.println(admini);		
		
		List<Admin> admin = userService.selall();
		//System.out.println(getType(admin));		
		
		Map<String,String> map = new HashMap<String,String>();
		//System.out.println(HashMap.MIN_TREEIFY_CAPACITY);
		
		// String.valueOf() : Integer 转 String 类型
        map.put("id", String.valueOf(id));
		map.put("NAME", admini);
		map.put("admin", JSON.toJSONString(admin));
		System.out.println(map);		
		return JSON.toJSONString(map);
	
    }
	
	
	// 分页查询
	@ResponseBody
	@RequestMapping(value = "/indexe")
	public String toindexe(ModelMap map, @RequestParam(defaultValue = "1", required = true, value = "pageNo") Integer pageNo, @RequestParam(defaultValue = "1", required = true, value = "pageNum") Integer pageNum, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println(pageNo);

		// 每页显示记录数
		Integer pageSize = 4;

		// 分页查询
		PageHelper.startPage(pageNo, pageSize);

		// 获取所有用户信息
		List<Admin> userList = userService.selall();
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(userList, pageSize);
		return JSON.toJSONString(pageInfo);
	}
	
	
	// 查询所有管理员
	@ResponseBody
	@RequestMapping(value = "/selall", /* method=RequestMethod.POST, */produces = "text/html; charset=UTF-8")
	public String selall(/* @RequestParam String admini, */HttpServletRequest request, HttpServletResponse response, Model model) {

		List<Admin> admin = userService.selall();
		
		System.out.println(admin);
		/*Map<String,String> map = new HashMap<String,String>();
		//System.out.println(HashMap.MIN_TREEIFY_CAPACITY);
		
		// String.valueOf() : Integer 转 String 类型
		map.put("id", "22");
		map.put("NAME", "33");
		map.put("admin", JSON.toJSONString(admin));
		System.out.println(map);*/		
		return JSON.toJSONString(admin);
	}

		
	// 增加管理员
	@ResponseBody
	@RequestMapping(value = "/admin")
	public ModelAndView admin(@RequestParam String admini, @RequestParam String pass, HttpServletRequest request, Model model, Admin admin, HttpServletResponse response) throws IOException {

		System.out.println(admini);
		System.out.println(admin);
		
		userService.admin(admin);
		ModelAndView mv = new ModelAndView("redirect:/js.html");
		return mv;
	}
	
	
	// ID 删除管理员
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam Integer id, HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {

		System.out.println(id);

		userService.delete(id);
		ModelAndView mv = new ModelAndView("redirect:/js.html");
		return mv;
	}
	
	
	// 修改
	@ResponseBody
	@RequestMapping(value = "/update")
	public ModelAndView edit(@RequestParam Integer id, @RequestParam String admini, @RequestParam String pass, HttpServletRequest request, Model model, Admin admin, HttpServletResponse response) {
		userService.update(admin);
		ModelAndView mv = new ModelAndView("redirect:/js.html");
		return mv;
	}
	
	
	
	
	
	/**  
	 * 文件上传功能  
	* @param file  
	* @return  
	* @throws IOException   
	*/ 
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");
		
		// res : 2020-05-18-16-16-33-110
		String res = sdf.format(new Date());

		// uploads文件夹位置
		//String rootPath = request.getSession().getServletContext().getRealPath("WEB-INF/upload");
		
		// rootPath  :  C:\TP\workspace2\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\ssm9\WEB-INF
		//String rootPath = request.getSession().getServletContext().getRealPath("WEB-INF");
		String rootPath = "C:\\Game";
		System.out.println(rootPath);
		
		// 原始名称
		String originalFileName = file.getOriginalFilename();
		
		// 获取原文件名称的 . 最后一次出现的位置
		System.out.println(originalFileName.lastIndexOf("."));
		
		// 获取文件后缀名
		System.out.println(originalFileName.substring(originalFileName.lastIndexOf(".")));
		
		// 新文件名   newFileName :  例如  sliver2020-05-18-16-16-33-110.txt  ,  res : 2020-05-18-16-16-33-110 , originalFileName.substring(originalFileName.lastIndexOf(".")) : .txt
		String newFileName = "sliver" + res + originalFileName.substring(originalFileName.lastIndexOf("."));
		
		
		// 创建年月文件夹
		Calendar date = Calendar.getInstance();
		System.out.println(date);	// date : java.util.GregorianCalendar[
		
		
		// 文件存放位置
		// 文件存放位置 ："C:\\Game\\年y\\月   " dateDirs : 2020\5  ,  date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1) :  2020\5
		// date.get(Calendar.YEAR) : 2020  ,  File.separator : \  ,   date.get(Calendar.MONTH + 1) :  当月月份 5 
		File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1));

		// 新文件  rootPath :  C:\TP\workspace2\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\ssm9\WEB-INF ,  dateDirs : 2020\5
		File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
		
		// 判断目标文件所在目录是否存在
		if (!newFile.getParentFile().exists()) {
			
			// 如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}
		
		// C:\TP\workspace2\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\ssm9\WEB-INF\2020\5\sliver2020-05-18-17-43-34-139.txt
		System.out.println(newFile);
		
		// 将内存中的数据写入磁盘
		System.out.println(file);
		file.transferTo(newFile);
		
		// 完整的url :  2020/5/sliver2020-05-18-16-16-33-110.txt
		String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + newFileName;
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("path", fileUrl);
		return mv;
	}
	
	
	
	
	/**  
     * 文件下载功能  
     * @param request  
     * @param response  
     * @throws Exception  
     */  
    @SuppressWarnings("resource")
	@RequestMapping("/down")  
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{  
    	
        // 需要下载的文件  :  sliver2020-05-18-17-51-51-25.txt
        //String fileName = request.getSession().getServletContext().getRealPath("WEB-INF/upload/2020/1")+"/sliver20200115164910583.txt";  
    	String fileName = "C:\\Game\\2020\\5\\"+"sliver2020-05-18-17-51-51-25.txt"; 
    	
        //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
        
        //假如以中文名下载的话  
        String filename = "下载文件.txt";  
        
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"UTF-8");  
        
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
    }
		
	

	
	// 重定向到index.html
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView toindex(HttpServletRequest request, Model model, Admin admin, HttpServletResponse response) throws IOException {

		ModelAndView mv = new ModelAndView("redirect:/index.html");
		return mv;
	}
	

	public static String getType(Object o) { // 获取变量类型方法
		return o.getClass().toString(); // 使用int类型的getClass()方法
	}
	 

	// 模糊查询管理员
	@ResponseBody
	@RequestMapping(value = "/mohu")
	public String mohu(@RequestParam String admini, HttpServletRequest request, Model model, HttpServletResponse response) {

		System.out.println(admini);

		List<Admin> list = userService.mohu(admini);

		return JSON.toJSONString(list);
	}

	
	// 接收前端ajax传来的数据
	@ResponseBody
	@RequestMapping(value = "/see")
	public ModelAndView see(@RequestParam String val1, HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {

		System.out.println(val1);

		ModelAndView mv = new ModelAndView("redirect:/1-2.html");
		return mv;
	}

	// 查询分页对象
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/findPage") public PageResult findPage(Integer
	 * pageNum,Integer pageSize){ return userService.findPage(pageNum, pageSize); }
	 */

	

	// 管理员登录验证
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	public ModelAndView logincheck(@RequestParam String admini, @RequestParam String pass, HttpServletRequest request, Model model) {

		if (userService.admincheck(admini, pass) == null || userService.admincheck(admini, pass) == "") {

			ModelAndView mv = new ModelAndView("redirect:/regist.html");
			return mv;

		} else {
			ModelAndView mv = new ModelAndView("redirect:/wan.html");
			return mv;
		}
	}

	
	
	// 文件复制并存放
	/*
	 * @RequestMapping("/index1") public String index1(String formData, String
	 * url,HttpServletRequest request,Model model) throws IOException {
	 * System.out.println(formData); System.out.println(url);
	 * 
	 * // 文件来源 File file = new File("D:/pic/33.jpg"); // 文件存放目标地址及名称 File toFile =
	 * new File("D:/pic/hh2.jpg");
	 * 
	 * try { copy(file, toFile); } catch (Exception e) { 
	 * catch block e.printStackTrace(); }
	 * 
	 * model.addAttribute("url", url); model.addAttribute("file", file); return
	 * "index1"; }
	 */

		// copy方法，供index1()调用
		public static void copy(File file, File toFile) throws Exception {
			byte[] b = new byte[1024];
			int a;
			FileInputStream fis;
			FileOutputStream fos;
			if (file.isDirectory()) {
				String filepath = file.getAbsolutePath();
				filepath = filepath.replaceAll("\\\\", "/");
				String toFilepath = toFile.getAbsolutePath();
				toFilepath = toFilepath.replaceAll("\\\\", "/");
				int lastIndexOf = filepath.lastIndexOf("/");
				toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());
				File copy = new File(toFilepath);
				// 复制文件夹
				if (!copy.exists()) {
					copy.mkdir();
				}
				// 遍历文件夹
				for (File f : file.listFiles()) {
					copy(f, copy);
				}
			} else {
				if (toFile.isDirectory()) {
					String filepath = file.getAbsolutePath();
					filepath = filepath.replaceAll("\\\\", "/");
					String toFilepath = toFile.getAbsolutePath();
					toFilepath = toFilepath.replaceAll("\\\\", "/");
					int lastIndexOf = filepath.lastIndexOf("/");
					toFilepath = toFilepath + filepath.substring(lastIndexOf, filepath.length());

					// 写文件
					File newFile = new File(toFilepath);
					fis = new FileInputStream(file);
					fos = new FileOutputStream(newFile);
					while ((a = fis.read(b)) != -1) {
						fos.write(b, 0, a);
					}
				} else {
					// 写文件
					fis = new FileInputStream(file);
					fos = new FileOutputStream(toFile);
					while ((a = fis.read(b)) != -1) {
						fos.write(b, 0, a);
					}
				}
			}
		}
	
	
	
	// -----------------------------------------------------------------------------------------------------

	
	
	// 跳转到about页面
	@RequestMapping("/about")
	public String about(HttpServletRequest request, Model model) {

		return "about";
	}

	// 跳转到ny.jsp
	@RequestMapping("/ny")
	public String ny(HttpServletRequest request, Model model) {

		return "ny";
	}

	// 跳转到list.jsp
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {

		return "list";
	}

	// 跳转到index2.jsp
	/*
	 * @RequestMapping("/index") public String index(HttpServletRequest request,
	 * Model model, HttpSession session) {
	 * 
	 * String username = "閮槦"; Person person = userService.self(username);
	 * model.addAttribute("person", person); return "index2"; }
	 */

	// 璺宠浆鍒扮櫥褰曠被鍨嬮�夋嫨椤甸潰
	@RequestMapping("/index1-2")
	public String index1(HttpServletRequest request, Model model, HttpSession session) {

		String username = "閮槦";
		Person person = userService.self(username);
		model.addAttribute("person", person);
		return "index3";
	}

	// 娉ㄥ唽椤电敤鎴峰悕ajax楠岃瘉
	@RequestMapping("/passche")
	public void emailCheck2(String username, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		Person person2 = userService.self(username);

		if (person2 != null) {
			out.println("鐢ㄦ埛鍚嶅凡缁忓瓨鍦�");
		} else {
			out.println("鐢ㄦ埛鍚嶉獙璇侀�氳繃");
		}
	}

	// 娉ㄥ唽椤垫帹鑽愪汉鏁伴檺鍒禷jax楠岃瘉
	@RequestMapping("/introche")
	public String introche(@Param("introducer1") String introducer1, HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		Integer intro = userService.selnum(introducer1);
		if (intro >= 3) {
			System.out.println(userService.selnum(introducer1));
			out.println("鎺ㄨ崘澶熶簡锛岃鏇存崲鎺ㄨ崘浜�");
			out.flush();
			out.close();
			return "zhuce";
		} else {
			out.println("鎺ㄨ崘浜洪獙璇侀�氳繃");
			out.flush();
			out.close();
			return "login";
		}

	}

	// 璺宠浆鍒扮敤鎴风櫥褰曢〉闈�
	@RequestMapping("/tologin")
	public String tologin(HttpServletRequest request, Model model) {

		return "login";
	}

	// 娉ㄥ唽锛岀劧鍚庤烦杞� 鍒扮櫥褰曢〉闈�
	@RequestMapping("/saveP")
	public String saveP(@RequestParam("username") String username, @RequestParam("introducer1") String introducer1, HttpServletRequest request, Model model, User user, HttpServletResponse response, Person person) throws IOException {

		Person person1 = userService.self(username);

		if (person1 == null) {

			userService.updatenum(introducer1);
			userService.saveperson(person);
			userService.saveP(user);

			Person person3 = userService.selcheck(introducer1);

			if (person3.getNumber() == 3) {
				userService.updateche(introducer1);
				return "login";
			}
			return "login";

		} else {

			return "zhuce";
		}
	}

	// 绠＄悊鍛樼櫥褰曢獙璇�
	@RequestMapping("/adminlogin")
	public String adminlogin(HttpServletRequest request, Model model) {

		String admini = request.getParameter("admini");
		String pass = request.getParameter("pass");

		if (userService.admincheck(admini, pass) == null || userService.admincheck(admini, pass) == "") {

			model.addAttribute("error", "璐﹀彿鎴栧瘑鐮侀敊璇�");
			return "adminlogin";

		} else {

			model.addAttribute("admini", admini);
			return "redirect:/user/selacc";
		}
	}

	// 绠＄悊鍛樼櫥褰曞悗閲嶅畾鍚戝埌杩欓噷鏌ュ嚭person琛ㄦ墍鏈変俊鎭�
	@RequestMapping("/selacc")
	public String selacc(HttpServletRequest request, Model model) {

		List<Person> person = userService.selacc();
		model.addAttribute("person", person);
		System.out.println(person);
		return "admin";
	}

	// 鐢ㄦ埛鐧诲綍楠岃瘉
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, HttpSession session) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (userService.logincheck(username, password) == null || userService.logincheck(username, password) == "") {
			model.addAttribute("error", "璐﹀彿鎴栧瘑鐮侀敊璇�");
			return "login";
		} else {
			model.addAttribute("username", username);
			request.getSession().setAttribute("username", username);
			return "redirect:/user/self";
		}
	}

	// 鐢ㄦ埛鐧诲綍鍚庣殑椤甸潰鏌ョ湅鑷繁鐨勪俊鎭�
	@RequestMapping("/self")
	public String self(HttpServletRequest request, Model model, HttpSession session) {

		String username = (String) session.getAttribute("username");
		Person person = userService.self(username);
		model.addAttribute("person", person);
		System.out.println(person);
		return "self";
	}

}
