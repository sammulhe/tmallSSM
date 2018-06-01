package mapper;

import java.util.List;

import pojo.PropertyValue;

public interface PropertyValueMapper {

	public List<PropertyValue> listByPid(int pid);
}
