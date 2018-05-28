package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pojo.Category;
import pojo.Page;
import pojo.Property;
import service.CategoryService;
import service.PropertyService;

@Controller
@RequestMapping("")
public class PropertyController {

	@Autowired
	PropertyService propertyService; 
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_property_list")
	public String list(Model model, Property property, Page page){
		//使用PageHelper实现分页
		PageHelper.offsetPage(page.getStart(),10); //规定每页显示多少
		page.setCount(10);   //规定每页显示多少
		List<Property> properties = propertyService.listProperty(property);
		int total = (int) new PageInfo<>(properties).getTotal(); 
        page.caculateLast(total);
        
        Category category = new Category();
        category.setId(property.getCid());
        category = categoryService.getOne(category);
        
		model.addAttribute("properties", properties);
		model.addAttribute("category", category);
		
		return "admin/listProperty.jsp";
	}
	
	
	@RequestMapping("admin_property_add")
	public String add(Property property){
		propertyService.addProperty(property);
		
		return "redirect:/admin_property_list?cid="+property.getCid()+"";
	}
	

	@RequestMapping("admin_property_edit")
	public String edit(Model model, Property property){
		property = propertyService.getProperty(property);		
		Category category = new Category();
		category.setId(property.getCid());
		property.setCategory(categoryService.getOne(category));
		
		model.addAttribute("property",property);
		
		return "admin/editProperty.jsp";
	}
	
	
	@RequestMapping("admin_property_update")
	public String update(Property property){
		propertyService.updateProperty(property);
		
		return "redirect:/admin_property_list?cid="+property.getCid()+"";
	}
	
	@RequestMapping("admin_property_delete")
	public String delete(Property property){
		//获取property的cid
		property = propertyService.getProperty(property);
		propertyService.deleteProperty(property);
		
		return "redirect:/admin_property_list?cid="+property.getCid()+"";
	}
}
