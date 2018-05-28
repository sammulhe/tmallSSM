package mapper;

import java.util.List;

import pojo.Property;

public interface PropertyMapper {

	public List<Property> list(Property property);
	
	public void add(Property property);
	
	public Property getOne(int id);
	
	public void update(Property property);
	
	public void delete(int id);
}
