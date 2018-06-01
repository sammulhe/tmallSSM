package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.Category;
import pojo.Product;
import service.CategoryService;
import service.ProductService;

@Controller
@RequestMapping("")
public class ForeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	@RequestMapping("forehome")
	public String home(Model model){
		List<Category> categorys = categoryService.listCategory();
		for(Category category : categorys){
			Product product = new Product();
			product.setCid(category.getId());
			List<Product> products = productService.listProduct(product);
			category.setProducts(products);
		}
		
		model.addAttribute("categorys",categorys);
		
		return "home.jsp";
	}
}
