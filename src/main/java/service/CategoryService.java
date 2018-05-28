package service;

import java.util.List;
import pojo.Category;

public interface CategoryService {

	public List<Category> listCategory();
	
	public void addCategory(Category category);
	
	public Category getOne(Category category);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
}
