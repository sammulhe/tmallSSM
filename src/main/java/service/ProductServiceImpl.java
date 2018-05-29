package service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.CategoryMapper;
import mapper.ProductImageMapper;
import mapper.ProductMapper;
import pojo.Category;
import pojo.Product;
import pojo.ProductImage;
import util.DateUtil;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductMapper productMapper;
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	ProductImageMapper productImageMapper;
	
	@Override
	public List<Product> listProduct(Product product) {
		List<Product> products = productMapper.list(product);
		for(Product p : products){
			ProductImage productImage = productImageMapper.getFirstSingleOne(p.getId());
			p.setProductSingleImage(productImage);
		}
		
		return products;
	}

	@Override
	public void addProduct(Product product) {
		String date = DateUtil.DateToString(new Date());
		product.setCreateDate(date);
		
		productMapper.add(product);
	}

	@Override
	public Product getProduct(int id) {
		Product product = productMapper.getOne(id);
		Category category = categoryMapper.getOne(product.getCid());
		product.setCategory(category);
		
		return product;
	}

	@Override
	public void updateProduct(Product product) {
		productMapper.update(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productMapper.delete(product.getId());
	}
	

	
	

}
