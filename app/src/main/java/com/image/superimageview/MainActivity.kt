package com.image.superimageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.image.imageview.Constants
import com.image.imageview.utils.CacheUtils
import com.image.imageview.utils.ThreadUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.requestUrlImage("wel_code_31.svga")
        //svg.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
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
        test21.requestUrlImage("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3080605948,3091584958&fm=26&gp=0.jpg")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}