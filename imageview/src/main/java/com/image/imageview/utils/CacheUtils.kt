package com.image.imageview.utils

import android.content.Context
import android.os.Handler
import android.util.Log
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

}