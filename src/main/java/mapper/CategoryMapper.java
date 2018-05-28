package mapper;

import java.util.List;

import pojo.Category;

public interface CategoryMapper {

	public List<Category> list();
	
	public void add(Category category);
	
	public Category getOne(int id);
	
	public void update(Category category);
	
	public void delete(int id);
}
