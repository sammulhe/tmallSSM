package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PropertyValueMapper;
import pojo.PropertyValue;

@Service
public class PropertyValueServiceImpl implements PropertyValueService{

	@Autowired
	PropertyValueMapper propertyValueMapper;
	@Override
	public List<PropertyValue> getPropertyValueByPid(int pid) {
		return propertyValueMapper.listByPid(pid);
	}
}
