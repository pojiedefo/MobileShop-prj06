package com.huatec.edu.mobileshop.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.huatec.edu.mobileshop.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/8/31.
 */
public class ImageLoaderManager {
    private static ImageLoaderManager mInstance;
    public static ImageLoaderManager getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager();
                }
            }
        }
        return mInstance;
    }
    public ImageLoaderManager() {
        if (mInstance == null) {
            //采用自定义配置
            ImageLoader.getInstance().init(customImageLoaderConfig(MyApplication.getContext()));

            //采用默认配置
            ImageLoader.getInstance().init(defaultImageLoaderConfig());
        }
    }
    //Image-loader框架默认配置
    private ImageLoaderConfiguration defaultImageLoaderConfig() {
        return ImageLoaderConfiguration.createDefault(MyApplication.getContext());
    }

    //Image-loader框架自定义配置
    private ImageLoaderConfiguration customImageLoaderConfig(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(1024, 1920) // default = device screen dimensions 内存缓存文件的最大长宽
                //.memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                //.memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default

                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                //.diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个

                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .imageDownloader(new BaseImageDownloader(context)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        return config;
    }

    //Image-loader框架显示图片的配置参数
    public static DisplayImageOptions product_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.image_loading) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.image_empty) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.image_error) // 设置图片加载或解码过程中发生错误显示的图片
            .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
            .delayBeforeLoading(1000)  // 下载前的延迟时间
            .cacheInMemory(true) // default false  设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // default false 设置下载的图片是否缓存在SD卡中
            .considerExifParams(false) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
            .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
            .handler(new Handler()) // default
            .build();
    public static DisplayImageOptions user_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.image_loading) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.face_default) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.face_default) // 设置图片加载或解码过程中发生错误显示的图片
            .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
            .delayBeforeLoading(1000)  // 下载前的延迟时间
            .cacheInMemory(true) // default false 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // default false 设置下载的图片是否缓存在SD卡中
            .considerExifParams(false) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
            .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
            .handler(new Handler()) // default
            .build();

    /**
     * 缩放类型mageScaleType:
     EXACTLY :图像将完全按比例缩小的目标大小
     EXACTLY_STRETCHED:图片会缩放到目标大小完全
     IN_SAMPLE_INT:图像将被二次采样的整数倍
     IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
     NONE:图片不会调整
     */
    /**
     *  显示方式displayer：
     RoundedBitmapDisplayer（int roundPixels）设置圆角图片
     FakeBitmapDisplayer（）这个类什么都没做
     FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
     SimpleBitmapDisplayer()正常显示一张图片
     */
}
