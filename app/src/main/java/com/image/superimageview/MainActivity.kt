package com.image.superimageview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.image.imageview.utils.CacheUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var adapter:ImageAdapter = ImageAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*        test.requestUrlImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595768596427&di=2f2800f00ad226cad117cb52b628e456&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201905%2F02%2F20190502231740_iP2Bk.thumb.400_0.gif")
        test1.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test2.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test3.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test4.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test5.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test6.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test7.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test8.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test9.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test10.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test12.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test13.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test14.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test15.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test16.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test17.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test18.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test19.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test20.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        test21.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")*/
        val imageList = mutableListOf<String>()
        for (i in 0..500){
            imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
        }
        Log.e("日志","长度:${imageList.size}")
        val ll = LinearLayoutManager(this)
        imageListRv.layoutManager = ll
        imageListRv.adapter = adapter
        adapter.setImageList(imageList)
        clearCache.setOnClickListener {
            CacheUtils.clearCacheFile(baseContext.cacheDir.absolutePath)
        }

/*        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595768596427&di=2f2800f00ad226cad117cb52b628e456&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201905%2F02%2F20190502231740_iP2Bk.thumb.400_0.gif").into(test)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test1)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test2)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test3)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test4)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test5)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test6)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test7)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test8)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test9)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test10)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test12)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test13)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test14)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test15)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test16)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test17)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test18)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test19)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test20)
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg").into(test21)*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}