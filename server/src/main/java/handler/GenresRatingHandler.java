package handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pojo.GenresRating;
import pojo.Rating;
import pojo.User;
import service.RatingService;
import service.UserService;

@Component
public class GenresRatingHandler implements Runnable {
    
    @Autowired
    private RatingService ratingService;
    
    @Autowired
    private UserService userService;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        while (true) {
            
            try {
                Thread.currentThread().sleep(1000 * 60 * 1);
                
                List<User> userList = 
                        userService.findHundredUsersIfRatingCountMoreThanXAndUnhandleRatingMoreThanY();
                
                if (userList == null || userList.size() == 0) continue;
                
                for (User user : userList) { 
                
                    Rating rating = new Rating();
                    rating.setUserId(user.getUserId());
                    
                    
                    ratingService.selectAndAddGenresRatingListToGenresRatingByUserId(rating);
                
                    
                    userService.makeUnhandleRatingsZeroByUserId(user);
                }
            }
            catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

}
