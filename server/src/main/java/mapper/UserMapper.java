package mapper;

import pojo.User;

public interface UserMapper {
    
    int add(User user);
    User findUserByEmail(User user);
    User findUserByUsername(User user);

}
