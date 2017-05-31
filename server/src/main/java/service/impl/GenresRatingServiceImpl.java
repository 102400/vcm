package service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mapper.FavoriteGenresMapper;
import mapper.GenresRatingMapper;
import pojo.FavoriteGenres;
import pojo.GenresRating;
import service.GenresRatingService;

@Service
@Transactional
public class GenresRatingServiceImpl implements GenresRatingService {
    
    @Autowired
    private GenresRatingMapper genresRatingMapper;
    
    @Autowired
    private FavoriteGenresMapper favoriteGenresMapper;

    @Override
    public GenresRating findFirstGenresRating() {
        // TODO Auto-generated method stub
        return genresRatingMapper.findFirstGenresRating();
    }

    @Override
    public int findAndAddFavoriteGenresByGenresRatingId(GenresRating genresRating) {
        // TODO Auto-generated method stub
        genresRating = genresRatingMapper.findGenresRatingByGenresRatingId(genresRating);
        
        List<GenresRating> genresRatingList = genresRatingMapper.findGenresRatingListByUserId(genresRating);
        
        
        if (genresRatingList == null || genresRatingList.size() < 3) {
            return genresRatingList == null ? 0 : genresRatingList.size();
        }
        
        List<FavoriteGenres> favoriteGenresList = new ArrayList<>(3);
        
        int[] index = new int[]{-1, -1, -1};  // 找出这个用户最喜欢的三个流派
        if (genresRatingList.size() == 3) { //直接写入
            for (GenresRating gr : genresRatingList) {
                FavoriteGenres fg = new FavoriteGenres();
                fg.setUserId(gr.getUserId());
                fg.setGenresId(gr.getGenresId());
                favoriteGenresList.add(fg);
            }
        }
        else {
        
//            int firstCount = genresRatingList.get(0).getCount();
//            int secondCount = genresRatingList.get(1).getCount();
//            
//            if (firstCount * 0.3 > secondCount) {  //第一个流派的count比第二个大30%时,不管如何都加入
//                FavoriteGenres fg = new FavoriteGenres();
//                fg.setUserId(genresRatingList.get(0).getUserId());
//                fg.setGenresId(genresRatingList.get(0).getGenresId());
//                favoriteGenresList.add(fg);
//            }
            
            
            float[] weight = new float[genresRatingList.size()];
            
            System.out.println("*****");
            
            for (int i=0; i<genresRatingList.size(); i++) {
                GenresRating gr = genresRatingList.get(i);
                weight[i] = (gr.getAvgRatio() + gr.getRatio()) * gr.getAvgRating();
                System.out.println(weight[i]);
            }
            
//            int[] index = new int[]{-1, -1, -1};  // 找出这个用户最喜欢的三个流派
//            for (int i=0; i<3; i++) {
//                int a = -1;
//                float b = -1F;
//                J:
//                for (int j=0; j<weight.length; j++) {
//                    if (weight[j] > b) {
//                        if (a == -1) {
//                            for (int k=0; k<index.length; k++) {
//                                if (j == index[k]) {
//                                    continue J;
//                                }
//                            }
//                            a = j;
//                            b = weight[j];
//                            index[i] = j;
//                            continue J;
//                        }
//                        for (int k=0; k<index.length; k++) {
//                            if (j == index[k]) {
//                                continue J;
//                            }
//                        }
//                       index[i] = j;
//                       a = j;
//                       b = weight[j];
//                    }
//                }
//            }
            for (int i=0; i<3; i++) {
//              int a = -1;
              float b = -1F;
              J:
              for (int j=0; j<weight.length; j++) {
                  if (weight[j] > b) {
                      for (int k=0; k<index.length; k++) {
                          if (j == index[k]) {
                              continue J;
                          }
                      }
                      index[i] = j;
//                      a = j;
                      b = weight[j];
                  }
              }
          }
            
            System.out.println("result:");
            
            for (int i : index) {
                GenresRating gr = genresRatingList.get(i);
                System.out.println((gr.getAvgRatio() + gr.getRatio()) * gr.getAvgRating());
                
                FavoriteGenres fg = new FavoriteGenres();
                fg.setUserId(gr.getUserId());
                fg.setGenresId(gr.getGenresId());
                
                favoriteGenresList.add(fg);
            }
            
//            try {
//                Thread.currentThread().sleep(1000 * 60 * 10);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            
        }
        //先删除用户所属的
        favoriteGenresMapper.deleteFavoriteGenresByUserId(favoriteGenresList.get(0));
        
        //写入数据库
        for (FavoriteGenres fg : favoriteGenresList) {
            favoriteGenresMapper.addFavoriteGenres(fg);
        }
        
        return genresRatingList.size();
    }

    @Override
    public GenresRating findLastGenresRating() {
        // TODO Auto-generated method stub
        return genresRatingMapper.findLastGenresRating();
    }

}
