package service;

import java.util.List;

import pojo.Review;

public interface ReviewService {

	public List<Review> listByPid(int pid);
}
