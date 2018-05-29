package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import pojo.ProductImage;

public interface ProductImageService {

	public List<ProductImage> listProductImage(ProductImage productImage);
	
	public void addProductImage(HttpServletRequest request, ProductImage productImage);
	
	public void deleteProductImage(int id);
	
	public ProductImage getProductImage(int id);
}
