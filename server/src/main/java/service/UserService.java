package service;

import java.util.List;

import pojo.User;

public interface UserService {
    
    boolean addUser(User user);
    User findUserByUsernameOrEmail(User user);
//    boolean addZombieUserRatingAndMovieByDoubanId(int doubanId);
    List<User> findHundredUsersIfRatingCountMoreThanXAndUnhandleRatingMoreThanY();
    int plusOneToUnhandleRatingsAndRatingCountByUserId(User user);
    int makeUnhandleRatingsZeroByUserId(User user);

}
