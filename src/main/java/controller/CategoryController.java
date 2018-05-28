package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.Category;
import service.CategoryService;

@Controller
@RequestMapping("")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_category_list")
	public String list(Model model){
		List<Category> categories = categoryService.listCategory();
	    model.addAttribute("categories",categories);
	    return "registerSuccess.jsp";
	}
	
	@RequestMapping("home")
	public String home(){
		return "index.jsp";
	}
}
