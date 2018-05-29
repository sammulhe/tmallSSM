package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.OrderItemMapper;
import mapper.OrderMapper;
import mapper.ProductImageMapper;
import mapper.ProductMapper;
import pojo.Order;
import pojo.OrderItem;
import pojo.Product;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemMapper orderItemMapper;
	@Autowired
	ProductMapper productMapper;
	@Autowired
	ProductImageMapper productImageMapper;
	
	@Override
	public List<Order> listOrder() {
	
		List<Order> orders = orderMapper.list();
		for(Order o : orders){
			int totalNumber = 0;
			float total = 0;
			for(OrderItem oi : o.getOrderItems()){
				//oi = orderItemMapper.getOne(oi);
				Product product = productMapper.getOne(oi.getPid());
				product.setProductSingleImage(productImageMapper.getFirstSingleOne(product.getId()));
				oi.setProduct(product);
				totalNumber = totalNumber + oi.getNumber();
				total = total + totalNumber * oi.getProduct().getPromotePrice();
			}
			o.setTotal(total);
			o.setTotalNumber(totalNumber);
		}
		
		return orders;
	}

}
