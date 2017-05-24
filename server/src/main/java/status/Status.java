package status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {
    
    public static int batchNewThreadCount = 0;
    public static boolean batchNewThreadFlag = true;
//    public static float batchNewThreadProgress = 0.0F;  //一个线程会抓取两百个用户的页面
    public static Map<String, Float> batchNewThreadProgressMap = new HashMap<>();
    
    private Status() {}

}
