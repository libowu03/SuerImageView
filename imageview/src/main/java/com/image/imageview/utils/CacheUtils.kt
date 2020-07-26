package com.image.imageview.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.Log
import android.util.LruCache
import com.image.imageview.Constants
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.lang.ref.WeakReference
import java.security.MessageDigest

/**
 * 缓存帮助类
 */
object CacheUtils {
    private var cache:LruCache<String,Bitmap?>?=null

    /**
     * 通过MD5生成缓存的key
     */
    fun createCacheKey(imgUrl:String):String{
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(imgUrl.toByteArray(charset("UTF-8")))
        val tempData = md5.digest()
        val key = StringBuffer()
        for (item in tempData){
            key.append(String.format("%02x", item))
        }
        return key.toString()
    }

    /**
     * 添加图片到缓存中
     * @param key 缓存标记
     * @param bitmap 需要放到缓存内的图片
     */
    fun addBitmap(key:String,bitmap: Bitmap?){
        if (cache == null){
            //获取最大内存
            val maxMemory = Runtime.getRuntime().maxMemory().toInt()
            //设置用户缓存的最大内存
            val cacheSize = maxMemory/8
            cache = LruCache(cacheSize)
        }
        cache?.put(key,bitmap)
    }

    /**
     * 获取图片缓存
     */
    fun getCacheBitmap(key:String):Bitmap?{
        return cache?.get(key)
    }

    /**
     * 删除缓存
     */
    fun deleteCacheBitmap(key:String){
        cache?.remove(key)
    }

}