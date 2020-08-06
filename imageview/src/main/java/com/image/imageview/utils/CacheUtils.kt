package com.image.imageview.utils

import android.graphics.BitmapFactory
import android.util.LruCache
import com.image.imageview.enum.ImageType
import com.image.imageview.model.Image
import java.io.*
import java.security.MessageDigest

/**
 * 缓存帮助类
 */
object CacheUtils {
    private var cache:LruCache<String,Image?>?=null

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
    fun addBitmap(key:String,bitmap: Image?){
        synchronized(this){
            if (cache == null) {
                //获取最大内存
                val maxMemory = Runtime.getRuntime().maxMemory().toInt()
                //设置用户缓存的最大内存
                val cacheSize = maxMemory / 8
                cache = LruCache<String, Image?>(cacheSize)
                /*   cache =  object : LruCache<String, Image?>(cacheSize) {
                    override fun sizeOf(key: String, value: Image?): Int {
                        if (bitmap?.bitmap != null){
                            //return bitmap.bitmap.byteCount / 1024
                            if (bitmap?.bitmap is Bitmap){
                                return (bitmap!!.bitmap as Bitmap).byteCount/1024
                            }
                        }
                        return super.sizeOf(key, value)
                    }
                }
            }*/
            }
            cache?.put(key,bitmap)
        }
    }

    /**
     * 获取图片缓存
     */
    fun getCacheBitmap(key:String):Image?{
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
    fun createCacheFile(bitmap:ByteArray?,key:String,cachePath:String){
        synchronized(this){
            if (bitmap == null){
                return
            }
            try{
                val path = cachePath+"/si-${key}"
                //L.e("地址：${path}")
                val file = File(path)
                if (file.exists()){
                    return
                    //file.delete()
                }
                val fileOutput = FileOutputStream(path)
                val dataOutput = DataOutputStream(fileOutput)
                val input = ByteArrayInputStream(bitmap)
                val buff = ByteArray(1024)
                var len = 0
                while (input.read(buff).also { len = it } != -1) {
                    dataOutput.write(buff, 0, len)
                }
                fileOutput.close()
                dataOutput.close()
            }catch (e:Exception){
                L.e("写入图片缓存失败"+e.localizedMessage)
            }
        }
    }

    /**
     * 清除图片缓存数据
     * @param cachePath 图片缓存地址
     */
    fun clearCacheFile(cachePath:String){
        val file = File(cachePath)
        val fileList = file.listFiles()
        fileList?.let {
            for (i in fileList){
                //如果存在文件，且名字开头是“si-”开头的文件，就进行删除操作
                if (i.exists() && i.isFile && i.name.startsWith("si-")){
                    i.delete()
                }
            }
        }
    }

    /**
     * 获取本地图片缓存
     */
    fun getCacheFile(key:String,cachePath:String):Image?{
        synchronized(this){
            try{
                val path = cachePath+"/si-${key}"
                //Log.e("日志","地址：${path}")
                val file = File(path)
                if (file.exists()){
                    val img = Image()
                    val factory = BitmapFactory.Options()
                    factory.inJustDecodeBounds = true
                    val input = BufferedInputStream(FileInputStream(file))
                    input.mark(0)
                    BitmapFactory.decodeStream(input,null,factory)
                    input.reset()
                    img.setHeight(factory.outWidth)
                    img.setWidth(factory.outWidth)
                    factory.inJustDecodeBounds = false
                    if (img.type == ImageType.GIF){
                        img.setBitmap(input.readBytes())
                    }else if (img.type == ImageType.JPEG || img.type == ImageType.PNG){
                        val bitmap = BitmapFactory.decodeStream(input)
                        img.setBitmap(bitmap)
                    }
                    input.close()
                    return img
                }else{
                    return null
                }
            }catch (e:Exception){
                if (e.localizedMessage != null){
                    L.e("读取缓存图片失败"+e.localizedMessage)
                }else{
                    L.e("读取缓存图片失败")
                }
                return null
            }
        }
    }
}