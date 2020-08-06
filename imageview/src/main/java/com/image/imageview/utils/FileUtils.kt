package com.image.imageview.utils

import android.util.Log
import com.image.imageview.Constants
import com.image.imageview.enum.ImageType
import com.image.imageview.model.Image
import java.io.BufferedInputStream
import java.io.InputStream


object FileUtils {

    fun checkFileType(stream:InputStream):ImageType {
        //判断文件类型
        val b = ByteArray(28)
        //此处一定需要标记，否则读取后指针往下移动后下次读取数据时不会从起点开始读取
        stream.mark(0)
        stream.read(b, 0, 28);
        val hex = StringBuffer()
        for (i in b){
            val tempStr = Integer.toHexString(i.toInt() and 0xFF)
            if (tempStr.length == 1){
                hex.append("0")
            }
            hex.append(tempStr.toLowerCase())
        }
        //此处一定需要重置，否则读取后指针往下移动后下次读取数据时不会从起点开始读取
        stream.reset()
        //Log.e(Constants.TAG_ERROR,"文件类型：${hex.toString()}")
        return when {
            hex.toString().startsWith("789cec") -> {
                ImageType.SVGA
            }
            hex.toString().startsWith("47494638") -> {
                ImageType.GIF
            }
            hex.toString().startsWith("89504e47") -> {
                ImageType.PNG
            }
            hex.toString().startsWith("ffd8ff") -> {
                ImageType.JPEG
            }
            else -> {
                ImageType.UNKNOW
            }
        }
    }

    fun checkFuleType(type:String):ImageType{
        when(type){
            "image/gif" -> {
                return ImageType.GIF
            }
            "image/png" -> {
                return ImageType.PNG
            }
            "image/jpg" -> {
                return ImageType.JPEG
            }
            else -> {
                return ImageType.UNKNOW
            }
        }
    }

}