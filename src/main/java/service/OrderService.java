package service;

import java.util.List;

import pojo.Order;

public interface OrderService {

	public List<Order> listOrder();
	
	public void addOrder(Order order);
	
	public Order getOrder(int id);
	
	public void updateOrder(Order order);
	
	public List<Order> listByUid(int uid);
	
	public void deleteOrder(int id);
}
