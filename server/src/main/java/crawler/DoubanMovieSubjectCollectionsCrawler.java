package crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import pojo.User;
import util.MD5;

//  https://movie.douban.com/subject/26718838/collections
//  https://movie.douban.com/subject/26718838/collections?start=0
//  https://movie.douban.com/subject/26718838/collections?start=180
//  每页20人
public class DoubanMovieSubjectCollectionsCrawler {
    
    private DoubanMovieSubjectCollectionsCrawler() {}
    
    public static List<User> crawer(int doubanId) {
        
        System.out.println("*****DoubanMovieSubjectCollectionsCrawler");
        
        List<User> userList = new ArrayList<>();
        
        int page = 0;  //起始页,每次+20,最大到180
        
        for(; page<200; page+=20) {
            
            try {
                URL url = new URL(Config.DOUBAN_HOST + "/subject/" + doubanId + "/collections?start=" + page);
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
               
                String str0 = "<div class=\"pl2\">";
                String str1 = "<a href=\"https://www.douban.com/people/rakasha/\" class=\"\">括括";
                String usernameA = "<a href=\"https://www.douban.com/people/";
                String usernameB = "/\" class=\"\">";
                String username = null;

                String nickname = null;

                while ((line = reader.readLine()) != null) {
                    
                    if (line.indexOf(str0) != -1) {
                        line = reader.readLine();
                        username = line.split(usernameA)[1].split(usernameB)[0];
                        nickname = line.split(usernameB)[1];

//                        System.out.println(username + ":" + nickname);
                        
                        User user = new User();
                        user.setUsername(username);
                        user.setNickname(nickname);
                        user.setEmail(username + Config.ZOMBIE_USER_EMAIL_SUFFIX);
                        user.setPassword(Config.ZOMBIE_USER_PASSWORD_MD5);
                        
                        userList.add(user);

                        continue;
                    }

                }
                
                
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        for (User user : userList) {
            System.out.println(user.getUsername() + ":" + user.getNickname());
        }
        
        
        System.out.println("*****");
        
        return userList;
    }

}
