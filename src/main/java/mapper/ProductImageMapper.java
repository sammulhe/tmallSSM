package mapper;

import java.util.List;

import pojo.ProductImage;

public interface ProductImageMapper {

	public List<ProductImage> list(ProductImage productImage);
	
	public void add(ProductImage productImage);
	
	public void delete(int id);
	
	public ProductImage getOne(int id);
	
	public ProductImage getFirstSingleOne(int pid);
}
