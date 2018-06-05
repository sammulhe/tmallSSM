package service;

import java.util.List;

import pojo.OrderItem;

public interface OrderItemService {
	
	public List<OrderItem> listByUid(int uid);

	public void addOrderItem(OrderItem orderItem);
	
	public OrderItem getOrderItem(int id);
	
	public OrderItem exist(OrderItem orderItem);
	
	public void updateOrderItem(OrderItem orderItem);
	
	public Integer getCartNum(int uid);
	
	public void update(OrderItem orderItem);
	
	public void delete(int id);
	
	public List<OrderItem> listByOid(int oid);
	
}
