package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PropertyMapper;
import pojo.Property;

@Service
public class PropertyServiceImpl implements PropertyService{

	@Autowired
	PropertyMapper propertyMapper;
	
	@Override
	public List<Property> listProperty(Property property) {
		return propertyMapper.list(property);				
	}

	@Override
	public void addProperty(Property property) {
		propertyMapper.add(property);
	}

	@Override
	public Property getProperty(Property property) {
		// TODO Auto-generated method stub
		return propertyMapper.getOne(property.getId());
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
