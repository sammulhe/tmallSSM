package service;

import java.util.List;

import pojo.PropertyValue;

public interface PropertyValueService {

	public List<PropertyValue> getPropertyValueByPid(int pid);
}
