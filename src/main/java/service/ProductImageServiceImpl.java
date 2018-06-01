package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.ProductImageMapper;
import mapper.ProductMapper;
import pojo.Product;
import pojo.ProductImage;
import util.FileUtil;

@Service
public class ProductImageServiceImpl implements ProductImageService{

	@Autowired
	ProductImageMapper productImageMapper;
	@Autowired
	ProductMapper productMapper;

	@Override
	public List<ProductImage> listProductImage(ProductImage productImage) {
		List<ProductImage> productImages = productImageMapper.list(productImage);
		Product product = productMapper.getOne(productImage.getPid());
		if(productImages.isEmpty()){
			ProductImage p = new ProductImage();
			productImages.add(p);
			return productImages;
		}
		productImages.get(0).setProduct(product);
				
		return productImages;
	}

	@Override
	public void addProductImage(HttpServletRequest request, ProductImage productImage) {
		String path = "D:\\workspace\\tmallSSM\\src\\main\\webapp\\img\\product" + productImage.getType() + "\\";		
		productImageMapper.add(productImage);
		FileUtil.upload(request, path, productImage.getId());
	}

	@Override
	public void deleteProductImage(int id) {
		productImageMapper.delete(id);
	}

	@Override
	public ProductImage getProductImage(int id) {
		return productImageMapper.getOne(id);
	}

	@Override
	public List<ProductImage> listByType(ProductImage productImage) {
		return productImageMapper.listByType(productImage);
	}

}
