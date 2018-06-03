package service;

import java.util.List;

import pojo.Product;

public interface ProductService {

	public List<Product> listProduct(Product product);
	
	public void addProduct(Product product);
	
	public Product getProduct(int id);
	
	public void updateProduct(Product product);
	
	public void deleteProduct(Product product);
	
	public List<Product> search(String keyword);
}
