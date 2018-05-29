package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.ProductImage;
import service.ProductImageService;
import service.ProductService;

@Controller
@RequestMapping("")
public class ProductImageController {

	@Autowired
	ProductImageService productImageService;
	
	@RequestMapping("admin_productImage_list")
	public String list(Model model, ProductImage productImage){
		List<ProductImage> productImages = productImageService.listProductImage(productImage);
		List<ProductImage> productSingleImages = new ArrayList<>();
		List<ProductImage> productDetailImages = new ArrayList<>();
		
		for(ProductImage pi : productImages){
			if(pi.getType().equals("Single")){
				productSingleImages.add(pi);
			}else{
				productDetailImages.add(pi);
			}
		}
		model.addAttribute("product",productImages.get(0).getProduct());
		model.addAttribute("productSingleImages",productSingleImages);
		model.addAttribute("productDetailImages",productDetailImages);
		
		return "admin/listProductImage.jsp";
	}
	
	@RequestMapping("admin_productImage_add")
	public String add(HttpServletRequest request, ProductImage productImage){
		productImageService.addProductImage(request,productImage);
		
		return "redirect:/admin_productImage_list?pid="+productImage.getPid()+"";
	}
	
	@RequestMapping("admin_productImage_delete")
	public String delete(ProductImage productImage){
		ProductImage p = productImageService.getProductImage(productImage.getId());
		productImageService.deleteProductImage(productImage.getId());
		
		return "redirect:/admin_productImage_list?pid="+p.getPid()+"";
	}
	
}
