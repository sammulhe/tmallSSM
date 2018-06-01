package pojo;

import java.util.ArrayList;
import java.util.List;

public class Category {

	private int id;
	private String name;
	private List<Product> products;
	private List<List<Product>> productsByRow;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	public List<List<Product>> getProductsByRow() {
		int row = 3;
		int count = 0;
		if(!products.isEmpty()){
			productsByRow = new ArrayList<>();
			List<Product> ps = new ArrayList<>();
			for(Product p : products){
				count++;
				ps.add(p);
				if(count % row == 0){
					List<Product> psi = new ArrayList<>();
					psi.addAll(ps);
					productsByRow.add(psi);
					ps.clear();
				}
			}
			productsByRow.add(ps);
		}
		
		return productsByRow;
	}
	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}
	
	

}
