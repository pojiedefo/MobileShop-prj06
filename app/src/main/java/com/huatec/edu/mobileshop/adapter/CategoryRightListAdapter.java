package com.huatec.edu.mobileshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.ImageLoaderManager;
import com.huatec.edu.mobileshop.entity.CategoryEntity;
import com.huatec.edu.mobileshop.utils.DesityUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class CategoryRightListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener {
    private Context mContext;
    private List<CategoryEntity> mRightData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CategoryRightListAdapter(Context context, List<CategoryEntity> rightData) {
        this.mContext = context;
        this.mRightData = rightData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list_right, parent, false);
        //获取左侧列表的宽度
        int left_width = (int) mContext.getResources().getDimension(R.dimen.category_list_left_width);
        //获取手机的屏幕宽度
        int width = DesityUtils.getWidth(mContext);
        //设置右侧列表的每个选项的宽度和高度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (width - left_width) / 3,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder holder = new ViewHolder(view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CategoryRightListAdapter.ViewHolder){
            final CategoryRightListAdapter.ViewHolder newHolder = ( CategoryRightListAdapter.ViewHolder) holder;
            CategoryEntity entity = mRightData.get(position);
            //适配item数据
            newHolder.title1.setText(entity.getName());
            ImageLoader.getInstance().displayImage(entity.getImage(), newHolder.image1,
                    ImageLoaderManager.product_options, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            //设置图片的高度
                            int image_width = (int) mContext.getResources().getDimension(R.dimen
                                    .category_list_right_image_width);
                            //重新计算图片，让图片始终都显示为正方形
                            if (loadedImage != null) {
                                Bitmap bmp = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage
                                        .getHeight());
                                bmp = Bitmap.createScaledBitmap(bmp, image_width, image_width, false);
                                newHolder.image1.setImageBitmap(bmp);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });

            holder.itemView.setTag(entity);
        }


    }

    @Override
    public int getItemCount() {
        return mRightData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (CategoryEntity) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title1;
        private ImageView image1;

        public ViewHolder(View itemView) {
            super(itemView);

            title1 = (TextView) itemView.findViewById(R.id.category_item_have_picture_text_1);
            image1 = (ImageView) itemView.findViewById(R.id.category_item_have_picture_image_1);
        }
    }
}
