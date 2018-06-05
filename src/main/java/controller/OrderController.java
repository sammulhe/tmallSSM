package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pojo.Order;
import service.OrderService;
import util.DateUtil;

@Controller
@RequestMapping("")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@RequestMapping("admin_order_list")
	public String list(Model model){
		List<Order> orders = orderService.listOrder();
		
		model.addAttribute("orders",orders);
		
		return "admin/listOrder.jsp";
	}
	
	
	@RequestMapping("admin_order_delivery")
	public void delivery(HttpServletResponse response, @RequestParam("id") int oid) throws IOException{
		Date date = new Date();
		Order order = orderService.getOrder(oid);
		
		order.setDeliveryDate(DateUtil.DateToString(date));
		order.setStatus("waitConfirm");
		orderService.updateOrder(order);
		
		response.getWriter().print("success");
	}
}
