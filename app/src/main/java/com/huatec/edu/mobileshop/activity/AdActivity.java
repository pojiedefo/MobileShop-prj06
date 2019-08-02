package com.huatec.edu.mobileshop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.Constants;
import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class AdActivity extends AppCompatActivity {
    private ImageView adImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        adImage = (ImageView) findViewById(R.id.ad_image);
        //加载广告
        loadAd(Constants.AD_URL);
        Button skipBtn = (Button) findViewById(R.id.skip_button);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
    }
    //加载广告
    private void loadAd(String url) {
        ImageLoader.getInstance().displayImage(url, adImage, ImageLoaderManager.product_options, new
                ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        skip();
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        adImage.setImageBitmap(loadedImage);
                        timer();
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        skip();
                    }
                });
    }

    //过3秒后跳转
    private void timer() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == -1)
                    skip();
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constants.AD_TIME_SECOND);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(-1);
            }
        }.start();
    }

    /**
     * 跳过
     */
    private void skip() {
        startActivity(new Intent(AdActivity.this, MainActivity.class));
    }

}
