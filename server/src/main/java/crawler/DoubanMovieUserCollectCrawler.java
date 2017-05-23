package crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import pojo.Rating;
import pojo.User;

//  https://movie.douban.com/people/soshuangwei/collect?start=0&sort=time&rating=all&filter=all&mode=grid
public class DoubanMovieUserCollectCrawler {
    
//    public static void main(String[] args) {
//        User user = new User();
//        user.setUsername("soshuangwei");
//        List<Rating> ratingList = crawer(user);
//        for (Rating rating : ratingList) {
//            System.out.println(rating.getMovieId() + ":" + rating.getRating() + ":" + rating.getComment());
//        }
//    }
    
    private DoubanMovieUserCollectCrawler() {}
    
    public static List<Rating> crawer(User user) {
        
        System.out.println("*****DoubanMovieUserCollectCrawler");
        System.out.println("username:" + user.getUsername());
        
        List<Rating> ratingList = new ArrayList<>();
        
        boolean flag = true;
        int page = 0;
        int movieCount = 0;
        for(; page==0 || page<movieCount; page+=15) {
            
            System.out.println("page" + page);
            System.out.println("movieCount" + movieCount);
            
            try {
        
                URL url = new URL(Config.DOUBAN_HOST + "/people/" + user.getUsername() + "/collect?start=" + page + "&sort=time&rating=all&filter=all&mode=grid");
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                
                httpConn.setDoInput(true);  
                httpConn.setDoOutput(true);  
                httpConn.setUseCaches(false);
                httpConn.setConnectTimeout(1000*60);
                // 2、设置请求头  
                httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");  
                // 3、连接  
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
                String line;
                
                //              String str0 = "<h1>红钢城二街看过的电影(519)</h1>";
                  String movieCountA = "看过的电影(";
                  String movieCountB = "看过的电影\\(";
                  String movieCountC = "\\)</h1>";
                
                //              String str1 = "<li class=\"title\">";
//                  String str2 = "<a href=\"https://movie.douban.com/subject/26676350/\" class=\"\">";
                  String doubanIdA = "<li class=\"title\">";
                  String doubanIdB = "<a href=\"https://movie.douban.com/subject/";
                  String doubanIdC = "/\" class=\"\">";
                  
                  Integer doubanId = null;
                  
                  String rating1 = "<span class=\"rating1-t\"></span>";
                  String rating2 = "<span class=\"rating2-t\"></span>";
                  String rating3 = "<span class=\"rating3-t\"></span>";
                  String rating4 = "<span class=\"rating4-t\"></span>";
                  String rating5 = "<span class=\"rating5-t\"></span>";
                  Integer iRating = null;
                  
//                  String str3 = "<span class=\"comment\">松田龙平五星！</span>";
                  String commentA = "<span class=\"comment\">";
                  String commentB = "</span>";
                  String comment = null;
                  
                  
                  boolean bDoubanId = false;
                  boolean bRating = false;
//                  boolean bComment = false;  //comment可以为null
                  
                  Rating rating = new Rating();
                
                  while ((line = reader.readLine()) != null) {
                      
                      if(bDoubanId&&bRating) {
                          
                          rating.setMovieId(doubanId);
                          rating.setRating(iRating);
                          rating.setComment(comment);
                          
                          ratingList.add(rating);
                          
                          rating = new Rating();
                          
                          doubanId = null;
                          iRating = null;
                          comment = null;
                          
                          bDoubanId = false;
                          bRating = false;
//                          bComment = false;
                          
//                          System.out.println("bbb");
                      }
                      
                      if (line.indexOf(movieCountA) != -1) {
                          
                          String movieCountS = line.split(movieCountB)[1].split(movieCountC)[0].split("\\)")[0];
                          movieCount = Integer.parseInt(movieCountS);
//                          System.out.println(movieCount);
                          continue;
                      }
                      if (line.indexOf(doubanIdA) != -1) {
                          
                          line = reader.readLine();
                          String doubanIdS = line.split(doubanIdB)[1].split(doubanIdC)[0];
                          doubanId = Integer.valueOf(doubanIdS);
                          
//                          System.out.println(doubanId);
                          
                          bDoubanId = true;
                          continue;
                      }
                      if (line.indexOf(commentA) != -1) {
                          
                          comment = line.split(commentA)[1].split(commentB)[0];
//                          System.out.println(comment);
                          
//                          bComment = true;
                          continue;
                      }
                      if (line.indexOf(rating1) != -1) {
                          
                          iRating = 1 * 2;
                          
                          bRating = true;
                          continue;
                      }
                      if (line.indexOf(rating2) != -1) {
                          
                          iRating = 2 * 2;
                          
                          bRating = true;
                          continue;
                      }
                      if (line.indexOf(rating3) != -1) {
                          
                          iRating = 3 * 2;
                          
                          bRating = true;
                          continue;
                      }
                      if (line.indexOf(rating4) != -1) {
                          
                          iRating = 4 * 2;
                          
                          bRating = true;
                          continue;
                      }
                      if (line.indexOf(rating5) != -1) {
                          
                          iRating = 5 * 2;
                          
                          bRating = true;
                          continue;
                      }
                  }
                  
//                  break;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            System.out.println("*****");
        }
        
        
        
        return ratingList;
    }

}
