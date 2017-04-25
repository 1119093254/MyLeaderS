package com.multshows.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.FlowerAndReword_Activity;
import com.multshows.Activity.Idle_ProduceDetails_Activity;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.LookMoreComment_Activity;
import com.multshows.Activity.MainActivity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Activity.My_FlowerMarket_Activity;
import com.multshows.Activity.My_MyWallet_Activity;
import com.multshows.Activity.Search_2_Activity;
import com.multshows.Beans.ShowsCommentUser;
import com.multshows.Beans.ShowsFavouriteAdd;
import com.multshows.Beans.ShowsFlowerAdd;
import com.multshows.Beans.ShowsLikeAdd;
import com.multshows.Beans.ShowsRewardAdd;
import com.multshows.Beans.UserHead;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Interfaces.CommentInterface;
import com.multshows.Interfaces.MyPageCommentInterface;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Utils.Utils;
import com.multshows.Views.ActionItem;
import com.multshows.Views.Dialog_Flower;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.Dialog_Reward;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.HorizontalListView;
import com.multshows.Views.LayoutCriclePopup;
import com.multshows.Views.LayoutWorkPopup;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.NineGridView;
import com.multshows.Views.NineGridview.preview.NineGridViewClickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import okhttp3.Call;

/**
 * 描述：所有作品列表适配器
 * 作者：李萧萧
 * 时间：2016.9.22
 */
public class WorkAll_Adapter extends BaseAdapter implements LayoutCriclePopup.OnItemOnClickListener, LayoutWorkPopup.OnItemOnClickListener {
    List<WorkAll_Beans> mList;//作品列表bean对象
    WorkAll_Beans mTempBean;//按钮点击后临时存放 作品bean
    List<SimpleDraweeView> mSimpleDraweeViewList;
    private Context mContext;
    ViewHolder viewHolder = null;
    String mName;//点击赞昵称的名字
    SaveSharedPreferences saveSharedPreferences;//偏好设置
    Gson mGson = new Gson();
    MyApplication mApplication;
    Activity mActivity;
    CommentInterface mInterface;
    MyPageCommentInterface mMyPageInterface;
    Dialog mDialog;//消息提示框
    Dialog_Hint mDialogHint;//提示余额不足
    ActionItem mActionItem1;
    ActionItem mActionItem2;
    ActionItem mActionItem3;

    ActionItem mActionWork1;
    ActionItem mActionWork2;
    ActionItem mActionWork3;
    ActionItem mActionWork4;
    ActionItem mActionWork5;
    ActionItem mActionWork6;
    LayoutCriclePopup titlePopup;
    LayoutWorkPopup workPopup;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类

    public void getInterface(CommentInterface interface_) {
        mInterface = interface_;
    }

    public void getMyPageInterface(MyPageCommentInterface myPageInterface) {
        mMyPageInterface = myPageInterface;
    }


