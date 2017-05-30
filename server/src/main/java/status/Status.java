package status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {
    
    public volatile static int batchNewThreadCount = 0;
    public static boolean batchNewThreadFlag = true;
    public volatile static Map<String, Float> batchNewThreadProgressMap = new HashMap<>();
    
    
    private Status() {}

}
