package controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pojo.Page;
import pojo.User;
import service.UserService;

@Controller
@RequestMapping("")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping("admin_user_list")
	public String list(Model model, Page page){
		//使用PageHelper实现分页
		PageHelper.offsetPage(page.getStart(),2); //规定每页显示多少
		page.setCount(2);   //规定每页显示多少
		List<User> users = userService.listUser();
		int total = (int) new PageInfo<>(users).getTotal(); 
        page.caculateLast(total);
		
		model.addAttribute("users", users);
		
		return "admin/listUser.jsp";
	}
	
	
}
