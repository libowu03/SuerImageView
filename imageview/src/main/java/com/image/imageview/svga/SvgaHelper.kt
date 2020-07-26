package com.image.imageview.svga

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.animation.LinearInterpolator
import com.image.imageview.Constants
import com.image.imageview.utils.CacheUtils
import com.image.imageview.view.SuperImageView
import com.opensource.svgaplayer.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class SvgaHelper(context:Context,imageView: SuperImageView,clearsAfterStop:Boolean) {
    private var context:Context?=null
    private var clearsAfterStop = true
    private var imageView:SuperImageView?=null
    private var animator: ValueAnimator? = null
    var fillMode = SVGAImageView.FillMode.Forward
    var callback: SVGACallback? = null

    init {
        this.context = context
        this.clearsAfterStop = clearsAfterStop
        this.imageView = imageView
    }

    /**
     * 获取动画
     */
    fun getAnimator():ValueAnimator?{
        return animator
    }

    /**
     * 请求svga
     */
    fun requestSvga(url:String,stream:InputStream,handler:Handler){
        context?.let {
            val parser = SVGAParser(it)
            if(url.startsWith("http://") || url.startsWith("https://") && stream != null) {
                parser.parse(stream,CacheUtils.createCacheKey(url), object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        handler?.post {
                            videoItem.antiAlias = true
                            setVideoItem(videoItem)
                            if (/*typedArray.getBoolean(R.styleable.SVGAImageView_autoPlay, true)*/true) {
                                startAnimation()
                            }
                        }
                    }

                    override fun onError() { }
                })
                return
            }
            parser.parse(url, object : SVGAParser.ParseCompletion {
                override fun onComplete(videoItem: SVGAVideoEntity) {
                    handler?.post {
                        videoItem.antiAlias = true
                        setVideoItem(videoItem)
                        if (/*typedArray.getBoolean(R.styleable.SVGAImageView_autoPlay, true)*/true) {
                            startAnimation()
                        }
                    }
                }

                override fun onError() { }
            })
        }
    }

    /**
     * 设置svga数据
     */
    private fun setVideoItem(videoItem: SVGAVideoEntity) {
        setVideoItem(videoItem, SVGADynamicEntity())
    }

    /**
     * 设置svga数据
     */
    private fun setVideoItem(videoItem: SVGAVideoEntity, dynamicItem: SVGADynamicEntity) {
        val drawable = SVGADrawable(videoItem, dynamicItem)
        val field = drawable::class.java.getDeclaredField("cleared")
        field.isAccessible = true
        field.set(drawable,clearsAfterStop)
        imageView?.setImageDrawable(drawable)
    }

    /**
     * 开始执行svga动画
     */
    fun startAnimation() {
        imageView?.let {imageView->
            val drawable = imageView.drawable as? SVGADrawable ?: return
            val field = drawable::class.java.getDeclaredField("cleared")
            field.isAccessible = true
            field.set(drawable,false)

            drawable.scaleType = imageView.scaleType
            drawable.videoItem?.let {
                var durationScale = 1.0
                val animator = ValueAnimator.ofInt(0, it.frames - 1)
                try {
                    Class.forName("android.animation.ValueAnimator")?.let {
                        it.getDeclaredField("sDurationScale")?.let {
                            it.isAccessible = true
                            it.getFloat(Class.forName("android.animation.ValueAnimator"))?.let {
                                durationScale = it.toDouble()
                            }
                        }
                    }
                } catch (e: Exception) {
                    if (e.localizedMessage != null){
                        Log.e(Constants.TAG_ERROR,e.localizedMessage)
                    }
                }
                animator.interpolator = LinearInterpolator()
                animator.duration = (it.frames * (1000 / it.FPS) / durationScale).toLong()
                animator.repeatCount = if (imageView.loops <= 0) 99999 else imageView.loops - 1
                animator.addUpdateListener {
                    val currentFrameField = drawable::class.java.getDeclaredField("currentFrame")
                    currentFrameField.isAccessible = true
                    currentFrameField.set(drawable,animator.animatedValue as Int)
                    callback?.onStep(drawable.currentFrame, ((drawable.currentFrame + 1).toDouble() / drawable.videoItem.frames.toDouble()))
                    imageView.invalidateDrawable(drawable)
                }
                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                        callback?.onRepeat()
                    }
                    override fun onAnimationEnd(animation: Animator?) {
                        stopAnimation()
                        if (!clearsAfterStop) {
                            if (fillMode == SVGAImageView.FillMode.Backward) {
                                val currentFrameField = drawable::class.java.getDeclaredField("currentFrame")
                                currentFrameField.isAccessible = true
                                currentFrameField.set(drawable,0)
                            }
                        }
                        callback?.onFinished()
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                animator.start()
                this.animator = animator
            }
        }

    }

    /**
     * 暂停svga动画
     */
    fun pauseAnimation() {
        stopAnimation(false)
        callback?.onPause()
    }

    /**
     * 停止svga动画
     */
    fun stopAnimation() {
        stopAnimation(clear = clearsAfterStop)
    }

    /**
     * 停止svga动画并清楚数据
     */
    fun stopAnimation(clear: Boolean) {
        animator?.cancel()
        animator?.removeAllUpdateListeners()
        imageView?.let {
            (it.drawable as? SVGADrawable)?.let {
                val clearedField = it::class.java.getDeclaredField("cleared")
                clearedField.isAccessible = true
                clearedField.set(it,clear)
            }
        }

    }
}