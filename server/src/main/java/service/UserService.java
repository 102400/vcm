package service;

import pojo.User;

public interface UserService {
    
    boolean addUser(User user);
    User findUserByUsernameOrEmail(User user);
//    boolean addZombieUserRatingAndMovieByDoubanId(int doubanId);

}
