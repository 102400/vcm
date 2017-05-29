package mapper;

import java.util.List;

import pojo.User;

public interface UserMapper {
    
    int add(User user);
    User findUserByEmail(User user);
    User findUserByUsername(User user);
    User findUserByUserId(User user);
    int plusOneToUnhandleRatingsAndRatingCountByUserId(User user);
    int makeUnhandleRatingsZeroByUserId(User user);
    List<User> findHundredUsersIfRatingCountMoreThanXAndUnhandleRatingMoreThanY();

}
