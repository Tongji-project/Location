package sse.tongji.localization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Package tj.sse.wow.Util
 * @Description 线程池
 * @author 黄日辉
 */
public class TDUtils {

    private static ExecutorService totalPool;

    static {
        totalPool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable command) {
        totalPool.execute(command);
    }

}
