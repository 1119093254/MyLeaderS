package com.multshows.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.MyPicPopupWindow;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 我的宝宝头像
 */
public class My_BabyHeader_Activity extends AppCompatActivity {
    public static final int CREAM_CODE = 6;//照相机返回结果
    MyPicPopupWindow mPopWindow;
    //控件
    SimpleDraweeView mHeader;//头像
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ImageView mMore;//更多

    Uri photoUri;
    Gson mGson=new Gson();
    Intent intent;
    String babyUrl="";//宝贝头像
    String returnUrl="";//返回路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_baby_header);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_BabyHeader_Activity.this, R.color.app_topColor);
        Login_Static.myBabyPic="";
        initView();
        initData();
        initListen();


    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_BabyInformationH_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_BabyInformationH_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_BabyInformationH_parentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_BabyInformationH_TopTitle);
        mMore= (ImageView) findViewById(R.id.My_BabyInformationH_more);
        mHeader= (SimpleDraweeView) findViewById(R.id.My_BabyInformationH_header);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_BabyHeader_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        intent=getIntent();
        babyUrl=intent.getStringExtra("babyHeader");
        if(babyUrl!=null && !"null".equals(babyUrl)) {
            mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(babyUrl)));
        }else {
            mHeader.setImageURI(Uri.parse(""));
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("babyImage",babyUrl);
                setResult(1,intent);
                finish();
            }
        });
        //更多操作
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow=new MyPicPopupWindow(My_BabyHeader_Activity.this,itemsOnClick,
                        "拍照","手机相册","","");
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(findViewById(R.id.My_BabyInformationH_parentsLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lookPhoto_dialog_Top:
                    //打开照相机
                   /* Intent intent_cream = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent_cream, CREAM_CODE);*/
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss");
                    String filename = timeStampFormat.format(new Date());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, filename);

                    photoUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(intent, CREAM_CODE);
                    break;
                case R.id.lookPhoto_dialog_Center:
                    intent=new Intent(My_BabyHeader_Activity.this,Photo_Album_Activity.class);
                    intent.putExtra("album_type","replace_photo");
                    //intent.putExtra("userId", "0");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(Login_Static.myBabyPic!=null && !"".equals(Login_Static.myBabyPic)) {
            babyUrl= Login_Static.myBabyPic;
            mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(Login_Static.myBabyPic)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CREAM_CODE:
                if(resultCode==RESULT_OK){
                    //这里开始的第二部分，获取图片的存储路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(photoUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    if(resultCode==-1) {
                        Intent intent_look = new Intent(My_BabyHeader_Activity.this, ReplacPhoto_Look_Activity.class);
                        intent_look.putExtra("ReplacType",0);
                        intent_look.putExtra("ReplacPhoto_Look", path);
                        startActivityForResult(intent_look, 666);
                    }
                }
                break;
            case 666:
                if(resultCode==2){
                    babyUrl=data.getStringExtra("returnUrl");
                    mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(babyUrl)));
                }
                break;
        }
    }


}
