package com.image.imageview.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import com.image.imageview.Constants
import com.image.imageview.R
import com.image.imageview.enum.ImageType
import com.image.imageview.svga.SvgaHelper
import com.image.imageview.utils.CacheUtils
import com.image.imageview.utils.FileUtils
import com.image.imageview.utils.ThreadUtils
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGAImageView
import pl.droidsonroids.gif.GifDrawable
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


open class SuperImageView : androidx.appcompat.widget.AppCompatImageView {
    private var roundValue = Array<Float>(8, { 0f })
    private var imgBitmap:Bitmap?=null
    private var handle:Handler = Handler()
    private var corner:Float = 0f
    private var topLeftCorner:Float = 0f
    private var topRightCorner:Float = 0f
    private var bottomLeftCorner:Float = 0f
    private var bottomRightCorner:Float = 0f
    private var enableCircle:Boolean = false
    private var loadingImage:Int = R.mipmap.loading
    private var httpUrl:String = ""
    private var clearsAfterStop = true
    private var animator: ValueAnimator? = null
    private var svgaHelper:SvgaHelper?=null
    private var oldThread:Thread?=null
    private val path = Path()
    var loops = -1
    var fillMode: SVGAImageView.FillMode = SVGAImageView.FillMode.Forward
    var callback: SVGACallback? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context?, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context?, attr: AttributeSet?, def: Int) : super(context, attr, def) {
        initAttrs(attr,def)
        initCornr(corner,topLeftCorner,topRightCorner,bottomLeftCorner,bottomRightCorner)
        initData()
    }

    /**
     * 获取xml参数
     */
    private fun initAttrs(attr: AttributeSet?, def: Int) {
        val parameters = context.theme.obtainStyledAttributes(attr,
            R.styleable.SuperImageView,def,0)
        corner = parameters.getDimension(R.styleable.SuperImageView_siCorner,0f)
        topLeftCorner = parameters.getDimension(R.styleable.SuperImageView_siTopLeftCorner,0f)
        topRightCorner = parameters.getDimension(R.styleable.SuperImageView_siTopRightCorner,0f)
        bottomLeftCorner = parameters.getDimension(R.styleable.SuperImageView_siBottomLeftCorner,0f)
        bottomRightCorner = parameters.getDimension(R.styleable.SuperImageView_siBottomRightCorner,0f)
        enableCircle = parameters.getBoolean(R.styleable.SuperImageView_siEnableCircle,false)
        loadingImage = parameters.getResourceId(R.styleable.SuperImageView_siLoadingImage,R.mipmap.loading)
        if (parameters.getString(R.styleable.SuperImageView_siImgHttpUrl) != null){
            httpUrl = parameters.getString(R.styleable.SuperImageView_siImgHttpUrl)!!
        }
        parameters.recycle()
    }

    /**
     * 设置圆角
     */
    private fun initCornr(
        corner: Float,
        topLeftCorner:Float,
        topRightCorner:Float,
        bottomLeftCorner:Float,
        bottomRightCorner:Float) {
        if (corner != 0f) {
            roundValue.set(0, corner)
            roundValue.set(1, corner)
            roundValue.set(2, corner)
            roundValue.set(3, corner)
            roundValue.set(4, corner)
            roundValue.set(5, corner)
            roundValue.set(6, corner)
            roundValue.set(7, corner)
        } else {
            //左上角
            roundValue.set(0, topLeftCorner)
            roundValue.set(1, topLeftCorner)
            //右上角
            roundValue.set(2, topRightCorner)
            roundValue.set(3, topRightCorner)
            //右下角
            roundValue.set(4, bottomRightCorner)
            roundValue.set(5, bottomRightCorner)
            //左下角
            roundValue.set(6, bottomLeftCorner)
            roundValue.set(7, bottomLeftCorner)
        }
    }

    private fun initData() {
        if (!httpUrl.isNullOrEmpty()){
            requestUrlImage(httpUrl)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (enableCircle){
            initCornr(Math.max(width,height)/2.0f,0f,0f,0f,0f)
        }
        path.addRoundRect(RectF(0f,0f, width.toFloat(), height.toFloat()),roundValue.toFloatArray(),Path.Direction.CW)
        canvas!!.clipPath(path)
        super.onDraw(canvas)
    }

    /**
     * 请求网络图片
     */
    fun requestUrlImage(imgUrl:Any){
        //Log.e("日志","执行request")
        try{
            oldThread?.interrupt()
        }catch (e:java.lang.Exception){
            if (e.localizedMessage != null){
                Log.e(Constants.TAG_ERROR,e.localizedMessage)
            }
        }
        if (imgUrl is String){
            val url = imgUrl as String
            setGif(loadingImage)
            //加载普通图片
            ThreadUtils.startMission {
                try{
                    oldThread = Thread.currentThread()
                    //先到缓存中获取内容,存在则直接使用缓存
                    val cacheKey = CacheUtils.createCacheKey(imgUrl as String)
                    var cacheBitmap = CacheUtils.getCacheBitmap(cacheKey)
                    //var cacheBitmap = CacheUtils.getCacheBitmap(cacheKey,context.cacheDir.absolutePath)
                    if (cacheBitmap != null && !cacheBitmap.isRecycled){
                        handle.post {
                            setImageBitmap(cacheBitmap)
                        }
                        Log.e("日志","读取缓存")
                        return@startMission
                    }else if (cacheBitmap != null && cacheBitmap.isRecycled){
                        //Log.e("日志","读取文件缓存")
                        CacheUtils.deleteCacheBitmap(cacheKey)
                        cacheBitmap = CacheUtils.getCacheFile(cacheKey,context.cacheDir.absolutePath)
                        if (cacheBitmap != null){
                            CacheUtils.addBitmap(cacheKey,cacheBitmap)
                            handle.post {
                                setImageBitmap(cacheBitmap)
                            }
                            return@startMission
                        }
                    }else if (cacheBitmap == null){
                        //Log.e("日志","读取文件缓存")
                        cacheBitmap = CacheUtils.getCacheFile(cacheKey,context.cacheDir.absolutePath)
                        if (cacheBitmap != null){
                            CacheUtils.addBitmap(cacheKey,cacheBitmap)
                            handle.post {
                                setImageBitmap(cacheBitmap)
                            }
                            return@startMission
                        }
                    }

                    if (url.startsWith("https://") || url.startsWith("https://")){
                        val path = URL(url)
                        val http: HttpURLConnection = path.openConnection() as HttpURLConnection
                        //设置请求方法
                        http.requestMethod = "GET"
                        //设置请求超时时间
                        http.connectTimeout = 4000
                        //获取请求返回码
                        val requestCode = http.responseCode
                        //判断返回码.返回码为200表示正常
                        if (requestCode == HttpURLConnection.HTTP_OK){
                            val stream = http.inputStream
                            val bufferStream = BufferedInputStream(stream)
                            val type = FileUtils.checkFileType(bufferStream)
                            dealWithDifferentTypeImage(type,url,bufferStream)
                        }
                    }else{
                        //获取文件类型
                        val stream = context.resources.assets.open(url)
                        val bufferStream = BufferedInputStream(stream)
                        val type = FileUtils.checkFileType(bufferStream)
                        //根据不同文件类型进行不同图片的加载
                        dealWithDifferentTypeImage(type,url,bufferStream)
                    }
                }catch (e:Exception){
                    if (e.localizedMessage != null){
                        Log.e(Constants.TAG_ERROR,e.localizedMessage)
                    }
                    Thread.currentThread()
                }
            }
        }else if (imgUrl is Int){
            try{
                val res: Resources = getResources()
                if (res != null) {
                    try {
                        setGif(imgUrl)
                    } catch (ignored: IOException) {
                        //ignored
                    } catch (ignored: NotFoundException) {
                    }
                }
                //Glide.with(context).load(imgUrl).centerCrop().into(this)
            }catch (e:java.lang.Exception){
                Log.e(Constants.TAG_ERROR,e.localizedMessage)
            }
        }
    }

    /**
     * 如果是gif图片，则加载gif，否则以普通形式加载
     */
    private fun setGif(imageId:Int){
        val factory = BitmapFactory.Options()
        factory.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,imageId,factory)
        //判断是不是gif，如果是，则使用gif加载器加载
        if (factory.outMimeType != null && factory.outMimeType == "image/gif"){
            val gif = GifDrawable(resources, imageId)
            setImageDrawable(gif)
        } else {
            setImageResource(imageId)
        }
    }

    /**
     * 通过类型处理不同图片的显示
     */
    private fun dealWithDifferentTypeImage(type:ImageType,url:String,stream:BufferedInputStream){
        when(type){
            ImageType.SVGA -> {
                //加载svga文件
                svgaHelper = SvgaHelper(context,this,clearsAfterStop)
                svgaHelper?.requestSvga(url,stream,handle)
            }
            ImageType.PNG,ImageType.JPEG -> {
                //保存图片到本地缓存
                stream.mark(0)
                CacheUtils.createCacheFile(stream,CacheUtils.createCacheKey(url),context.cacheDir.absolutePath)
                stream.reset()

                imgBitmap = BitmapFactory.decodeStream(stream)
                CacheUtils.addBitmap(CacheUtils.createCacheKey(url),imgBitmap)
                handle.post {
                    setImageBitmap(imgBitmap)
                }

            }
            ImageType.GIF -> {
                val streamByte = stream.readBytes()
                handle.post {
                    val gif = GifDrawable(streamByte)
                    setImageDrawable(gif)
                    gif.start()
                }
            }
        }
    }

    /**
     * 计算inSampleSize值
     *
     * @param options
     * 用于获取原图的长宽
     * @param reqWidth
     * 要求压缩后的图片宽度
     * @param reqHeight
     * 要求压缩后的图片长度
     * @return
     * 返回计算后的inSampleSize值
     */
    open fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // 原图片的宽高
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2


            // 计算inSampleSize值
            while (halfHeight / inSampleSize >= reqHeight
                && halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /**
     * view退出窗口后停止动画
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        svgaHelper?.getAnimator()?.let {
            animator?.cancel()
            animator?.removeAllUpdateListeners()
        }
    }

}