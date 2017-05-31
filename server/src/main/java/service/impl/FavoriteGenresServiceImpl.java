package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.RatingStats;
import mapper.FavoriteGenresMapper;
import mapper.GenresRatingMapper;
import mapper.NearestNeighborMapper;
import mapper.RatingMapper;
import pojo.FavoriteGenres;
import pojo.GenresRating;
import pojo.NearestNeighbor;
import pojo.Rating;
import service.FavoriteGenresService;

@Service
@Transactional
public class FavoriteGenresServiceImpl implements FavoriteGenresService {

    @Autowired
    private FavoriteGenresMapper favoriteGenresMapper;
    
    @Autowired
    private GenresRatingMapper genresRatingMapper;
    
    @Autowired
    private RatingMapper ratingMapper;
    
    @Autowired
    private NearestNeighborMapper nearestNeighborMapper;
    
    @Override
    public FavoriteGenres findLastFavoriteGenres() {
        // TODO Auto-generated method stub
        return favoriteGenresMapper.findLastFavoriteGenres();
    }

    @Override
    public FavoriteGenres findFirstFavoriteGenres() {
        // TODO Auto-generated method stub
        return favoriteGenresMapper.findFirstFavoriteGenres();
    }

    @Override
    public int findAndAddNearestNeighbor(FavoriteGenres favoriteGenres) {
        // TODO Auto-generated method stub
        favoriteGenres = favoriteGenresMapper.findFavoriteGenresByFavoriteGenresId(favoriteGenres);
        
        if (favoriteGenres == null) return 0;
        
        List<FavoriteGenres> favoriteGenresList = favoriteGenresMapper.findFavoriteGenresListByUserId(favoriteGenres);
        Integer genresA = favoriteGenresList.get(0).getGenresId();
        Integer genresB = favoriteGenresList.get(1).getGenresId();
        Integer genresC = favoriteGenresList.get(2).getGenresId();
        
        GenresRating mGR = new GenresRating();
        mGR.setUserId(favoriteGenresList.get(0).getUserId());
        Rating mRating = new Rating();
        mRating.setUserId(favoriteGenresList.get(0).getUserId());
        
        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put("genresRating", mGR);
        mMap.put("genresA", genresA);
        mMap.put("genresB", genresB);
        mMap.put("genresC", genresC);
//        List<GenresRating> mGRList = genresRatingMapper.findGenresRatingListByUserIdAndGenresId(mGR, genresA, genresB, genresC);
        List<GenresRating> mGRList = genresRatingMapper.findGenresRatingListByUserIdAndGenresId(mMap);
        
        RatingStats mRatingStats = ratingMapper.selectRatingStatsByUserId(mRating);
        float mAvgRating = mRatingStats.getAvgRating();
        
        List<FavoriteGenres> theSamefavoriteGenresList = 
//                favoriteGenresMapper.findTheSameFavoriteGenres(genresA, genresB, genresC);
                favoriteGenresMapper.findTheSameFavoriteGenres(mMap);
        
        float[] distance = new float[theSamefavoriteGenresList.size()];
        int[] index = new int[] { -1, -1, -1};  //距离最短的index
        
//        for (FavoriteGenres tsfg : theSamefavoriteGenresList) {
        for (int j=0; j<theSamefavoriteGenresList.size(); j++) {
            FavoriteGenres tsfg = theSamefavoriteGenresList.get(j);
            GenresRating pGR = new GenresRating();
            pGR.setUserId(tsfg.getUserId());
            Rating pRating = new Rating();
            pRating.setUserId(tsfg.getUserId());
            
            HashMap<String, Object> pMap = new HashMap<>();
            pMap.put("genresRating", pGR);
            pMap.put("genresA", genresA);
            pMap.put("genresB", genresB);
            pMap.put("genresC", genresC);
            
//            List<GenresRating> pGRList = genresRatingMapper.findGenresRatingListByUserIdAndGenresId(pGR, genresA, genresB, genresC);
            List<GenresRating> pGRList = genresRatingMapper.findGenresRatingListByUserIdAndGenresId(pMap);
            RatingStats pRatingStats = ratingMapper.selectRatingStatsByUserId(pRating);
            
            float mpRatingRatio = mAvgRating / pRatingStats.getAvgRating();
            
            System.out.println("*****Euclidean distance");
            System.out.println(mAvgRating + ":" + pRatingStats.getAvgRating());
            System.out.println("mpRatingRatio:" + mpRatingRatio);
            
            float distanceT = 0F;
//            for (GenresRating gr : pGRList) {
            for (int i=0; i<pGRList.size(); i++) {
                GenresRating pGRB = pGRList.get(i);
                GenresRating mGRB = mGRList.get(i);
                float newAvgRating = pGRB.getAvgRating() * mpRatingRatio;
                pGRB.setAvgRating(newAvgRating);
                pGRB.setAvgDifference(newAvgRating - mAvgRating);
                
                // x坐标为流派平均评分, y坐标为流派评分所占用户评分比例
                float x1, y1, x2, y2;
                x1 = mGRB.getAvgRating();
                y1 = mGRB.getRatio();
                x2 = pGRB.getAvgRating();
                y2 = pGRB.getRatio();
                
                System.out.println("x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
                float temp = (float) Math.sqrt(Math.pow((double)(x1 - x2), 2) + Math.pow((double)(y1 - y2) * 10, 2));
                System.out.println(temp);
                distanceT = distanceT + temp;
            }
            distance[j] = distanceT;
            System.out.println(distanceT);
            
        }
        
        System.out.println("*****");
        
        for (int i=0; i<distance.length; i++) {
            System.out.println(i + ":" + distance[i] + ":" + theSamefavoriteGenresList.get(i).getUserId());
        }
        
        
        for (int i=0; i<3; i++) {
//            int a = -1;
            float b = 1000F;
            J:
            for (int j=0; j<distance.length; j++) {
                if (distance[j] < b) {
                    for (int k=0; k<index.length; k++) {
                        if (j == index[k]) {
                            continue J;
                        }
                    }
                    index[i] = j;
        //                  a = j;
                    b = distance[j];
                }
            }
        }
        
//        List<NearestNeighbor> nearestNeighborList = new ArrayList<>();
        
        NearestNeighbor nn = new NearestNeighbor();
        nn.setUserId(mGR.getUserId());
        nearestNeighborMapper.deleteNearestNeighborByUserId(nn);
        
        for (int i=0; i<theSamefavoriteGenresList.size(); i++) {
            NearestNeighbor nearestNeighbor = new NearestNeighbor();
            nearestNeighbor.setUserId(mGR.getUserId());
            nearestNeighbor.setNeighborId(theSamefavoriteGenresList.get(index[i]).getUserId());
            nearestNeighbor.setDistance(distance[index[i]]);
            
//            System.out.println("*****");
//            System.out.println(nearestNeighbor.getUserId() + ":" + nearestNeighbor.getNeighborId() + ":" + nearestNeighbor.getDistance());
            
//            nearestNeighborList.add(nearestNeighbor);
            
            nearestNeighborMapper.addNearestNeighbor(nearestNeighbor);
        }
        
        
        
        return favoriteGenresList.size();
    }

}
