package mapper;

import java.util.List;

import pojo.Category;

public interface CategoryMapper {

	public List<Category> list();
	
	public int getTotal();
}
