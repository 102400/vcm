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
import pojo.FavoriteGenres;
import pojo.GenresRating;
import service.FavoriteGenresService;
import service.GenresRatingService;

/**
 * 
 * 依赖 FavoriteGenresHandler 处理后的结果
 *
 */

@Component
public class NearestNeighborHandler implements Runnable {
    
    private static Integer nextFavoriteGenresIdINI = 1;  //从本地文件初始化下一个处理的
    private static Integer nextFavoriteGenresId = 1;
    
    static {
        try {
            File file = new File(Config.CONFIG_PATH + "/nextFavoriteGenresId.ini");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            nextFavoriteGenresIdINI = Integer.valueOf(br.readLine());
            nextFavoriteGenresId = nextFavoriteGenresIdINI;
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
                            //每10秒检查nextFavoriteGenresIdINI与nextFavoriteGenresId, 两者不同则写入磁盘更新
                            if (nextFavoriteGenresId != nextFavoriteGenresIdINI) {
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
                                bw.write(nextFavoriteGenresId + "");
                                bw.flush();
                                bw.close();
                                nextFavoriteGenresIdINI = nextFavoriteGenresId;
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
    private FavoriteGenresService favoriteGenresService;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            //延迟启动
//            Thread.currentThread().sleep(1000 * 60 * 10);
            Thread.currentThread().sleep(1000 * 10);  // dev
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        while (true) {
            FavoriteGenres firstFavoriteGenres = favoriteGenresService.findFirstFavoriteGenres();
            if (firstFavoriteGenres == null) {  //表空时
                try {
                    Thread.currentThread().sleep(1000 * 60 * 10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            }
            if (firstFavoriteGenres.getFavoriteGenresId() > nextFavoriteGenresIdINI) {
                nextFavoriteGenresId = firstFavoriteGenres.getFavoriteGenresId();  //id为表的第一行
            }
            break;
        }
        
        while (true) {
            try {
                FavoriteGenres favoriteGenres = new FavoriteGenres();
                favoriteGenres.setFavoriteGenresId(nextFavoriteGenresId);
                
                int count = favoriteGenresService.findAndAddNearestNeighbor(favoriteGenres);
                if (count == 0) {  //到表底了或卡住了。。。。
                    
                    FavoriteGenres fg = favoriteGenresService.findLastFavoriteGenres();
                    
                    if (fg.getFavoriteGenresId() < nextFavoriteGenresId) {  //到表底了
                        nextFavoriteGenresId = fg.getFavoriteGenresId() + 1;
                        Thread.currentThread().sleep(1000 * 60 * 5);
                    }
                    else {  //出现了不连续的id
                        nextFavoriteGenresId++;
                        Thread.currentThread().sleep(100);
                    }
                }
                nextFavoriteGenresId = count + nextFavoriteGenresId;
            }
            catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.currentThread().sleep(1000 * 60 * 5);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
        
    }

}
