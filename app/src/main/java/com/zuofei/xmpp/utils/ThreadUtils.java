package com.zuofei.xmpp.utils;


import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {
    public static Handler handler = new Handler();

    /**子线程执行task*/
    public static void runInThread(Runnable task){
        new Thread(task).start();
    }

    /**UI线程执行task*/
    public static void runInUIThread(Runnable task){
        Looper.prepare();
        handler.post(task);
        Looper.loop();
    }
}
