package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class GoodsViewPagerAdapter extends PagerAdapter {

    private List<String> mImages;
    private Context mContext;

    public GoodsViewPagerAdapter(Context mContext, List<String> mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
    }

    /**
     *  获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
     * @return
     */
    @Override
    public int getCount() {
        return mImages.size();
    }

    /**
     * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    /**
     * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(mContext);
        ImageLoader.getInstance().displayImage(mImages.get(position),iv, ImageLoaderManager.product_options);
        System.out.println("url:"+mImages.get(position));
        ((ViewPager)container).addView(iv, 0);
        return iv;
    }

    /**
     * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
