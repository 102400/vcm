package handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import config.Config;
import pojo.GenresRating;
import service.GenresRatingService;

/**
 * 
 * 依赖 GenresRatingHandler 处理后的结果
 *
 */

@Component
public class FavoriteGenresHandler implements Runnable {
    
    private static Integer nextGenresRatingIdINI = 1;  //从本地文件初始化下一个处理的
    private static Integer nextGenresRatingId = 1;
    
    static {
        try {
            File file = new File(Config.CONFIG_PATH + "/nextGenresRatingId.ini");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            nextGenresRatingIdINI = Integer.valueOf(br.readLine());
            nextGenresRatingId = nextGenresRatingIdINI;
            br.close();
            
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(1000 * 20);  //延迟启动
                    }
                    catch (Exception e) {}
                    
                    while (true) {
                        try {
                            Thread.currentThread().sleep(1000 * 10);
                            //每10秒检查nextGenresRatingIdINI与nextGenresRatingId, 两者不同则写入磁盘更新
                            if (nextGenresRatingId != nextGenresRatingIdINI) {
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
                                bw.write(nextGenresRatingId + "");
                                bw.flush();
                                bw.close();
                                nextGenresRatingIdINI = nextGenresRatingId;
                            }
                        }
                        catch (Exception e) {
//                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Autowired
    private GenresRatingService genresRatingService;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            //延迟启动
//            Thread.currentThread().sleep(1000 * 60 * 5);
            Thread.currentThread().sleep(1000 * 10);  // dev
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        GenresRating firstGenresRating = genresRatingService.findFirstGenresRating();
        if (firstGenresRating.getGenresRatingId() > nextGenresRatingIdINI) {
            nextGenresRatingId = firstGenresRating.getGenresRatingId();  //id为表的第一行
        }
        
        while (true) {
            
            try {
                GenresRating genresRating = new GenresRating();
                genresRating.setGenresRatingId(nextGenresRatingId);
                
                //如果nextGenresRatingId不连续则会卡住!
                try {
//                    Thread.currentThread().sleep(1000 * 1);
                    int count = genresRatingService.findAndAddFavoriteGenresByGenresRatingId(genresRating);
                    if (count == 0) {  //到表底了或卡住了。。。。
                        Thread.currentThread().sleep(1000 * 60 * 5);
                    }
                    nextGenresRatingId = count + nextGenresRatingId;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().sleep(1000 * 60 * 5);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

}
