package mapper;

import java.util.List;

import pojo.NearestNeighbor;

public interface NearestNeighborMapper {
    
    int addNearestNeighbor(NearestNeighbor nearestNeighbor);
    void deleteNearestNeighborByUserId(NearestNeighbor nearestNeighbor);
    List<NearestNeighbor> findNearestNeighborListByUserId(NearestNeighbor nearestNeighbor);

}
