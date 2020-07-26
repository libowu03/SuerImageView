package com.image.imageview.utils;

import android.util.Log;

import com.image.imageview.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static ExecutorService service;

    public static void startMission(Runnable runnable){
        try{
            if (service == null){
                synchronized (ThreadUtils.class){
                    service = Executors.newFixedThreadPool(10);
                }
                service.execute(runnable);
            }else {
                service.execute(runnable);
            }
        }catch (Exception e){
            if (e.getLocalizedMessage() != null){
                Log.e(Constants.TAG_ERROR,e.getLocalizedMessage());
            }
        }
    }

    public static void shotdownMission(){
        try{
            if (service != null){
                service.shutdown();
            }
        }catch (Exception e){
            if (e.getLocalizedMessage() != null){
                Log.e(Constants.TAG_ERROR,e.getLocalizedMessage());
            }
        }
    }
}
