package mapper;

import java.util.List;

import pojo.User;

public interface UserMapper {

	public List<User> list();
	
	public User getOne(int id);
	
	public User login(User user);
	
	public void register(User user);
	
	public User checkUsername(String username);
}