    public WorkAll_Adapter(Activity activity, Context mContext, List<WorkAll_Beans> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mActivity = activity;
        mSimpleDraweeViewList = new ArrayList<>();
        mApplication = (MyApplication) activity.getApplicationContext();
        saveSharedPreferences = new SaveSharedPreferences();
        mDialog = new HintText_Dialog(mContext, R.style.MyDialog);
        mActionItem1 = new ActionItem(mContext, "赞    ", R.drawable.momindex_iocn01);
        mActionItem2 = new ActionItem(mContext, "评论", R.drawable.momindex_iocn04);
        mActionItem3 = new ActionItem(mContext, "分享", R.drawable.item_share);

        mActionWork1 = new ActionItem(mContext, "赞    ", R.drawable.momindex_iocn01);
        mActionWork2 = new ActionItem(mContext, "送花", R.drawable.momindex_iocn02);
        mActionWork3 = new ActionItem(mContext, "打赏", R.drawable.momindex_iocn03);
        mActionWork4 = new ActionItem(mContext, "评论", R.drawable.momindex_iocn04);
        mActionWork5 = new ActionItem(mContext, "收藏", R.drawable.momindex_iocn05);
        mActionWork6 = new ActionItem(mContext, "购买", R.drawable.momindex_iocn06);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getVisiable();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }//布局类型种类总数

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (getItemViewType(i) == 1) {
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.adapter_allwork_item, null, false);
                viewHolder.itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
                viewHolder.mAllWorkuserpic = (SimpleDraweeView) view.findViewById(R.id.AllWorkuserpic);
                viewHolder.mAllWorkusername = (TextView) view.findViewById(R.id.Workusername);
                viewHolder.mAllWorktitle = (TextView) view.findViewById(R.id.AllWorktitle);
                viewHolder.mAllWorkcontent = (TextView) view.findViewById(R.id.AllWorkcontent);
                viewHolder.nineGrid = (NineGridView) view.findViewById(R.id.nineGrid);
                viewHolder.mAllWorkcomentImage = (ImageView) view.findViewById(R.id.AllWorkcomentImage);
                viewHolder.reward_userList = (HorizontalListView) view.findViewById(R.id.reward_userList);
                viewHolder.flower_userList = (HorizontalListView) view.findViewById(R.id.flower_userList);
                viewHolder.mAllWorkrewardnum = (TextView) view.findViewById(R.id.AllWorkrewardnum);
                viewHolder.mViewNumText = (TextView) view.findViewById(R.id.ViewNumText);
                viewHolder.mViewNumTextIamge = (ImageView) view.findViewById(R.id.ViewNumIamge);
                viewHolder.mAllWorklikename = (TextView) view.findViewById(R.id.AllWorklikename);
                viewHolder.mAllWorkflowerName = (TextView) view.findViewById(R.id.AllWorkflowerName);
                viewHolder.LookMoreComment = (RelativeLayout) view.findViewById(R.id.LookMoreComment);
                viewHolder.mWork_money = (TextView) view.findViewById(R.id.Work_money);
                viewHolder.commenimageLayoutLine = view.findViewById(R.id.commenimageLayoutLine);
                viewHolder.LikeTopline = view.findViewById(R.id.LikeTopline);
                viewHolder.flowerTopLine = view.findViewById(R.id.flowerTopLine);
                viewHolder.money_time = (TextView) view.findViewById(R.id.money_time);
                viewHolder.CommentNumVisiable = (TextView) view.findViewById(R.id.CommentNumVisiable);
                viewHolder.commentListview = (MyListView) view.findViewById(R.id.WorkcommentListview);
                viewHolder.mAllWorkrewardLayout = (RelativeLayout) view.findViewById(R.id.AllWorkrewardLayout);
                viewHolder.mAllWorkrewardUserImage = (RelativeLayout) view.findViewById(R.id.AllWorkrewardUserImage);
                viewHolder.mAllWorklikeLayout = (RelativeLayout) view.findViewById(R.id.AllWorklikeLayout);
                viewHolder.mAllWorkflowerLayout = (RelativeLayout) view.findViewById(R.id.AllWorkflowerLayout);
                viewHolder.userSelfSee = (RelativeLayout) view.findViewById(R.id.userSelfSee);
                viewHolder.DeleteText = (TextView) view.findViewById(R.id.DeleteText);
                viewHolder.AllWorkflowerView = view.findViewById(R.id.AllWorkflowerView);
                viewHolder.UserIden = (TextView) view.findViewById(R.id.UserIden);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final WorkAll_Beans beans = mList.get(i);
            //判断身份，身份显示
            if (beans.getUserType() == 1) {
                viewHolder.UserIden.setText("宝");
            } else if (beans.getUserType() == 0) {
                viewHolder.UserIden.setText("妈");
            }
            //判断是否是本人可删的作品，用来显示删除文字
            if (beans.getUserid().equals(Login_Static.myUserID) && beans.getIsTrade() == 0) {
                viewHolder.DeleteText.setVisibility(View.VISIBLE);
            } else {
                viewHolder.DeleteText.setVisibility(View.INVISIBLE);
            }
            viewHolder.mAllWorkusername.setText(beans.getUsername());
            Uri uri = Uri.parse(beans.getUserPic());
            viewHolder.mAllWorkuserpic.setImageURI(uri);
            viewHolder.mAllWorktitle.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getThemetitle())));
            viewHolder.mAllWorkcontent.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getContent())));
            //如果价格是0，隐藏该文本
            if (beans.getPrice().equals("0.0")) {
                viewHolder.mWork_money.setVisibility(View.INVISIBLE);
                viewHolder.mWork_money.setText("¥" + beans.getPrice());
            } else {
                viewHolder.mWork_money.setVisibility(View.VISIBLE);
                viewHolder.mWork_money.setText("¥" + beans.getPrice());
            }
            //浏览量
            if (beans.getViews() >= 0) {
                viewHolder.mViewNumText.setText(beans.getViews() + "人浏览过");
                viewHolder.mViewNumTextIamge.setVisibility(View.VISIBLE);
                viewHolder.mViewNumText.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mViewNumTextIamge.setVisibility(View.INVISIBLE);
                viewHolder.mViewNumText.setVisibility(View.INVISIBLE);
            }
            //获取图片名称，传至九宫格图片显示器
            if (beans.getImageStrinig().size() == 0 || !beans.getImageStrinig().get(0).equals("")) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                List<String> imageDetails = beans.getImageStrinig();
                if (imageDetails != null) {
                    for (String imageDetail : imageDetails) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(SubString_Utils.getImageUrl3(imageDetail));
                        info.setBigImageUrl(SubString_Utils.getImageUrlclearly(imageDetail));
                        CLog.e("getIsVideo", beans.getIsVideo() + "");
                        CLog.e("getIsVideo", beans.getVideoName() + "");
                        info.setImageViewHeight(beans.getIsVideo());
                        info.setIsVideo(beans.getIsVideo());
                        info.setVideoName(beans.getVideoName());
                        info.setWorkId(beans.getId());
                        imageInfo.add(info);
                    }
                }

                NineGridViewClickAdapter mGridViewClickAdapter = new NineGridViewClickAdapter(mContext, imageInfo);

                viewHolder.nineGrid.setAdapter(mGridViewClickAdapter);
            } else viewHolder.nineGrid.setVisibility(View.GONE);
            //本人可见的作品
            if (beans.getSoType() == 2) {
                viewHolder.userSelfSee.setVisibility(View.VISIBLE);
            } else {
                viewHolder.userSelfSee.setVisibility(View.GONE);
            }
            viewHolder.money_time.setText(DateDeal.format(Time_Now.getStringTime(Integer.parseInt(beans.getDate()))));
            viewHolder.DeleteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogHint = new Dialog_Hint(mContext, 0,
                            "确认删除吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeleteWork(beans, i);
                            mDialogHint.dismiss();
                        }
                    });
                    mDialogHint.show();
                }
            });
            //item点击事件
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CLog.e("李萧萧", "" + beans.getUserid() + " " + !beans.equals("0.0") + (beans.getType() != 1));

                    if (beans.getType() != 1) {
                        Intent mIntent = new Intent(mContext, Idle_ProduceDetails_Activity.class);
                        mIntent.putExtra("IdleID", beans.getId());
                        mIntent.putExtra("titlename", 1);//1代表是作品详情
                        mContext.startActivity(mIntent);
                    }

                }
            });
            //过滤打赏重复的人
            if (beans.getRewardimageUrl() != null)
                for (int n = 0; n < beans.getRewardimageUrl().size(); n++)  //外循环是循环的次数
                {
                    for (int j = beans.getRewardimageUrl().size() - 1; j > n; j--)  //内循环是 外循环一次比较的次数
                    {

                        if (beans.getRewardimageUrl().get(n).getUserId().equals(beans.getRewardimageUrl().get(j).getUserId())) {
                            beans.getRewardimageUrl().remove(j);
                        }

                    }
                }
            viewHolder.commenimageLayoutLine.setVisibility(View.VISIBLE);
            viewHolder.flowerTopLine.setVisibility(View.VISIBLE);
            viewHolder.LikeTopline.setVisibility(View.VISIBLE);

            if (beans.getRewardimageUrl() == null || beans.getRewardimageUrl().size() == 0) {
                if (beans.getFlowerUsername() == null || beans.getFlowerUsername().size() == 0) {

                } else {
                    viewHolder.flowerTopLine.setVisibility(View.INVISIBLE);
                }
            }
            if (beans.getRewardimageUrl() == null || beans.getRewardimageUrl().size() == 0) {
                if (beans.getFlowerUsername() == null || beans.getFlowerUsername().size() == 0) {
                    if (beans.getLikeUsername() == null || beans.getLikeUsername().size() == 0) {
                        viewHolder.commenimageLayoutLine.setVisibility(View.INVISIBLE);
                        viewHolder.flowerTopLine.setVisibility(View.INVISIBLE);
                        viewHolder.LikeTopline.setVisibility(View.INVISIBLE);
                    }
                }
            }

            if (beans.getRewardimageUrl() != null)
                if (beans.getRewardimageUrl().size() > 0) {
                    viewHolder.mAllWorkrewardLayout.setVisibility(View.VISIBLE);
                    if (beans.getRewardimageUrl().size() > 8) {
                        viewHolder.mAllWorkrewardnum.setVisibility(View.VISIBLE);
                        viewHolder.mAllWorkrewardnum.setText("+" + beans.getRewardimageUrl().size());
                    } else {
                        viewHolder.mAllWorkrewardnum.setVisibility(View.INVISIBLE);
                    }
                    List<UserHead> list2 = new ArrayList<>();
                    if (beans.getRewardimageUrl().size() > 8) {
                        for (int k = 0; k < 8; k++) {
                            list2.add(beans.getRewardimageUrl().get(k));
                        }
                    } else {
                        list2.addAll(beans.getRewardimageUrl());
                    }

                    HorizontalListViewAdapter mListAdapter = new HorizontalListViewAdapter(mContext, list2);
                    viewHolder.reward_userList.setAdapter(mListAdapter);


                    //动态添加打赏人头像列表显示viewHolder.rewardUserPic1


                /*if (beans.getRewardimageUrl().size() == 1) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic2.setVisibility(View.GONE);
                    viewHolder.rewardUserPic3.setVisibility(View.GONE);
                    viewHolder.rewardUserPic4.setVisibility(View.GONE);
                    viewHolder.rewardUserPic5.setVisibility(View.GONE);
                    viewHolder.rewardUserPic6.setVisibility(View.GONE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 2) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic3.setVisibility(View.GONE);
                    viewHolder.rewardUserPic4.setVisibility(View.GONE);
                    viewHolder.rewardUserPic5.setVisibility(View.GONE);
                    viewHolder.rewardUserPic6.setVisibility(View.GONE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 3) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic4.setVisibility(View.GONE);
                    viewHolder.rewardUserPic5.setVisibility(View.GONE);
                    viewHolder.rewardUserPic6.setVisibility(View.GONE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 4) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic5.setVisibility(View.GONE);
                    viewHolder.rewardUserPic6.setVisibility(View.GONE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 5) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(4).getPortrait()));
                    viewHolder.rewardUserPic5.setImageURI(urij);
                    viewHolder.rewardUserPic5.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic6.setVisibility(View.GONE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 6) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(4).getPortrait()));
                    viewHolder.rewardUserPic5.setImageURI(urij);
                    viewHolder.rewardUserPic5.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(5).getPortrait()));
                    viewHolder.rewardUserPic6.setImageURI(urij);
                    viewHolder.rewardUserPic6.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 6) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(4).getPortrait()));
                    viewHolder.rewardUserPic5.setImageURI(urij);
                    viewHolder.rewardUserPic5.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(5).getPortrait()));
                    viewHolder.rewardUserPic6.setImageURI(urij);
                    viewHolder.rewardUserPic6.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(6).getPortrait()));
                    viewHolder.rewardUserPic7.setImageURI(urij);
                    viewHolder.rewardUserPic7.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic7.setVisibility(View.GONE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() == 7) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(4).getPortrait()));
                    viewHolder.rewardUserPic5.setImageURI(urij);
                    viewHolder.rewardUserPic5.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(5).getPortrait()));
                    viewHolder.rewardUserPic6.setImageURI(urij);
                    viewHolder.rewardUserPic6.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(6).getPortrait()));
                    viewHolder.rewardUserPic7.setImageURI(urij);
                    viewHolder.rewardUserPic7.setVisibility(View.VISIBLE);
                    viewHolder.rewardUserPic8.setVisibility(View.GONE);
                } else if (beans.getRewardimageUrl().size() >= 8) {
                    Uri urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(0).getPortrait()));
                    viewHolder.rewardUserPic1.setImageURI(urij);
                    viewHolder.rewardUserPic1.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(1).getPortrait()));
                    viewHolder.rewardUserPic2.setImageURI(urij);
                    viewHolder.rewardUserPic2.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(2).getPortrait()));
                    viewHolder.rewardUserPic3.setImageURI(urij);
                    viewHolder.rewardUserPic3.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(3).getPortrait()));
                    viewHolder.rewardUserPic4.setImageURI(urij);
                    viewHolder.rewardUserPic4.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(4).getPortrait()));
                    viewHolder.rewardUserPic5.setImageURI(urij);
                    viewHolder.rewardUserPic5.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(5).getPortrait()));
                    viewHolder.rewardUserPic6.setImageURI(urij);
                    viewHolder.rewardUserPic6.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(6).getPortrait()));
                    viewHolder.rewardUserPic7.setImageURI(urij);
                    viewHolder.rewardUserPic7.setVisibility(View.VISIBLE);
                    urij = Uri.parse(SubString_Utils.getImageUrl(beans.getRewardimageUrl().get(7).getPortrait()));
                    viewHolder.rewardUserPic8.setImageURI(urij);
                    viewHolder.rewardUserPic8.setVisibility(View.VISIBLE);
                }*/

                } else viewHolder.mAllWorkrewardLayout.setVisibility(View.GONE);
            else viewHolder.mAllWorkrewardLayout.setVisibility(View.GONE);

            //送花头像处理，过滤相同人
            if (beans.getFlowerUsername() != null)
                if (beans.getFlowerUsername().size() > 0) {
                    for (int n = 0; n < beans.getFlowerUsername().size(); n++)  //外循环是循环的次数
                    {
                        for (int j = beans.getFlowerUsername().size() - 1; j > n; j--)  //内循环是 外循环一次比较的次数
                        {
                            if (beans.getFlowerUsername().get(n).getUserId().equals(beans.getFlowerUsername().get(j).getUserId())) {
                                beans.getFlowerUsername().remove(j);
                            }
                        }
                    }

                    viewHolder.mAllWorkflowerLayout.setVisibility(View.VISIBLE);
                    if (beans.getFlowerUsername().size() > 8) {
                        viewHolder.mAllWorkflowerName.setVisibility(View.VISIBLE);
                        viewHolder.mAllWorkflowerName.setText("+" + beans.getFlowerUsername().size());
                    } else viewHolder.mAllWorkflowerName.setVisibility(View.INVISIBLE);
                    List<UserHead> list2 = new ArrayList<>();
                    if (beans.getFlowerUsername().size() > 8) {
                        for (int k = 0; k < 8; k++) {
                            list2.add(beans.getFlowerUsername().get(k));
                        }
                    } else {
                        list2.addAll(beans.getFlowerUsername());
                    }
                    HorizontalListViewAdapter mFlower = new HorizontalListViewAdapter(mContext, list2);
                    viewHolder.flower_userList.setAdapter(mFlower);
                    //动态添加打赏人头像列表显示viewHolder.rewardUserPic1
                } else viewHolder.mAllWorkflowerLayout.setVisibility(View.GONE);
            else viewHolder.mAllWorkflowerLayout.setVisibility(View.GONE);
            // viewHolder.mAllWorktitle.setVisibility(View.GONE);
            if (beans.getThemetitle().equals("")) {
                viewHolder.mAllWorktitle.setVisibility(View.GONE);
            } else {
                viewHolder.mAllWorktitle.setVisibility(View.VISIBLE);
            }
            if (beans.getLikeUsername().size() > 0) {
                viewHolder.mAllWorklikeLayout.setVisibility(View.VISIBLE);
                StringBuffer mStringBuffer = new StringBuffer();
                for (int j = 0; j < beans.getLikeUsername().size(); j++) {
                    mStringBuffer.append(beans.getLikeUsername().get(j).getNickName());
                    if (j >= 9 || j == beans.getLikeUsername().size() - 1) {
                        mStringBuffer.append("等" + beans.getLikeUsername().size() + "人觉得很赞");
                        break;
                    } else mStringBuffer.append("、");
                }
                viewHolder.mAllWorklikename.setText(mStringBuffer);
            } else viewHolder.mAllWorklikeLayout.setVisibility(View.GONE);


      /*  if (beans.getLikeUsername().size() > 0) {
            StringBuffer mstringBuffer=new StringBuffer();
            for (int m = 0; m < beans.getLikeUsername().size(); m++) {
                if (m >= 9) {
                    mstringBuffer.append("<a style=\"color:blue;text-decoration:none;\" href='username'>"
                            + "等" +  beans.getLikeUsername().size() + "人觉得很赞" + " </a>");
                    break;
                }
                if (m !=  beans.getLikeUsername().size() - 1) {
                    mstringBuffer.append("<a style=\"color:blue;text-decoration:none;\"href='" + beans.getLikeUsername().get(m).getUserId() + "'>"
                            +  beans.getLikeUsername().get(m).getNickName() + "," + " </a>");
                    //mstringBuffer.append(bean.getZalistBeanses().getBeanList().get(i).getNickName() + "、");
                } else
                    mstringBuffer.append("<a style=\"color:blue;text-decoration:none;\"href='" + beans.getLikeUsername().get(m).getUserId() + "'>" +
                            beans.getLikeUsername().get(m).getNickName() + "觉得很赞" + " </a>");
            }
            viewHolder.mAllWorklikename.setText(Html.fromHtml(mstringBuffer.toString()));
        } else viewHolder.mAllWorklikeLayout.setVisibility(View.GONE);
        viewHolder.mAllWorklikename.setMovementMethod(LinkMovementMethod
                .getInstance());
        CharSequence text = viewHolder.mAllWorklikename.getText();
        int ends = text.length();
        Spannable spannable = (Spannable) viewHolder.mAllWorklikename.getText();
        URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
        SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
        stylesBuilder.clearSpans(); // should clear old spans
        for (URLSpan url : urlspan) {
            TextViewURLSpan myURLSpan = new TextViewURLSpan(url.getURL());
            stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                    spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        viewHolder.mAllWorklikename.setText(stylesBuilder);
        viewHolder.mAllWorklikename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mName",""+mName);
                if (beans.getLikeUsername().size() > 0) {
                    List<UserHead>  BeanList = beans.getLikeUsername();
                    for (int i = 0; i < BeanList.size(); i++) {
                        UserHead beans2 = BeanList.get(i);
                        if (beans2.getUserId().equals(mName)) {
                            Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                            saveSharedPreferences.Save_PREFS(mContext, "otherId", beans2.getUserId());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            mContext.startActivity(mIntent);
                        }
                    }

                }
            }
        });*/
   /*     if (beans.getLikeUsername().size() > 0) {
            viewHolder.mAllWorklikeLayout.setVisibility(View.VISIBLE);
            StringBuffer mStringBuffer = new StringBuffer();
            for (int j = 0; j < beans.getLikeUsername().size(); j++) {
                mStringBuffer.append(beans.getLikeUsername().get(j).getNickName());
                if (j == beans.getLikeUsername().size() - 1)
                    mStringBuffer.append("等" + beans.getLikeUsername().size() + "人觉得很赞");
                else mStringBuffer.append("、");
            }
            viewHolder.mAllWorklikename.setText(mStringBuffer);
        }*/


            if (beans.getCommentBeansList() != null && beans.getCommentBeansList().size() > 0) {
                List<ShowsCommentUser> bean = new ArrayList<>();
                viewHolder.commentListview.setVisibility(View.VISIBLE);
                viewHolder.AllWorkflowerView.setVisibility(View.VISIBLE);
                if (beans.getCommentBeansList().size() > 3) {
                    viewHolder.LookMoreComment.setVisibility(View.VISIBLE);
                    viewHolder.CommentNumVisiable.setText("查看全部" + beans.getCommentBeansList().size() + "条评论");
                    bean.add(beans.getCommentBeansList().get(0));
                    bean.add(beans.getCommentBeansList().get(1));
                    bean.add(beans.getCommentBeansList().get(2));
                } else {
                    viewHolder.LookMoreComment.setVisibility(View.GONE);
                    bean.addAll(beans.getCommentBeansList());
                }
                WorkAll_Comment_Adapter commentAdapter = new WorkAll_Comment_Adapter(mContext, bean);
                viewHolder.commentListview.setAdapter(commentAdapter);
            } else {
                viewHolder.LookMoreComment.setVisibility(View.GONE);
                viewHolder.AllWorkflowerView.setVisibility(View.INVISIBLE);
                viewHolder.commentListview.setVisibility(View.GONE);
            }


            viewHolder.mAllWorkcomentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTempBean = beans;
                    if (beans.getUserid().equals(Login_Static.myUserID) || beans.getPrice().equals("0.0")) {
                        mActionWork6.setTitle("");
                    } else {
                        mActionWork6.setTitle("购买");
                    }
                    boolean is = false;
                    if (beans.getLikeUsername() != null)
                        for (int j = 0; j < beans.getLikeUsername().size(); j++) {
                            if (beans.getLikeUsername().get(j).getUserId().equals(Login_Static.myUserID)) {
                                is = true;
                                break;
                            }
                        }
                    if (is) {
                        mActionItem1.setItemTv("取消");
                        mActionItem1.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn07));
                    } else {
                        mActionItem1.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn01));
                        mActionItem1.setItemTv("赞    ");
                    }

                    boolean is3 = false;
                    if (beans.getLikeUsername() != null)
                        for (int j = 0; j < beans.getLikeUsername().size(); j++) {
                            if (beans.getLikeUsername().get(j).getUserId().equals(Login_Static.myUserID)) {
                                is3 = true;
                                break;
                            }
                        }
                    if (is3) {
                        mActionWork1.setItemTv("取消");
                        mActionWork1.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn07));
                    } else {
                        mActionWork1.setItemTv("赞    ");
                        mActionWork1.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn01));
                    }
                    //是否收藏过
                    boolean is2 = false;
                    if (beans.getFavUser() != null)
                        for (int j = 0; j < beans.getFavUser().size(); j++) {
                            if (beans.getFavUser().get(j).getUserId().equals(Login_Static.myUserID)) {
                                is2 = true;
                                break;
                            }
                        }
                    if (is2) {
                        mActionWork5.setItemTv("取消");
                        mActionWork5.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn08));
                    } else {
                        mActionWork5.setDrawable(mContext.getResources().getDrawable(R.drawable.momindex_iocn05));
                        mActionWork5.setItemTv("收藏");
                    }

                    if (beans.getType() == 1) {
                        popup(view);
                    } else {
                        popupWork(view);
                    }

                }
            });
            initLister(beans);
        } else {
            if ("mom".equals(saveSharedPreferences.Get_PREFS(mContext, "Type"))) {
                view = LayoutInflater.from(mContext).inflate(R.layout.sort_top_item_3, null);
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.sort_top_item_4, null);
            }

            RelativeLayout searchLayout = (RelativeLayout) view.findViewById(R.id.Work_SearchLayout);
            searchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, Search_2_Activity.class);
                    intent.putExtra("SearchType", "show");
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

    //删除作品
    private void DeleteWork(WorkAll_Beans mMList, final int position) {
        OkHttpUtils.get().url(mApplication.getUrl() + "/Shows/DelShows")
                .addParams("showsId", mMList.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
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
                                mList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                mDialog.show();
                                showError("删除失败", 0);
                                //  mDialog.show();
                                //  showError("取消点赞失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //删除评论
    private void DeleteComment(final List<ShowsCommentUser> mMList, final int position) {
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
                                //  mDialog.show();
                                //  showError("取消点赞失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //送花
    private void sendFlower(final WorkAll_Beans bean) {
        Dialog_Flower mDialog_flower = new Dialog_Flower(mContext, 0, bean, new Dialog_Flower.EditTextContent() {
            @Override
            public void getEditText(String text) {
                ShowsFlowerAdd mShowsFlowerAdd = new ShowsFlowerAdd();
                mShowsFlowerAdd.setUserId(Login_Static.myUserID);
                mShowsFlowerAdd.setAuthorId(bean.getUserid());
                mShowsFlowerAdd.setShowsId(bean.getId());
                mShowsFlowerAdd.setAmount(Integer.parseInt(text));
                String data = mGson.toJson(mShowsFlowerAdd);
                CLog.e("data", data);
                OKHttp.OkHttpPost("/Shows/AddShowsFlower", "", data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing", "错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "changeNickName:" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialog.show();
                                showError("送花成功", 1);
                                UserHead mUser = new UserHead();
                                mUser.setUserId(Login_Static.myUserID);
                                mUser.setNickName(Login_Static.mAccount.getNickName());
                                mUser.setPortrait(Login_Static.mAccount.getPortrait());
                                if (mTempBean.getFlowerUsername() == null) {
                                    List<UserHead> mUserHeads = new ArrayList<UserHead>();
                                    mTempBean.setFlowerUsername(mUserHeads);
                                }
                                mTempBean.getFlowerUsername().add(mUser);
                                notifyDataSetChanged();
                            } else if (Json_Utils.getCode(response) == 1017) {
                                mDialogHint = new Dialog_Hint(mContext, 0,
                                        "鲜花不足，是否前往购买?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialogHint.dismiss();
                                        Intent intent = new Intent(mContext, My_FlowerMarket_Activity.class);
                                        mContext.startActivity(intent);
                                    }
                                });
                                mDialogHint.show();

                            } else {
                                mDialog.show();
                                showError(Json_Utils.getMessage(response), 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        mDialog_flower.show();
    }

    //打赏
    private void sendReward(final WorkAll_Beans bean) {
        CLog.e("sendReward",System.currentTimeMillis()+"秒");
        //  MainActivity.isShow2=false;
        Dialog_Reward mDialog_Reward = new Dialog_Reward(mActivity, mContext, 0, bean, new Dialog_Reward.EditTextContent() {
            @Override
            public void getEditText(String text) {
                ShowsRewardAdd mShowsRewardAdd = new ShowsRewardAdd();
                mShowsRewardAdd.setUserId(Login_Static.myUserID);
                mShowsRewardAdd.setAuthorId(bean.getUserid());
                mShowsRewardAdd.setShowsId(bean.getId());
                mShowsRewardAdd.setAmount(Double.parseDouble(text));
                String data = mGson.toJson(mShowsRewardAdd);
                CLog.e("data", data);
                OKHttp.OkHttpPost("/Shows/AddShowsReward", "", data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "changeNickName:" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialog.show();
                                CLog.e("sendReward",System.currentTimeMillis()+"秒");
                                showError("打赏成功", 1);
                                UserHead mUser = new UserHead();
                                mUser.setUserId(Login_Static.myUserID);
                                mUser.setNickName(Login_Static.mAccount.getNickName());
                                mUser.setPortrait(Login_Static.mAccount.getPortrait());
                                if (mTempBean.getRewardimageUrl() == null) {
                                    List<UserHead> mUserHeads = new ArrayList<UserHead>();
                                    mTempBean.setRewardimageUrl(mUserHeads);
                                }
                                mTempBean.getRewardimageUrl().add(mUser);
                                notifyDataSetChanged();
                            } else if (Json_Utils.getCode(response) == 1016) {
                                mDialogHint = new Dialog_Hint(mContext, 0,
                                        "余额不足，是否前往充值?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialogHint.dismiss();
                                        Intent intent = new Intent(mContext, My_MyWallet_Activity.class);
                                        mContext.startActivity(intent);
                                    }
                                });
                                mDialogHint.show();

                            } else {
                                mDialog.show();
                                showError(Json_Utils.getMessage(response), 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        mDialog_Reward.setCanceledOnTouchOutside(true);
        mDialog_Reward.show();
    }

    //点赞
    private void sendLike(final WorkAll_Beans bean) {
        // Toast.makeText(mContext, ""+bean.getId(), Toast.LENGTH_SHORT).show();
        ShowsLikeAdd mShowsLikeAdd = new ShowsLikeAdd();
        mShowsLikeAdd.setUserId(Login_Static.myUserID);
        mShowsLikeAdd.setAuthorId(bean.getUserid());
        mShowsLikeAdd.setShowsId(bean.getId());
        mShowsLikeAdd.setType(1);
        String data = mGson.toJson(mShowsLikeAdd);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Shows/AddShowsLike", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        mActionItem1.setItemTv("取消");
                        UserHead mUser = new UserHead();
                        mUser.setUserId(Login_Static.myUserID);
                        mUser.setNickName(Login_Static.mAccount.getNickName());
                        if (mTempBean.getLikeUsername() == null) {
                            List<UserHead> mUserHeads = new ArrayList<UserHead>();
                            mTempBean.setLikeUsername(mUserHeads);
                        }
                        mTempBean.getLikeUsername().add(mUser);
                        AddLookNum(bean.getId());
                        for (int i = 1; i < mList.size(); i++) {
                            if (mList.get(i).getId().equals(bean.getId())) {
                                mList.get(i).setViews(mList.get(i).getViews() + 1);
                                notifyDataSetChanged();
                                break;
                            }
                        }
                        notifyDataSetChanged();

                        //  mTextView.setText("取消");
                    } else {
                        //mDialog.show();
                        // showError("点赞失败", 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //取消点赞
    private void cancleLike(WorkAll_Beans bean) {
        OkHttpUtils.get().url(mApplication.getUrl() + "/Shows/DelShowsLike")
                .addParams("showsId", bean.getId())
                .addParams("userId", Login_Static.myUserID)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mActionItem1.setItemTv("赞    ");
                                // mDialog.show();
                                for (int j = 0; j < mTempBean.getLikeUsername().size(); j++) {
                                    if (mTempBean.getLikeUsername().get(j).getUserId().equals(Login_Static.myUserID)) {
                                        mTempBean.getLikeUsername().remove(j);
                                    }
                                }
                                notifyDataSetChanged();
                                //  showError("取消点赞成功", 1);
                            } else {
                                //  mDialog.show();
                                //  showError("取消点赞失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //收藏
    private void sendCollect(final WorkAll_Beans bean) {
        ShowsFavouriteAdd mShowsFavouriteAdd = new ShowsFavouriteAdd();
        mShowsFavouriteAdd.setUserId(Login_Static.myUserID);
        mShowsFavouriteAdd.setShowsId(bean.getId());
        mShowsFavouriteAdd.setAuthorId(bean.getUserid());
        mShowsFavouriteAdd.setState(1);
        String data = mGson.toJson(mShowsFavouriteAdd);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Shows/AddShowsFavourite", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {

                        mDialog.show();
                        showError("收藏成功", 1);
                        UserHead mUser = new UserHead();
                        mUser.setUserId(Login_Static.myUserID);
                        mUser.setNickName(Login_Static.mAccount.getNickName());
                        if (mTempBean.getFavUser() == null) {
                            List<UserHead> mUserHeads = new ArrayList<UserHead>();
                            mTempBean.setFavUser(mUserHeads);
                        }
                        mTempBean.getFavUser().add(mUser);
                    } else {
                        mDialog.show();
                        showError("收藏失败", 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //取消 收藏
    private void cancleCollect(final WorkAll_Beans bean) {
        OkHttpUtils.get().url(mApplication.getUrl() + "/Shows/DelShowsFavourite")
                .addParams("showsId", bean.getId())
                .addParams("userId", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialog.show();
                                for (int j = 0; j < mTempBean.getFavUser().size(); j++) {
                                    if (mTempBean.getFavUser().get(j).getUserId().equals(Login_Static.myUserID)) {
                                        mTempBean.getFavUser().remove(j);
                                    }
                                }
                                showError("取消收藏成功", 1);
                            } else {
                                mDialog.show();
                                showError("取消收藏失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //监听点击事件
    private void initLister(final WorkAll_Beans bean) {
        //点击头像
        viewHolder.mAllWorkuserpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                saveSharedPreferences.Save_PREFS(mContext, "otherId", bean.getUserid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.LookMoreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, LookMoreComment_Activity.class);
                mIntent.putExtra("bean", bean);
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.mAllWorkflowerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("bean", bean);
                mIntent.putExtra("type", "送花");
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.mAllWorklikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("bean", bean);
                mIntent.putExtra("type", "赞");
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.mAllWorkrewardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("bean", bean);
                mIntent.putExtra("type", "打赏");
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.commentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (bean.getCommentBeansList().get(position).getUser().getUserId().equals(Login_Static.myUserID)) {
                    mDialogHint = new Dialog_Hint(mContext, 0,
                            "确认删除吗?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeleteComment(bean.getCommentBeansList(), position);
                            mDialogHint.dismiss();
                        }
                    });
                    mDialogHint.show();
                } else {
                    if (!saveSharedPreferences.Get_PREFS(mContext, "ismy").equals("yes"))
                        mInterface.execute(bean.getId(), bean.getCommentBeansList().get(position).getUser().getUserId(), bean.getUserid(), bean.getCommentBeansList().get(position).getId());
                    else {
                        mMyPageInterface.execute(bean.getId(), bean.getCommentBeansList().get(position).getUser().getUserId(), bean.getUserid(), bean.getCommentBeansList().get(position).getId());
                    }
                }

            }
        });
        viewHolder.reward_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("bean", bean);
                mIntent.putExtra("type", "打赏");
                mContext.startActivity(mIntent);
            }
        });
        viewHolder.flower_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(mContext, FlowerAndReword_Activity.class);
                mIntent.putExtra("bean", bean);
                mIntent.putExtra("type", "送花");
                mContext.startActivity(mIntent);
            }
        });
    /*    viewHolder.mLikeTextLayout= (RelativeLayout) view.findViewById(R.id.likeTextLayout);
        viewHolder.mFlowerLayout= (RelativeLayout) view.findViewById(R.id.flowerLayout);
        viewHolder.mRewardLayout= (RelativeLayout) view.findViewById(R.id.rewardLayout);
        viewHolder.mCommentImageLayout= (RelativeLayout) view.findViewById(R.id.commentImageLayout);
        viewHolder.mCollectLayout= (RelativeLayout) view.findViewById(R.id.collectLayout);
        viewHolder.mPurchaseLayout= (RelativeLayout) view.findViewById(R.id.purchaseLayout);
        viewHolder.mShareLayout= (RelativeLayout) view.findViewById(R.id.ShareLayout);*/
    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        switch (position) {
            case 0://赞
                TextView teview = null;
                CLog.e("mActionItem1", mActionItem1.getTitle().toString());
                if (mActionItem1.getTitle().toString().equals("赞    "))
                    sendLike(mTempBean);
                else if (mActionItem1.getTitle().toString().equals("取消"))
                    cancleLike(mTempBean);
                break;
            case 1://评论
                MainActivity.isShow2 = true;
                if (!saveSharedPreferences.Get_PREFS(mContext, "ismy").equals("yes"))
                    mInterface.execute(mTempBean.getId(), mTempBean.getUserid(), mTempBean.getUserid(), "0");
                else {
                    mMyPageInterface.execute(mTempBean.getId(), mTempBean.getUserid(), mTempBean.getUserid(), "0");
                }
                break;
            case 2://分享
                showShare(mTempBean);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemWorkClick(ActionItem item, int position) {
        switch (position) {
            case 0://赞
                if (!saveSharedPreferences.Get_PREFS(mContext, "Unlogin").equals("yes")) {
                    if (mActionWork1.getTitle().toString().equals("赞    "))
                        sendLike(mTempBean);
                    else if (mActionWork1.getTitle().toString().equals("取消"))
                        cancleLike(mTempBean);
                } else {
                    Intent mIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(mIntent);
                }

                break;
            case 1://送花
                if (!saveSharedPreferences.Get_PREFS(mContext, "Unlogin").equals("yes")) {
                    if (!mTempBean.getUserid().equals(Login_Static.myUserID))
                        sendFlower(mTempBean);
                    else {
                        mDialog.show();
                        showError("不能送花给自己", 0);
                    }
                } else {
                    Intent mIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(mIntent);
                }

                break;
            case 2://打赏
                if (!saveSharedPreferences.Get_PREFS(mContext, "Unlogin").equals("yes")) {
                    if (!mTempBean.getUserid().equals(Login_Static.myUserID))
                        sendReward(mTempBean);
                    else {
                        mDialog.show();
                        showError("不能打赏自己作品", 0);
                    }
                } else {
                    Intent mIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(mIntent);
                }

                break;
            case 3://评论
                if (!saveSharedPreferences.Get_PREFS(mContext, "Unlogin").equals("yes")) {
                    MainActivity.isShow2 = true;
                    if (!saveSharedPreferences.Get_PREFS(mContext, "ismy").equals("yes")) {
                        mInterface.execute(mTempBean.getId(), mTempBean.getUserid(), mTempBean.getUserid(), "0");
                        CLog.e("execute", "非个人主页回调");
                    } else {
                        mMyPageInterface.execute(mTempBean.getId(), mTempBean.getUserid(), mTempBean.getUserid(), "0");
                        CLog.e("execute", "个人主页回调");
                    }
                } else {
                    Intent mIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(mIntent);
                }

                break;
            case 4://收藏
                if (!saveSharedPreferences.Get_PREFS(mContext, "Unlogin").equals("yes")) {
                    if (mActionWork5.getTitle().toString().equals("收藏")) {
                        sendCollect(mTempBean);
                    } else if (mActionWork5.getTitle().toString().equals("取消")) {
                        cancleCollect(mTempBean);
                    }
                } else {
                    Intent mIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(mIntent);
                }

                break;
            case 5://购买
                Intent mIntent = new Intent(mContext, Idle_ProduceDetails_Activity.class);
                mIntent.putExtra("IdleID", mTempBean.getId());
                mIntent.putExtra("titlename", 1);//1代表是作品详情
                mContext.startActivity(mIntent);
                break;
            default:
                break;
        }
    }


    class ViewHolder {
        RelativeLayout itemLayout;//item布局
        SimpleDraweeView mAllWorkuserpic;//用户头像
        TextView mAllWorkusername;//用户昵称
        TextView mAllWorktitle;//作品标题
        TextView mAllWorkcontent;//作品内容
        NineGridView nineGrid;//九宫格图片控件
        ImageView mAllWorkcomentImage;//评论图标
        ImageView mViewNumTextIamge;//浏览量图标
        TextView mAllWorkrewardnum;//打赏人数显示
        TextView mAllWorklikename;//赞人姓名
        TextView mAllWorkflowerName;//送花人姓名
        TextView mWork_money;//作品价格
        TextView money_time;//作品时间
        TextView mViewNumText;//浏览数量
        MyListView commentListview;//评论listview
        RelativeLayout mAllWorkrewardLayout;//打赏
        RelativeLayout mAllWorkrewardUserImage;//打赏
        RelativeLayout mAllWorklikeLayout;//赞
        RelativeLayout mAllWorkflowerLayout;//送花
        RelativeLayout userSelfSee;//仅自己可见布局
        HorizontalListView reward_userList;//打赏人头像list
        HorizontalListView flower_userList;//打赏人头像list
        TextView CommentNumVisiable;//更多评论显示条数
        View commenimageLayoutLine;//评论图标下方分割线
        View LikeTopline;//赞布局上方分割线
        View flowerTopLine;//送花布局上方分割线
        View AllWorkflowerView;//评论送花分割线
        RelativeLayout LookMoreComment;//更多评论布局
        TextView DeleteText;//删除作品按钮
        TextView UserIden;//用户类型
    }

    //dp 转变为px
    public int Dp2Px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px转变为dp
    public int Px2Dp(float px) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    private void showShare(final WorkAll_Beans mTempBean) {
        ShareSDK.initSDK(mContext);
        final OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mTempBean.getThemetitle() + mTempBean.getContent());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.leadershows.com/home/download");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mTempBean.getThemetitle() + mTempBean.getContent());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.leadershows.com/home/download");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(" ");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("领秀S");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.leadershows.com/home/download");
        if (mTempBean.getImageStrinig() != null && mTempBean.getImageStrinig().size() > 0) {
            oks.setImageUrl(SubString_Utils.getImageUrl4(mTempBean.getImageStrinig().get(0)));
        } else {
            oks.setImageUrl("http://www.leadershows.com/icon1.png");
        }

// 启动分享GUI
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            //自定义分享的回调想要函数
            @Override
            public void onShare(Platform platform,
                                cn.sharesdk.framework.Platform.ShareParams paramsToShare) {

                //点击新浪微博
                if ("SinaWeibo".equals(platform.getName())) {
                    //限制微博分享的文字不能超过20
                    paramsToShare.setTitle(mTempBean.getThemetitle());
                    paramsToShare.setText(mTempBean.getContent() + " " + "http://www.leadershows.com/home/download");
                    paramsToShare.setShareType(Platform.SHARE_APPS);
                }
                //点击除了QQ以外的平台
                if (!"QQ".equals(platform.getName())) {
                    Log.i("", "点击了QQ以外的平台");
                }
            }
        });
        oks.show(mContext);
    }

    //发布结果提示
    public void showError(String errorString, final int is) {
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
                MainActivity.isShow2 = true;
            }
        }, 1000);
    }

    /**
     * 弹出评论,赞菜单加载
     */
    private void popup(View v) {
        titlePopup = new LayoutCriclePopup(mContext, Utils.dip2px(mContext, 220), Utils.dip2px(mContext, 30));
        titlePopup.addAction(mActionItem1);
        titlePopup.addAction(mActionItem2);
        titlePopup.addAction(mActionItem3);
        titlePopup.setItemOnClickListener(this);
        titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
        titlePopup.show(v);
    }

    /**
     * 弹出评论,赞菜单加载
     */
    private void popupWork(View v) {
        workPopup = new LayoutWorkPopup(mContext, Utils.dip2px(mContext, 220), Utils.dip2px(mContext, 60));
        workPopup.addAction(mActionWork1);
        workPopup.addAction(mActionWork2);
        workPopup.addAction(mActionWork3);
        workPopup.addAction(mActionWork4);
        workPopup.addAction(mActionWork5);
        workPopup.addAction(mActionWork6);
        workPopup.setItemOnClickListener(this);
        workPopup.setAnimationStyle(R.style.cricleBottomAnimation);
        workPopup.show(v);
    }

    //增加浏览量
    private void AddLookNum(final String workid) {
        OkHttpUtils.get().url(mApplication.getUrl() + "/Shows/UpdateViews")
                .addParams("showsId", workid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                // SaveSharedPreferences mSaveSharedPreferences = new SaveSharedPreferences();
                                //  mSaveSharedPreferences.Save_PREFS(mContext, "WorkIdNum", workid);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private class TextViewURLSpan extends ClickableSpan {
        private String clickString;

        public TextViewURLSpan(String clickString) {
            this.clickString = clickString;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mContext.getResources().getColor(R.color.bg_black));
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            mName = clickString;
        }
    }

}
