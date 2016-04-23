package sse.tongji.localization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TDUtils {

    private static ExecutorService totalPool;

    static {
        totalPool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable command) {
        totalPool.execute(command);
    }

}
