package mapper;

import java.util.List;

import pojo.Product;

public interface ProductMapper {

	public List<Product> list(Product product);
	
	public void add(Product product);
	
	public Product getOne(int id);
	
	public void update(Product product);
	
	public void delete(int id);
	
	public List<Product> search(String keyword);
}
