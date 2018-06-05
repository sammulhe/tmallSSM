package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import pojo.Order;
import pojo.OrderItem;
import pojo.Product;
import pojo.ProductImage;
import pojo.PropertyValue;
import pojo.Review;
import pojo.User;
import service.CategoryService;
import service.OrderItemService;
import service.OrderService;
import service.ProductImageService;
import service.ProductService;
import service.PropertyService;
import service.PropertyValueService;
import service.ReviewService;
import service.UserService;
import util.DateUtil;

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
	@Autowired
	OrderService orderService;
	
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
		session.setAttribute("cartTotalItemNumber", orderItemService.getCartNum(user.getId()));
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
			session.setAttribute("cartTotalItemNumber", orderItemService.getCartNum(user.getId()));
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
		Integer cartTotalItemNumber = (Integer) session.getAttribute("cartTotalItemNumber");
		
		OrderItem orderItem = new OrderItem();
		orderItem.setPid(product.getId());
		orderItem.setUid(user.getId());
		orderItem.setNumber(num);
		orderItem.setProduct(product);
		orderItemService.updateOrderItem(orderItem);
		
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(orderItem);
		
		float total = num * product.getPromotePrice();
		
		session.setAttribute("orderItems", orderItems);
		session.setAttribute("total",total);		
		if(null == cartTotalItemNumber){
			session.setAttribute("cartTotalItemNumber", num);
		}else{
			session.setAttribute("cartTotalItemNumber", cartTotalItemNumber + num);
		}
		
		return "buy.jsp";
	}
	
	@RequestMapping("forebuy")
	public String buy(HttpServletRequest request){
		String[] oiids = request.getParameterValues("oiid");
		
		List<OrderItem> orderItems = new ArrayList<>();
		float total = 0;
		for(String oi : oiids){
			int oiid = Integer.parseInt(oi);
			OrderItem orderItem = orderItemService.getOrderItem(oiid);
			Product product = productService.getProduct(orderItem.getPid());
			orderItem.setProduct(product);
			
			total = total + orderItem.getNumber() * product.getPromotePrice();			
			orderItems.add(orderItem);
		}
		
		request.getSession().setAttribute("total", total);
		request.getSession().setAttribute("orderItems", orderItems);
		
		return "buy.jsp";
	}
	
	
	@RequestMapping("foreaddCart")
	public void addCart(HttpServletRequest request,HttpServletResponse response, @RequestParam("pid") int pid, @RequestParam("num") int num) throws IOException{
		User user = (User) request.getSession().getAttribute("user");
		int uid = user.getId();
		Integer cartTotalItemNumber = (Integer) request.getSession().getAttribute("cartTotalItemNumber");
		
		OrderItem orderItem = new OrderItem();
		orderItem.setPid(pid);
		orderItem.setUid(uid);
		orderItem.setNumber(num);
		orderItemService.updateOrderItem(orderItem); //在update中调用了exist判断是否有能合并的订单项存在，存在就合并，否则插入
		
		if(null == cartTotalItemNumber){
			request.getSession().setAttribute("cartTotalItemNumber", num);
		}else{
			request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber + num);
		}
		
		response.getWriter().print("success");
		return;
	}
	
	
	@RequestMapping("forecart")
	public String cart(HttpSession session,Model model){
		User user = (User) session.getAttribute("user");
		if(null == user){
			return "login.jsp";
		}
		int uid = user.getId();
		
		List<OrderItem> orderItems = orderItemService.listByUid(uid);
		for(OrderItem orderItem : orderItems){
			Product product = productService.getProduct(orderItem.getPid());
			orderItem.setProduct(product);
		}
		
		model.addAttribute("orderItems",orderItems);
		
		return "cart.jsp";
	}
	
	
	@RequestMapping("forechangeOrderItem")
	public void changeOrderItem(HttpServletResponse response, HttpSession session,
			@RequestParam("pid") int pid, @RequestParam("oiid") int oiid, @RequestParam("number") int number) throws IOException{
		OrderItem orderItem = orderItemService.getOrderItem(oiid);
		int add = number - orderItem.getNumber();
		orderItem.setNumber(number);
		
		orderItemService.update(orderItem);
	    session.setAttribute("cartTotalItemNumber", add);
		
		response.getWriter().print("success");		
	}
	
	@RequestMapping("foredeleteOrderItem")
	public void deleteOrderItem(HttpServletResponse response, HttpSession session,@RequestParam("oiid") int oiid) throws IOException{
		OrderItem orderItem = orderItemService.getOrderItem(oiid);
		orderItemService.delete(oiid);
		
		Integer cartTotalItemNumber = (Integer) session.getAttribute("cartTotalItemNumber");
		session.setAttribute("cartTotalItemNumber", cartTotalItemNumber - orderItem.getNumber());
		
		response.getWriter().print("success");
	}
	
	
	@RequestMapping("forecreateOrder")
	public String createOrder(HttpServletRequest request, HttpSession session, Order order){
		User user = (User) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("orderItems");
		Integer cartTotalItemNumber = (Integer) session.getAttribute("cartTotalItemNumber");
		
		Date date = new Date();
		order.setOrderCode(DateUtil.DateToString(date) + "ttttt");
		order.setCreateDate(DateUtil.DateToString(date));
		order.setStatus("waitPay");
		order.setUid(user.getId());
		orderService.addOrder(order);
		
		for(OrderItem orderItem : orderItems){
			orderItem.setOid(order.getId());
			orderItemService.update(orderItem);
			cartTotalItemNumber = cartTotalItemNumber - orderItem.getNumber();
		}
		
		request.setAttribute("oid", order.getId());
		session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		return "alipay.jsp";
	}
	
	
	@RequestMapping("forepayed")
	public String payed(Model model,@RequestParam("oid") int oid, @RequestParam("total") float total){
		Date date = new Date();
		Order order = orderService.getOrder(oid);
		
		order.setPayDate(DateUtil.DateToString(date));
		order.setStatus("waitDelivery");
		orderService.updateOrder(order);
		
		model.addAttribute("order",order);
		
		return "payed.jsp";		
	}
	
	
	@RequestMapping("forebought")
	public String bought(Model model, HttpSession session){
		User user = (User) session.getAttribute("user");
		
		List<Order> orders = orderService.listByUid(user.getId());
		for(Order order : orders){
			List<OrderItem> orderItems = orderItemService.listByOid(order.getId());
			int totalNumber = 0;
			float total = 0;
			for(OrderItem orderItem : orderItems){
				totalNumber = totalNumber + orderItem.getNumber();
				total = total + orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			}
			order.setOrderItems(orderItems);
			order.setTotalNumber(totalNumber);
			order.setTotal(total);
		}
		
		model.addAttribute("orders",orders);
		
		return "bought.jsp";
	}
	
	//订单页付款
	@RequestMapping("forealipay")
	public String alipay(Model model,@RequestParam("oid") int oid, @RequestParam("total") float total){
		
		model.addAttribute("oid",oid);
		model.addAttribute("total",total);
		
		return "alipay.jsp";	
	}
	
	
	@RequestMapping("foreconfirmPay")
	public String confirmPay(Model model,@RequestParam("oid") int oid){
		Order order = orderService.getOrder(oid);
		float total = 0;
		
		List<OrderItem> orderItems = orderItemService.listByOid(oid);
		for(OrderItem orderItem : orderItems){
			total = total + orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
		}
		order.setTotal(total);
		order.setOrderItems(orderItems);
		
		model.addAttribute("order",order);
		
		return "confirmPay.jsp";
	}
	
	@RequestMapping("foreorderConfirmed")
	public String orderConfirmed(@RequestParam("oid") int oid){
		Date date = new Date();
		Order order = orderService.getOrder(oid);
		
		order.setConfirmDate(DateUtil.DateToString(date));
		order.setStatus("waitReview");
		orderService.updateOrder(order);
		
		return "orderConfirmed.jsp";
		
	}
	
	
	@RequestMapping("foredeleteOrder")
	public void deleteOrder(HttpServletResponse response, @RequestParam("oid") int oid) throws IOException{
		orderService.deleteOrder(oid);
		
		response.getWriter().print("success");
	}
	
	
	@RequestMapping("forereview")
	public String review(Model model,@RequestParam("oid") int oid){
		Order order = new Order();
		List<OrderItem> orderItems = orderItemService.listByOid(oid);
		OrderItem orderItem = new OrderItem();
		if(!orderItems.isEmpty()){
			orderItem = orderItems.get(0);
		}
		
		Product product = productService.getProduct(orderItem.getPid());
		List<Review> reviews = reviewService.listByPid(product.getId());
		product.setReviewCount(reviews.size());
		
		model.addAttribute("product",product);
		order.setId(oid);
		model.addAttribute("order",order);
		
		return "review.jsp";
	}
	
	
	@RequestMapping("foredoreview")
	public String doreview(Model model,HttpSession session,Review review,@RequestParam("oid") int oid){
		Date date = new Date();
		User user = (User) session.getAttribute("user");
		
		review.setUid(user.getId());
		review.setCreateDate(DateUtil.DateToString(date));
		reviewService.addReview(review);
		
		Product product = productService.getProduct(review.getPid());
		List<Review> reviews = reviewService.listByPid(product.getId());
		product.setReviewCount(reviews.size());
		
		Order order = orderService.getOrder(oid);
		order.setStatus("finish");
		orderService.updateOrder(order);
		
		model.addAttribute("product",product);
		model.addAttribute("order",order);
		model.addAttribute("reviews", reviews);
		
		return "review.jsp";
	}
}
