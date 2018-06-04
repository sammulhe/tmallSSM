package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pojo.Category;
import pojo.OrderItem;
import pojo.Product;
import pojo.ProductImage;
import pojo.PropertyValue;
import pojo.Review;
import pojo.User;
import service.CategoryService;
import service.OrderItemService;
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
	@Autowired
	OrderItemService orderItemService;
	
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
	
	@RequestMapping("forecheckLogin")
	public void checkLogin(HttpSession session, HttpServletResponse response) throws IOException{
	
		if(session.getAttribute("user") != null){
			response.getWriter().print("success");
			return;
		}
		response.getWriter().print("fail");
		return;
	}
	
	@RequestMapping("foreloginAjax")
	public void loginAjax(HttpSession session, HttpServletResponse response, User u) throws IOException{
		User user = userService.loginUser(u);
		if(null != user){
			session.setAttribute("user", user);
			response.getWriter().print("success");
			return;
		}
		response.getWriter().print("fail");
		return;
	}
	
	@RequestMapping("forecategory")                                   //获取cid
	public String category(Model model,Product product, HttpServletRequest request){
		Category category = new Category();
		category.setId(product.getCid());
		category = categoryService.getOne(category);
		
		//获取products
		List<Product> products = productService.listProduct(product);
		category.setProducts(products);	
		String sort = request.getParameter("sort");
		if(sort != null){
			switch (sort) {
            case "review":
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                break;
            case "date":
                Collections.sort(category.getProducts(), (p1, p2) -> p1.getCreateDate().compareTo(p2.getCreateDate()));
                break;
            case "saleCount":
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                break;
            case "price":
                Collections.sort(category.getProducts(), (p1, p2) -> (int) (p1.getPromotePrice() - p2.getPromotePrice()));
                break;
            case "all":
                Collections.sort(category.getProducts(), (p1, p2) -> p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount());
                break;
            default:
                break;	
		}
		}
		
		model.addAttribute("category",category);
		
		return "category.jsp";		
	}
	
	
	@RequestMapping("foresearch")
	public String search(HttpServletRequest request){
		String keyword = request.getParameter("keyword");
		
		List<Product> products = productService.search(keyword);
		
		request.setAttribute("products", products);
		
		return "searchResult.jsp";
	}
	
	
	@RequestMapping("forebuyone")
	public String buyone(HttpSession session, Model model, @RequestParam("pid") int pid, @RequestParam("num") int num){
		Product product = productService.getProduct(pid);
		User user = (User) session.getAttribute("user");
		
		
		OrderItem orderItem = new OrderItem();
		orderItem.setPid(product.getId());
		orderItem.setUid(user.getId());
		orderItem.setNumber(num);
		orderItem.setProduct(product);
		orderItemService.addOrderItem(orderItem);
		
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(orderItem);
		
		model.addAttribute("orderItems",orderItems);
		
		return "buy.jsp";
	}
	
}
