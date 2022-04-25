package untils;

import java.util.Random;
import java.util.UUID;

/**
 * @description 提供生成唯一ID的方法
 * @author Chen Lin
 * @date 2022/1/24 22:06
 */
public class IdUtils {

    /**
     * @description 生成32位的唯一ID，缺点是查找的时候性能低一些
     * @author Chen Lin
     * @date 2022/1/24 19:03
     * @return java.lang.String
     */
    public static String getUniqueIdByUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * @description 使用时间生成唯一的ID，是14位的数字
     * @author Chen Lin
     * @date 2022/1/24 19:38
     * @return java.lang.String
     */
    public static String getUniqueIdByTime(){
        String time = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String tag = random.nextInt(10)+"";
        return time+tag;
    }

    public static void main(String[] args) {
        System.out.println(getUniqueIdByTime());
    }
}

