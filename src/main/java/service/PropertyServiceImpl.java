package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.CategoryMapper;
import mapper.PropertyMapper;
import pojo.Property;

@Service
public class PropertyServiceImpl implements PropertyService{

	@Autowired
	PropertyMapper propertyMapper;
	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public List<Property> listProperty(Property property) {
        List<Property> properties = propertyMapper.list(property);	
        
        return properties;
	}

	@Override
	public void addProperty(Property property) {
		propertyMapper.add(property);
	}

	@Override
	public Property getProperty(Property property) {
		property = propertyMapper.getOne(property.getId());
		
		return property;
	}

	@Override
	public void updateProperty(Property property) {
		propertyMapper.update(property);
	}

	@Override
	public void deleteProperty(Property property) {
		propertyMapper.delete(property.getId());
	}

}
