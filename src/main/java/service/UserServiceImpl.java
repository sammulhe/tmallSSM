package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.UserMapper;
import pojo.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userMapper;

	@Override
	public List<User> listUser() {
		return userMapper.list();
	}

	@Override
	public User loginUser(User user) {
		return userMapper.login(user);
	}

	@Override
	public void registerUser(User user) {
		userMapper.register(user);
	}

	@Override
	public boolean checkUsername(String username) {
		User user = userMapper.checkUsername(username);
		System.out.println(user);
		if(user == null){
			return true;
		}
		return false;
	}

	
}
