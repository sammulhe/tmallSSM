package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.OrderItemMapper;
import pojo.OrderItem;

@Service
public class OrderItemServiceImpl implements OrderItemService{

	@Autowired
	OrderItemMapper orderItemMapper;

	@Override
	public void addOrderItem(OrderItem orderItem) {
		orderItemMapper.add(orderItem);
	}
	
	
}
