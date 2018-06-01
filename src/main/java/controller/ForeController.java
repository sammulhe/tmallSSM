package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pojo.Category;
import pojo.Product;
import pojo.ProductImage;
import pojo.PropertyValue;
import pojo.Review;
import pojo.User;
import service.CategoryService;
import service.ProductImageService;
import service.ProductService;
import service.PropertyService;
import service.PropertyValueService;
import service.ReviewService;
import service.UserService;

@Controller
@RequestMapping("")
public class ForeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	PropertyService propertyService;
	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	ReviewService reviewService;
	
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
	
	@RequestMapping("forelogin")
	public String login(Model model, User u, HttpSession session){
		User user = userService.loginUser(u);
		if(user == null){
			model.addAttribute("msg","账号密码错误");
			return "login.jsp";
		}
		session.setAttribute("user", user);
		return "redirect:/forehome";
	}
	
	
	@RequestMapping("foreregister")
	public String register(Model model, User user, HttpSession session){
		userService.registerUser(user);
		session.setAttribute("user", user);
		
		return "registerSuccess.jsp";
	}
	
	@RequestMapping("forecheckUserName")
	public void checkUserName(HttpServletResponse response,User user) throws IOException{
		if(userService.checkUsername(user.getUsername())){
			response.getWriter().print("true");		 //用println在网页端识别不了	
			return;
		}
		response.getWriter().print("false");
		return ;
	}
	
	@RequestMapping("forelogout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:/forehome";
	}
	
	@RequestMapping("foreproduct")
	public String product(HttpServletRequest request){
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productService.getProduct(pid); //获取product的salecount，reviewcount等等
		
		//获取ProductSingleImages
		ProductImage productImage = new ProductImage();
		productImage.setPid(pid);
		productImage.setType("Single");
		List<ProductImage> productImages = productImageService.listByType(productImage);	
		product.setProductSingleImages(productImages);
		
		//获取ProductDetailImages
		productImage.setType("Detail");
		List<ProductImage> productDetailImages = productImageService.listByType(productImage);
		product.setProductDetailImages(productDetailImages);
		
		//获取propertyValue
		List<PropertyValue> propertyValues = propertyValueService.getPropertyValueByPid(pid);
		
		//获取reviews
		List<Review> reviews = reviewService.listByPid(pid);
		
		request.setAttribute("product", product);
		request.setAttribute("propertyValues", propertyValues);
		request.setAttribute("reviews", reviews);
		
		return "product.jsp";
	}
}
