package service;

import java.util.List;

import pojo.Category;
import pojo.Property;

public interface PropertyService {

	public List<Property> listProperty(Property property);
	
	public void addProperty(Property property);
	
	public Property getProperty(Property property);
	
	public void updateProperty(Property property);
	
	public void deleteProperty(Property property);
}
