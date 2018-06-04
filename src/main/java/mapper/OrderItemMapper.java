package mapper;


import pojo.OrderItem;

public interface OrderItemMapper {

	public OrderItem getOne(OrderItem orderItem);
	
	public Integer getSaleCount(int pid);
	
	public void add(OrderItem orderItem);
}
