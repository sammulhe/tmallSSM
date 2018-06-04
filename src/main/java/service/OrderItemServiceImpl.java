package service;

import java.util.List;

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

	@Override
	public OrderItem getOrderItem(int id) {
		return orderItemMapper.getOne(id);
	}

	@Override
	public OrderItem exist(OrderItem orderItem) {
		return orderItemMapper.exist(orderItem);
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		OrderItem oi = orderItemMapper.exist(orderItem);
		if(null == oi){
			orderItemMapper.add(orderItem);
		}else{
			oi.setNumber(oi.getNumber() + orderItem.getNumber());
			orderItemMapper.update(oi);
		}
	}
	
	

	@Override
	public List<OrderItem> listByUid(int uid) {
		return orderItemMapper.listByUid(uid);
	}

	@Override
	public Integer getCartNum(int uid) {
		return orderItemMapper.getCartNum(uid);
	}

	@Override
	public void update(OrderItem orderItem) {
		orderItemMapper.update(orderItem);
	}

	@Override
	public void delete(int id) {
		orderItemMapper.delete(id);
	}
	
	
}
