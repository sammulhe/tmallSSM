package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.CategoryMapper;
import pojo.Category;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public List<Category> listCategory() {
		return categoryMapper.list();
	}

	@Override
	public void addCategory(Category category) {
		categoryMapper.add(category);
		
	}

	@Override
	public Category getOne(Category category) {
		return categoryMapper.getOne(category.getId());
	}

	@Override
	public void updateCategory(Category category) {
		categoryMapper.update(category);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryMapper.delete(category.getId());
	}

}
