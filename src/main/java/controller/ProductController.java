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
import pojo.Product;
import service.CategoryService;
import service.ProductService;

@Controller
@RequestMapping("")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("admin_product_list")
	public String list(Model model, Product product, Page page){	
		//使用PageHelper实现分页
		PageHelper.offsetPage(page.getStart(),5); //规定每页显示多少
		page.setCount(5);   //规定每页显示多少
		List<Product> products = productService.listProduct(product);
		int total = (int) new PageInfo<>(products).getTotal(); 
        page.caculateLast(total);
		
		Category category = new Category();
		category.setId(product.getCid());
		category = categoryService.getOne(category);
		
		model.addAttribute("category",category);
		model.addAttribute("products",products);
		
		return "admin/listProduct.jsp";
	}
	
	@RequestMapping("admin_product_add")
	public String add(Product product){
		productService.addProduct(product);
		
		return "redirect:/admin_product_list?cid="+product.getCid()+"";
	}
	
	@RequestMapping("admin_product_edit")
	public String edit(Model model,Product product){
		product = productService.getProduct(product.getId());
		
		model.addAttribute("product",product);
		
		return "admin/editProduct.jsp";
	}
	
	@RequestMapping("admin_product_update")
	public String update(Product product){
		productService.updateProduct(product);
		
		return "redirect:/admin_product_edit?id="+product.getId()+"";
	}
	
	@RequestMapping("admin_product_delete")
	public String delete(Product product){		
		product = productService.getProduct(product.getId());
		productService.deleteProduct(product);
		
		return "redirect:/admin_product_list?cid="+product.getCid()+"";
	}
}
