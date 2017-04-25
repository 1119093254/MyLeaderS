package com.multshows.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.FlowerAndReword_Activity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Activity.NewsLikeActivity;
import com.multshows.Beans.Idle_ProduceComment_Beans;
import com.multshows.Beans.Idle_ProduceDetails_Bean;
import com.multshows.Beans.ShowsComment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.ColorsConstant;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.preview.ImagePreviewActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：宝贝详情适配器
 * 作者：李萧萧
 * 时间：2016.10.7
 */
public class Adapter_Idle_producedetails_Adapter extends BaseAdapter {

    private Context mContext;
    List<Idle_ProduceDetails_Bean> mList;
    ViewHolder mViewHolder;
    private ImageAdapter mImageAdapter;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();//偏好设置
    private MyApplication mApplication;
    Dialog_Hint mDialogHint;//确认删除
    Dialog mDialog;//消息提示框

    public Adapter_Idle_producedetails_Adapter(Context mContext, List<Idle_ProduceDetails_Bean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mApplication= (MyApplication) mContext.getApplicationContext();
        mDialog = new HintText_Dialog(mContext, R.style.MyDialog);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_idle_produce_details, null, false);
            mViewHolder.mImageListVIew = (MyListView) convertView.findViewById(R.id.idle_details_recyclerview);
            mViewHolder.mListVIew = (MyListView) convertView.findViewById(R.id.commentListview);
            mViewHolder.mUsername = (TextView) convertView.findViewById(R.id.idle_detailsusername);
            mViewHolder.mTime = (TextView) convertView.findViewById(R.id.idle_detailsdate);
            mViewHolder.mPrice = (TextView) convertView.findViewById(R.id.idle_detailsmoney);
            mViewHolder.idle_num = (TextView) convertView.findViewById(R.id.idle_num);
            mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.idle_detailstitle);
            mViewHolder.mContent = (TextView) convertView.findViewById(R.id.Idledetailscontent);
            mViewHolder.mLocation = (TextView) convertView.findViewById(R.id.idle_details_address);
            mViewHolder.LiketextNum = (TextView) convertView.findViewById(R.id.LiketextNum);
            mViewHolder.Idlelikename = (TextView) convertView.findViewById(R.id.Idlelikename);
            mViewHolder.mLookNum = (TextView) convertView.findViewById(R.id.lookNum);
            mViewHolder.mUserPic = (SimpleDraweeView) convertView.findViewById(R.id.idle_detailsuserpic);
            mViewHolder.likeuserpicList = (RelativeLayout) convertView.findViewById(R.id.likeuserpicList);
            mViewHolder.idle_details_addressImage = (ImageView) convertView.findViewById(R.id.idle_details_addressImage);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final Idle_ProduceDetails_Bean bean = mList.get(position);
        mViewHolder.mUsername.setText(bean.getName());
        Uri uri = Uri.parse(bean.getUserpic());
        mViewHolder.mUserPic.setImageURI(uri);
        mViewHolder.idle_num.setText("库存：" + bean.getIdlenum());
        mViewHolder.mTime.setText(DateDeal.format(Time_Now.getStringTime(Integer.parseInt(bean.getTime()))));
        mViewHolder.mPrice.setText("¥" + bean.getPrice());
        mViewHolder.mContent.setText(bean.getContent());
        mViewHolder.mContent.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(bean.getContent())));
        mViewHolder.mTitle.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(bean.getTitle())));
        if (!bean.getLocation().equals("")) {
            String mLocation=bean.getLocation();
            mLocation = mLocation.replace(" ", "");
            mViewHolder.mLocation.setText("来自" + mLocation);
        } else {
            mViewHolder.mLocation.setText("");
            mViewHolder.idle_details_addressImage.setVisibility(View.INVISIBLE);
        }

        mViewHolder.mLookNum.setText(bean.getNum() + "人浏览过");
        if (bean.getPicString() != null) {
            mImageAdapter = new ImageAdapter(bean.getPicString());
            mViewHolder.mImageListVIew.setAdapter(mImageAdapter);
        }
        if (bean.getWorkComment_beanes() != null) {
            Idle_Comment_Adapter adapter = new Idle_Comment_Adapter(mContext, bean.getWorkComment_beanes(), bean.getUserbaseId());
            mViewHolder.mListVIew.setAdapter(adapter);
        }
        mViewHolder.mUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                saveSharedPreferences.Save_PREFS(mContext, "otherId", bean.getUserid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                mContext.startActivity(mIntent);
            }
        });

        if (bean.getPicLikeString().size() > 0) {
            mViewHolder.likeuserpicList.setVisibility(View.VISIBLE);
            StringBuffer mStringBuffer = new StringBuffer();
            for (int j = 0; j <bean.getPicLikeString().size(); j++) {
                mStringBuffer.append(bean.getPicLikeString().get(j).getNickName());
                if (j != bean.getPicLikeString().size() - 1)
                    mStringBuffer.append("、");
            }
            mViewHolder.Idlelikename.setText(mStringBuffer);
            mViewHolder.LiketextNum.setText("等"+bean.getPicLikeString().size()+"人觉得很赞");
        }else mViewHolder.likeuserpicList.setVisibility(View.INVISIBLE);
        mViewHolder.likeuserpicList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("mLikeBeans",  bean);
                mIntent.putExtra("type", "赞");
                mContext.startActivity(mIntent);
            }
        });
        mViewHolder.mListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(mList.get(0).getUserid().equals(Login_Static.myUserID)){
                    mDialogHint = new Dialog_Hint(mContext, 0,
                            "确认删除吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeleteComment( bean.getWorkComment_beanes(),position);
                            mDialogHint.dismiss();
                        }
                    });
                    mDialogHint.show();
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        MyListView mImageListVIew;//图片详情集合
        MyListView mListVIew;//评论集合
        TextView mUsername;//用户昵称
        ImageView idle_details_addressImage;
        SimpleDraweeView mUserPic;//用户头像
        TextView mTime;//发表时间
        TextView mPrice;//售价
        TextView idle_num;//库存数量
        TextView mContent;//文字介绍
        TextView mTitle;//标题
        TextView mLocation;//发表位置
        TextView mLookNum;//浏览数量
        TextView Idlelikename;//赞过人姓名
        TextView LiketextNum;//赞过人数量
        SimpleDraweeView mIdleImage;//图片详情
        RelativeLayout likeuserpicList;//赞人列表
        //赞的人头像列表显示控件，（动态添加该布局的话会导致滑动时该布局重绘，导致界面卡顿）
    }

    class ImageAdapter extends BaseAdapter {
        List<String> picString;

        public ImageAdapter(List<String> picString) {
            this.picString = picString;
        }

        @Override
        public int getCount() {
            return picString.size();
        }

        @Override
        public Object getItem(int position) {
            return picString.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_idle_imageitem, null, false);
                mViewHolder.mIdleImage = (SimpleDraweeView) convertView.findViewById(R.id.idle_image);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            Uri uri = Uri.parse(SubString_Utils.getImageUrl4(picString.get(position)));
            mViewHolder.mIdleImage.setImageURI(uri);
            final ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int i = 0; i < picString.size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(SubString_Utils.getImageUrl3(picString.get(i)));
                info.setBigImageUrl(SubString_Utils.getImageUrlclearly(picString.get(i)));
                imageInfo.add(info);
            }
            mViewHolder.mIdleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, imageInfo);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }
            });
            return convertView;
        }
    }
    //删除评论
    private void DeleteComment(final List<Idle_ProduceComment_Beans> mMList, final int position){
        OkHttpUtils.get().url(mApplication.getUrl() + "/Comment/DelComment")
                .addParams("commentId", mMList.get(position).getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", e.toString());
                        mDialog.show();
                        showError("删除失败", 0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialog.show();
                                showError("删除成功", 1);
                                mMList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                mDialog.show();
                                showError("删除失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //发布结果提示
    public void showError(final String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(mContext, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                mDialog.dismiss();
            }
        }, 2000);
    }
}
