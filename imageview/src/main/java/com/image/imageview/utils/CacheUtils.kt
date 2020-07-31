package com.image.imageview.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import com.image.imageview.Constants
import java.io.*
import java.lang.Exception
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
            cache = object : LruCache<String, Bitmap?>(cacheSize) {
                override fun sizeOf(key: String, value: Bitmap?): Int {
                    if (bitmap != null){
                        return bitmap.byteCount / 1024
                    }
                    return super.sizeOf(key, value)
                }
            }
        }
        if (cache?.get(key) == null){
            cache?.put(key,bitmap)
        }
    }

    /**
     * 获取图片缓存
     */
    fun getCacheBitmap(key:String):Bitmap?{
        return cache?.get(key)
    }

    /**
     * 获取cache中保存图片的数量
     */
    fun getCacheBitmapLength():Int{
        if (cache != null){
            return cache!!.size()
        }
        return 0
    }

    /**
     * 删除缓存
     */
    fun deleteCacheBitmap(key:String){
        cache?.remove(key)
    }

    /**
     * 保存缓到缓存目录中去
     * @param stream 图片流
     * @param key 图片的key
     */
    fun createCacheFile(bitmap:BufferedInputStream?,key:String,cachePath:String){
        synchronized(this){
            if (bitmap == null){
                return
            }
            try{
                val path = cachePath+"/si-${key}"
                //Log.e("日志","地址：${path}")
                val file = File(path)
                if (file.exists()){
                    return
                }
                val write = BufferedWriter(FileWriter(file))
                var readByte = bitmap.read()
                Log.e("日志","执行前参数：${readByte}")
                while (readByte != -1){
                    Log.e("日志","执行读写")
                    readByte = bitmap.read()
                    if (readByte != -1){
                        write.write(readByte)
                    }
                }
                bitmap.close()
                write.close()
               /* when(imageType){
                    "png" -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    "jpg","jpeg" -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    "webp" -> bitmap.compress(Bitmap.CompressFormat.WEBP, 100, out)
                }*/
            }catch (e:Exception){
                if (e.localizedMessage != null){
                    Log.e(Constants.TAG_ERROR,"写入图片缓存失败"+e.localizedMessage)
                }else{
                    Log.e(Constants.TAG_ERROR,"写入图片缓存失败")
                }
            }
        }
    }

    /**
     * 获取本地图片缓存
     */
    fun getCacheFile(key:String,cachePath:String):Bitmap?{
        synchronized(this){
            try{
                val path = cachePath+"/si-${key}"
                //Log.e("日志","地址：${path}")
                val file = File(path)
                if (file.exists()){
                    val input = BufferedInputStream(FileInputStream(file))
                    val bitmap = BitmapFactory.decodeStream(input)
                    input.close()
                    return bitmap
                }else{
                    return null
                }
            }catch (e:Exception){
                if (e.localizedMessage != null){
                    Log.e(Constants.TAG_ERROR,"读取缓存图片失败"+e.localizedMessage)
                }else{
                    Log.e(Constants.TAG_ERROR,"读取缓存图片失败")
                }
                return null
            }
        }
    }
}