package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pojo.Category;
import pojo.Page;
import service.CategoryService;
import util.FileUtil;

//分类的相关操作
@Controller
@RequestMapping("")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	//分类管理
	@RequestMapping("admin_category_list")
	public String list(Model model, Page page){
		
		//使用PageHelper实现分页
		PageHelper.offsetPage(page.getStart(),10); //规定每页显示多少
		page.setCount(10);   //规定每页显示多少
        List<Category> categorys= categoryService.listCategory();
        int total = (int) new PageInfo<>(categorys).getTotal(); 
        page.caculateLast(total);
        
	    model.addAttribute("categorys",categorys);
	    return "admin/listCategory.jsp";
	}
	
	@RequestMapping("admin_category_add")
	public  ModelAndView add(HttpServletRequest request,Category category){
		ModelAndView mav = new ModelAndView();
		categoryService.addCategory(category); //添加到数据库，同时返回category的id

		//String photoFolder = request.getServletContext().getRealPath("/img/category");  因为项目已部署到tomcat，会把文件保存到tomcat下，而不是在项目中
		//为了在eclipse方便开发，直接存在项目中，之后发布到服务器还是要改会上面的语句，保存到服务器端
		String path = "D:\\workspace\\tmallSSM\\src\\main\\webapp\\img\\category\\";			
		FileUtil.upload(request, path, category.getId());
		
		mav.setViewName("redirect:/admin_category_list");	
		return mav;
	}
	
	@RequestMapping("admin_category_edit")
	public String edit(Model model, Category category){
		category = categoryService.getOne(category);
		model.addAttribute("category",category);
		
		return "admin/editCategory.jsp";
	}
	
	@RequestMapping("admin_category_update")
	public String update(HttpServletRequest request, Category category){
		categoryService.updateCategory(category);
		
		String path = "D:\\workspace\\tmallSSM\\src\\main\\webapp\\img\\category\\";			
		FileUtil.upload(request, path, category.getId());
		
		return "redirect:/admin_category_list";
	}
	
	@RequestMapping("admin_category_delete")
	public String delete(Category category){
		categoryService.deleteCategory(category);
		
		return "redirect:/admin_category_list";
	}
}
