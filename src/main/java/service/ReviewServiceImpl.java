package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.ReviewMapper;
import mapper.UserMapper;
import pojo.Review;
import pojo.User;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	ReviewMapper reviewMapper;
	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<Review> listByPid(int pid) {
		List<Review> reviews = reviewMapper.listByPid(pid);
		for(Review review : reviews){
			User user = userMapper.getOne(review.getUid());
			review.setUser(user);
		}
		
		return reviews;
	}

	@Override
	public void addReview(Review review) {
		reviewMapper.add(review);
	}

}
