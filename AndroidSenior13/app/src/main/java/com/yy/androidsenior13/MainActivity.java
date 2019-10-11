package com.yy.androidsenior13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String url = "https://www.xuexi.cn/lgpage/images/5c0cbdcb8a12e151903b3f14e3babc6f.png";
    private String errorUrl = "https://www.xuexi.cn/lgpage/images/5c0cbdcb8a12e151903b3f14e3babc6f.png1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void base(View view) {//方法调用顺序不能乱
        Glide.with(this)
                .load(url)
                .into(imageView);
    }

    public void placeholder(View view) {
        Glide.with(this)
                .load(errorUrl)
                .placeholder(R.drawable.bitmap)
                .into(imageView);
    }

    public void error(View view) {
        Glide.with(this)
                .load(errorUrl)
                .placeholder(R.drawable.bitmap)
                .error(R.drawable.error)
                .into(imageView);
    }


    public void requestOptions(View view) {
        RequestOptions cropOption = new RequestOptions().centerCrop();
        Glide.with(this)
                .load(url)
                .apply(cropOption)
                .into(imageView);
    }

    public void transitionOption(View view) {
        Glide.with(this)
                .load(url)
                .transition(withCrossFade())
                .into(imageView);
    }

    public void thumbnail(View view) {
        Glide.with(this)
                .load(url)
                .thumbnail(0.25f)
                .into(imageView);
    }

    public void errorStartNew(View view) {
        Glide.with(this)
                .load(url)
                .error(Glide.with(this).load(url))
                .into(imageView);
    }

    public void circleCrop(View view) {
        Glide.with(this)
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    public void roundedCorners(View view) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                .into(imageView);
    }

    public void cacheStrategy(View view) {
        Glide.with(this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public void gif(View view) {
        Glide.with(this)
                .load(R.drawable.timg)
                .into(imageView);
    }

    public void blur(View view) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5,2)))
                .into(imageView);
    }

}
