package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Utils.SubString_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujian on 2016/3/23.
 * RecyclingPagerAdapter是Jake WhartonAndroid大神封装的可用于复用的PagerAdapter。
 */
public class TubatuAdapter extends RecyclingPagerAdapter {

    private List<String> mList;
    private Context mContext;

    public TubatuAdapter(Context context,List<String> strList) {
        mList = new ArrayList<>();
        mContext = context;
        this.mList = strList;

    }
    public TubatuAdapter(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

   /* public void addAll(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        SimpleDraweeView imageView = null;
        if (convertView == null) {
            imageView = new SimpleDraweeView(mContext);
        } else {
            imageView = (SimpleDraweeView) convertView;
        }
        if(mList.get(position)!=null && !"null".equals(mList.get(position))) {
            imageView.setImageURI(Uri.parse(SubString_Utils.getImageUrl(mList.get(position))));
        }else {
            imageView.setImageURI(Uri.parse(""));
        }
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, strList.get(position), Toast.LENGTH_SHORT).show();
            }
        });*/
        return imageView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
