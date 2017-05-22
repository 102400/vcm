package crawler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import pojo.Genres;
import pojo.Movie;

public class DoubanMovieSubjectCrawler {
    
    private DoubanMovieSubjectCrawler() {}
    
    public static List<Object> crawer(int doubanId) {
        
        System.out.println("*****DoubanMovieSubjectCrawler");
        
        Movie movie = null;
        List<Genres> genresList = new ArrayList<>();
        
        try {
            URL url = new URL(Config.DOUBAN_HOST + "/subject/" + doubanId);
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
            
//                String str0 = "<span property=\"v:itemreviewed\">异星觉醒 Life</span>";
            String nameZhA = "<span property=\"v:itemreviewed\">";
            String nameZhB = "</span>";
            String nameZh = null;
            String nameO = null;
            
//                String str1 = "<a href=\"http://www.imdb.com/title/tt5442430\" target=\"_blank\" rel=\"nofollow\">tt5442430</a>";
            String imdbIdA = "<a href=\"http://www.imdb.com/title/tt";
            String imdbIdB = "\" target=\"_blank\" rel=\"nofollow\">tt";
            Integer imdbId = null;
            
//                String str2 = "<span property=\"v:summary\" class=\"\">几位漂浮在空间站的宇航员们 ..." + "... 人类的命运也危在旦夕。</span>";
            String storylineA = "<span property=\"v:summary\" class=\"\">";
//                String storylineB = "</span>";
            String storyline = null;
            
            String str3 = "<span property=\"v:initialReleaseDate\" content=\"2017-05-19(中国大陆)\">2017-05-19(中国大陆)</span> / <span property...";
            String releaseDateA = "<span property=\"v:initialReleaseDate\" content=\"";
            String releaseDateB = "\">";
            StringBuilder releaseDate = new StringBuilder();
            
//                String str4 = "<span property=\"v:runtime\" content=\"104\">104分钟</span>";
            String runtimeA = "<span property=\"v:runtime\" content=\"";
            String runtimeB = "\">";
            Short runtime = null;
            
//                String str5 = "<img src=\"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2456571580.jpg\" title=\"点击看更多海报\" alt=\"Life\" rel=\"v:image\" />";
            String coverA = "\" rel=\"v:image\" />";
            String coverB = "\" title=\"点击看更多海报\" alt=\"";
            String coverC = "<img src=\"";
            String cover = null;
            
            String str6 = "<span property=\"v:genre\">科幻</span><span pro...";
            String genresA = "<span property=\"v:genre\">";
            String genresB = "</span>";
//            String genres = null;
           
            
            while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
                if (line.indexOf(nameZhA) != -1) {
                    String temp = line.split(nameZhA)[1].split(nameZhB)[0];
                    nameZh = temp.split(" ")[0];
                    try {
                        nameO = temp.split(" ")[1];
                    }
                    catch (Exception e) {}
                    
                    System.out.println(nameZh);
                    System.out.println(nameO);
                    continue;
                }
                if (line.indexOf(imdbIdA) != -1) {
                    String imdbIdS = line.split(imdbIdA)[1].split(imdbIdB)[0];
                    imdbId = Integer.valueOf(imdbIdS);
                    
                    System.out.println(imdbId);
                    continue;
                }
                if (line.indexOf(storylineA) != -1) {
                    line = reader.readLine();
                    storyline = line;
                    
                    System.out.println(storyline);
                    continue;
                }
                if (line.indexOf(releaseDateA) != -1) {
                    String[] temp = line.split(" / ");
                    for (int i=0; i<temp.length; i++) {
                        String releaseDateBS = temp[i].split(releaseDateA)[1].split(releaseDateB)[0];
                        releaseDate.append(releaseDateBS + "/");
                    }
                    
                    System.out.println(releaseDate.toString());
                    continue;
                }
                if (line.indexOf(runtimeA) != -1) {
                    String runtimeS = line.split(runtimeA)[1].split(runtimeB)[0];
                    runtime = Short.valueOf(runtimeS);
                    
                    System.out.println(runtime);
                    continue;
                }
                if (line.indexOf(coverA) != -1) {
                    cover = line.split(coverB)[0].split(coverC)[1];
                    
                    System.out.println(cover);
                    continue;
                }
                if (line.indexOf(genresA) != -1) {
                    String[] temp = line.split(" / ");
                    for (int i=0; i<temp.length; i++) {
                        Genres genres = new Genres();
                        
                        String genresS = temp[i].split(genresA)[1].split(genresB)[0];
                        genres.setNameZh(genresS);
                        
                        genresList.add(genres);
                        
                        System.out.println(genresS);
                    }
                    
                    continue;
                }
            }
            
            if (imdbId == null || nameZh == null ) {
                return null;
            }
            
            movie = new Movie();
            movie.setDoubanId(doubanId);
            movie.setImdbId(imdbId);
//                movie.setRatings(0f);
//                movie.setUsers(0);
            movie.setNameZh(nameZh);
            movie.setNameO(nameO);
            movie.setStoryline(storyline);
            movie.setReleaseDate(releaseDate.toString());
            movie.setRuntime(runtime);
            
            
            if(!saveCover(movie, cover)) {  //保存封面
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        List<Object> list = new ArrayList<>();
        list.add(movie);
        list.add(genresList);
        
        System.out.println("*****");
        
        return list;
    }
    
    private static boolean saveCover(Movie movie, String cover) {
        try {
            URL url = new URL(cover);
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
            
            InputStream is = httpConn.getInputStream();  //获得图片数据
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] temp0 = new byte[1024];
            int len = 0;
            while ((len=is.read(temp0))!=-1) {
                baos.write(temp0, 0, len);
            }
            is.close();
            byte[] image = baos.toByteArray();
            baos.close();
            
            System.out.println(image.length+"字节");
            
            
            String filename = movie.getDoubanId() + ".jpg";
            
            
            if (!writeFileToDisk(image, Config.MOVIE_COVER_PATH, filename)) {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    private static boolean writeFileToDisk(byte[] image, String path, String filename) {  //写入到本地磁盘
        
        File file = new File(path + "/" + filename);  //写入的位置
        try {
//            if(!file.getParentFile().exists()) {  
//                //如果目标文件所在的目录不存在，则创建父目录  
//                System.out.println("目标文件所在目录不存在，准备创建它！");  
//                if(!file.getParentFile().mkdirs()) {  
//                    System.out.println("创建目标文件所在目录失败！");
//                }  
//            } 
            
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(image);
            fos.flush();
            fos.close();
            System.out.println(filename+" 已写入");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
