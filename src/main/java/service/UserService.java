package service;

import java.util.List;

import pojo.User;

public interface UserService {
	
	public List<User> listUser();
	
	public User loginUser(User user);
	
	public void registerUser(User user);
	
	public boolean checkUsername(String username);

}
