package handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import service.MovieService;
import service.RatingService;

@Component
public class RatingsAndUsersHandler implements Runnable {
    
//    @Autowired
//    private RatingService ratingService;
    
    @Autowired
    private MovieService movieService;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.currentThread().sleep(1000 * 10);  //延迟启动
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        while (true) {
            try {
                
                movieService.calculationAndUpdateRatingsAndUsers();
                
//                Thread.currentThread().sleep(1000 * 3);  //dev
                Thread.currentThread().sleep(1000 * 60 * 2);
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.currentThread().sleep(1000 * 60 * 2);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

}
