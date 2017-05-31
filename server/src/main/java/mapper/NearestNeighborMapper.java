package mapper;

import pojo.NearestNeighbor;

public interface NearestNeighborMapper {
    
    int addNearestNeighbor(NearestNeighbor nearestNeighbor);
    void deleteNearestNeighborByUserId(NearestNeighbor nearestNeighbor);

}
