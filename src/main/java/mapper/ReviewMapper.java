package mapper;

import java.util.List;

import pojo.Review;

public interface ReviewMapper {

	public List<Review> listByPid(int pid);
}
