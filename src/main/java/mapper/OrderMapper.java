package mapper;

import java.util.List;

import pojo.Order;

public interface OrderMapper {

	public List<Order> list();
	
	public void add(Order order);
	
	public Order getOne(int id);
	
	public void update(Order order);
	
	public List<Order> listByUid(int uid);
	
	public void delete(int id);
}
