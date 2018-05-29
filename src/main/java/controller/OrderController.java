package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.Order;
import service.OrderService;

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
}
