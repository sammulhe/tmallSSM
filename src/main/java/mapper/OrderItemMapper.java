package mapper;


import java.util.List;

import pojo.OrderItem;

public interface OrderItemMapper {

	public List<OrderItem> listByUid(int uid);
	
	public OrderItem getOne(int id);
	
	public Integer getSaleCount(int pid);
	
	public void add(OrderItem orderItem);
	
	public OrderItem exist(OrderItem orderItem);
	
	public void update(OrderItem orderItem);
	
	public Integer getCartNum(int uid);
	
	public void delete(int id);
	
	public List<OrderItem> listByOid(int oid);
	}
