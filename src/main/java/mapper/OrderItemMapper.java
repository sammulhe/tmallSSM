package mapper;

import pojo.OrderItem;

public interface OrderItemMapper {

	public OrderItem getOne(OrderItem orderItem);
	
	public Integer getSaleCount(int pid);
}
