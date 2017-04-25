package com.multshows.Activity;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.ImageInfo;
import com.multshows.Beans.Photo_ImageItem_Beans;
import com.multshows.Beans.ShowsAdd;
import com.multshows.Beans.Task_distributionList_Beans;
import com.multshows.Fragment.VideoFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.NetUtil;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.SavePicture_toLocal_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyGridView;
import com.multshows.Views.MyPicpupWindow;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：发布作品页
 * 作者：李萧萧
 * 时间：2016.10/3
 */
public class Release_Work_Activity extends AppCompatActivity {

    MyPicpupWindow mPopWindow;//选择照片方式弹出框
    @Bind(R.id.releaseWorkActivity)
    TextView mReleaseWorkActivity;//发布
    @Bind(R.id.ReLeaseWorkTitle)
    TextView ReLeaseWorkTitle;//发布标题
    @Bind(R.id.WrokClassification)
    TextView mWrokClassification;//作品类别
    @Bind(R.id.ReleaseWork_return)
    ImageView mReleaseWorkReturn;//返回
    @Bind(R.id.Release_workTitle)
    EditText mReleaseWorkTitle;//标题
    @Bind(R.id.Release_workContent)
    EditText mReleaseWorkContent;//内容
    @Bind(R.id.imageWorkGridview)
    MyGridView mImageWorkGridview;
    @Bind(R.id.chioceClassification)
    RelativeLayout mChioceClassification;
    @Bind(R.id.WorkisSell)
    ImageView mWorkisSell; //作品是否可售
    @Bind(R.id.BabySee)
    ImageView mBabySee;//宝宝可见性
    @Bind(R.id.workprice)
    TextView mWorkprice;
    @Bind(R.id.workPriceEditText)
    EditText mWorkPrice;//作品价格
    @Bind(R.id.workFreightPrice)
    EditText mWorkFreightPrice;//作品运费
    @Bind(R.id.PriceLayout)
    LinearLayout mPriceLayout;//作品运费
    @Bind(R.id.BabySeeLayout)
    RelativeLayout BabySeeLayout;//宝宝可见布局
    @Bind(R.id.my_toplayout)
    RelativeLayout my_toplayout;//头部布局，设置颜色样式
    @Bind(R.id.lookmyActivity)
    RelativeLayout lookmyActivity;//标题所在布局

    @Bind(R.id.WorkConntentlayout)
    RelativeLayout WorkConntentlayout;//活动内容所在布局

    @Bind(R.id.release_work_Layout)
    LinearLayout release_work_Layout;//设置本页背景
    List<String> imageListString = new ArrayList<>();
    private File file = null;
    private GridAdapter adapter;
    int flag_upload = 0;//记录上传图片个数，用于判断是否上传所有
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    int isSell = 0;//标记是否出售
    Gson mGson = new Gson();
    int babySee = 1;//1 宝宝可见，0 宝宝不可见
    Uri photoUri;
    String mShowsClassId = "12";//作品分类id
    String mShowsClassName = "";//作品分类id
    Dialog mDialog;//消息提示框
    String Imageurl = "";//上传图片地址
    String token;//七牛云token
    int IsVideo = 2;//是否是视频  1 视频 2图片
    String VideoName;//上传七牛云的视频名称
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    List<Task_distributionList_Beans> mList = new ArrayList<>();//宝宝列表bean
    private static final String FILE_PATH2 = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/multshows/";
    private MyApplication mApplication = new MyApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_activity);
        ButterKnife.bind(this);
        //判断SD卡是否存在或可用
        if (getSDPath() != null) {
            file = videoRename();
        }
        initData();
        initLister();
    }

    private void initLister() {
        //限制价格只能两位小数
        mWorkPrice.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        //限制运费只能两位小数
        mWorkFreightPrice.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        mImageWorkGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Photo_Bimp.albumSelectBitmap.size()) {
                    //底部弹出框
                    //实例化MyPicPopupWindow
                    HideInputManager_Utils.hideInputManager(Release_Work_Activity.this);//隐藏 键盘
                    mPopWindow = new MyPicpupWindow(Release_Work_Activity.this, itemsOnClick,
                            "拍照", "拍摄视频", "手机相册", "");
                    //设置弹出动画效果
                    // mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                    //显示窗口  //设置layout在PopupWindow中显示的位置
                    mPopWindow.showAtLocation(findViewById(R.id.release_work_Layout),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    if (Photo_Bimp.albumSelectBitmap.get(arg2).isVideo()) {
                        VideoFragment bigPic = VideoFragment.newInstance(Photo_Bimp.albumSelectBitmap.get(arg2).getImagePath());
                        android.app.FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction transaction = mFragmentManager.beginTransaction();
                        transaction.replace(R.id.release_work_Layout, bigPic);
                        transaction.commit();
                    } else {
                        Intent intent = new Intent(Release_Work_Activity.this,
                                Photo_Preview_Activity.class);
                        intent.putExtra("photoPosition", 1);
                        intent.putExtra("ID", arg2);
                        startActivity(intent);
                    }

                }
            }
        });
      /*  //添加图片点击长按事件
        mImageWorkGridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.item_grida_image_delete);
                imageView.setVisibility(View.VISIBLE);
                adapter = new GridAdapter(Release_Work_Activity.this, "yes");
                mImageWorkGridview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });*/

    }


    //初始化数据
    private void initData() {
        //设置本页背景颜色等样式
        if (mSave.Get_PREFS(Release_Work_Activity.this, "Type").equals("mom")) {
            release_work_Layout.setBackgroundColor(getResources().getColor(R.color.bg));
            lookmyActivity.setBackgroundColor(getResources().getColor(R.color.white));
            WorkConntentlayout.setBackgroundColor(getResources().getColor(R.color.white));
            mChioceClassification.setBackgroundColor(getResources().getColor(R.color.white));
            mPriceLayout.setBackgroundColor(getResources().getColor(R.color.white));
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(Release_Work_Activity.this, R.color.green);
            BabySeeLayout.setVisibility(View.VISIBLE);
        } else {
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            tintManageUtils.setSystemBarTintManage_noColor(Release_Work_Activity.this);
            my_toplayout.setBackgroundResource(R.drawable.classify_topbg);
            mReleaseWorkReturn.setImageResource(R.drawable.return_arrow);
            ReLeaseWorkTitle.setTextColor(getResources().getColor(R.color.red_grey));
            mReleaseWorkActivity.setTextColor(getResources().getColor(R.color.red_grey));
            BabySeeLayout.setVisibility(View.GONE);
        }
        mDialog = new HintText_Dialog(Release_Work_Activity.this, R.style.MyDialog);
        mImageWorkGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this, "no");
        adapter.update();
        mImageWorkGridview.setAdapter(adapter);
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chioce_camera:
                    if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                        if (Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                            mDialog.show();
                            showError("不能选择图片", 0);
                        } else photo();//调用系统相机拍照
                    } else
                        photo();
                    break;
                case R.id.chioce_video:
                    //跳到录像
                    Intent intent = new Intent(Release_Work_Activity.this, VideoRecording_Activity.class);
                    if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                        mDialog.show();
                        showError("不能再次选择视频", 0);
                    } else {
                        startActivityForResult(intent, 200);
                    }
                   /* Intent intent = new Intent();
                    intent.setAction("android.media.action.VIDEO_CAPTURE");
                    intent.addCategory("android.intent.category.DEFAULT");
                    if (file.exists()) {
                        file.delete();
                    }
                    Uri uri2 = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//		                 intent.putExtra(MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED);
                    startActivityForResult(intent, 0);*/
                    break;
                case R.id.chioce_photo:
                    //选择相册
                    if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                        if (Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                            mDialog.show();
                            showError("不能选择图片", 0);
                        } else {
                            intent = new Intent(Release_Work_Activity.this, Photo_Album_Activity.class);
                            intent.putExtra("userId", Login_Static.myUserID);
                            startActivity(intent);
                        }
                    } else {
                        intent = new Intent(Release_Work_Activity.this, Photo_Album_Activity.class);
                        intent.putExtra("userId", Login_Static.myUserID);
                        startActivity(intent);
                    }

                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    /**
     * 判断SD卡是否可用
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else {
            Toast.makeText(Release_Work_Activity.this, "msg" + "SD卡不可用" + Environment.getExternalStorageState(), Toast.LENGTH_SHORT).show();
        }
        return sdDir.toString();

    }

    /*
 * 生成video文件名字
 */
    protected File videoRename() {
        //用系统当前时间命名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()) + ".3gp";
        //创建文件夹
        File out = new File(FILE_PATH2);
        if (!out.exists()) {
            out.mkdirs();
        }

        return new File(FILE_PATH2 + fileName);
    }
    //监听事件
    @OnClick({R.id.releaseWorkActivity, R.id.ReleaseWork_return, R.id.WorkisSell, R.id.BabySee, R.id.chioceClassification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.releaseWorkActivity:
                if (VerificationParameters()) {
                    if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                        if (Photo_Bimp.albumSelectBitmap.size() > 0 && Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                            mDialog.show();
                            HintText_Dialog hint = new HintText_Dialog(Release_Work_Activity.this, this.getString(R.string.release), "");
                            String name = Login_Static.myUserID + System.currentTimeMillis() + ".mp4";
                            getQiNiuToken(Photo_Bimp.albumSelectBitmap.get(0).imagePath, name);//获取taken,及上传视频
                        } else {
                            mDialog.show();
                            HintText_Dialog hint = new HintText_Dialog(Release_Work_Activity.this, this.getString(R.string.release), "");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    GetQiniuImageToken();//获取七牛云上传图片所需的token 并进行图片上传;//上传图片
                                }
                            }).start();
                        }
                    } else {
                        mDialog.show();
                        showError(this.getString(R.string.pictureNum), 0);
                    }

                }
                break;
            case R.id.ReleaseWork_return:
                HideInputManager_Utils.hideInputManager(Release_Work_Activity.this);//隐藏 键盘
                Photo_Bimp.albumSelectBitmap.clear();
                Photo_Bimp.taskSelectBitmap.clear();
                Photo_Bimp.tempSelectBitmap.clear();
                finish();
                break;
            case R.id.BabySee:
                if (babySee == 1) {
                    mBabySee.setImageResource(R.drawable.switch_off);
                    babySee = 0;
                } else if (babySee == 0) {
                    babySee = 1;
                    mBabySee.setImageResource(R.drawable.switch_on);
                }
                break;
            case R.id.WorkisSell:
                if(Photo_Bimp.albumSelectBitmap.size()>0){
                    if(Photo_Bimp.albumSelectBitmap.get(0).isVideo()){
                        if (isSell == 1) {
                            mWorkisSell.setImageResource(R.drawable.switch_off);
                            isSell = 0;
                            mPriceLayout.setVisibility(View.GONE);
                        }else if (isSell == 0) {
                            isSell = 1;
                            mPriceLayout.setVisibility(View.VISIBLE);
                            mWorkisSell.setImageResource(R.drawable.switch_on);
                        }
                    }
                }else {
                    if (isSell == 1) {
                        mWorkisSell.setImageResource(R.drawable.switch_off);
                        isSell = 0;
                        mPriceLayout.setVisibility(View.GONE);
                    }else if (isSell == 0) {
                        isSell = 1;
                        mPriceLayout.setVisibility(View.VISIBLE);
                        mWorkisSell.setImageResource(R.drawable.switch_on);
                    }
                }


                break;
            case R.id.chioceClassification:
                Intent mIntent = new Intent(Release_Work_Activity.this, WorkOnlyClassificationActivity.class);
                mIntent.putExtra("Classification", mWrokClassification.getText().toString());
                startActivityForResult(mIntent, 5);
                //showDialog();
                break;
        }
    }

    /**
     * 验证参数函数
     */
    private Boolean VerificationParameters() {
        Boolean falg = true;
        Boolean falg2 = true;
        /*if (mReleaseWorkTitle != null && mReleaseWorkTitle.getText().toString().equals("")) {
            falg = false;
        }*/
        if (mReleaseWorkContent != null && mReleaseWorkContent.getText().toString().equals("")) {
            falg = false;
        } else if (mReleaseWorkContent.getText().toString().length() > 140) {
            mDialog.show();
            showError("最多为140个字", 0);
            falg2 = false;
            falg = false;
        }
        if (mWorkPrice.getText().toString().equals("") && isSell == 1) {
            falg = false;
        }
        if (mWrokClassification != null && mWrokClassification.getText().toString().equals("")) {
            mDialog.show();
            showError("请选择分类", 0);
            falg2 = false;
            falg = false;
        }
        if (isSell == 1 && falg && Double.parseDouble(mWorkPrice.getText().toString()) <= 0.0) {
            mDialog.show();
            showError("售价必须大于0", 0);
            falg2 = false;
            falg = false;
        }

        //类似颜文字的字符串，禁止发表
        String[] res = mReleaseWorkTitle.getText().toString().split("\n");
        String[] res1 = mReleaseWorkContent.getText().toString().split("\n");
        String[] res2 = mReleaseWorkTitle.getText().toString().split(" ");
        String[] res3 = mReleaseWorkContent.getText().toString().split(" ");
        CLog.e("res", res.length + "");
        CLog.e("res1", res1.length + "");
        CLog.e("res2", res2.length + "");
        CLog.e("res3", res3.length + "");
        if ((res.length > 3 && res2.length >= 5) || (res1.length > 4 && res3.length > 5)) {
            mDialog.show();
            showError(this.getString(R.string.noPass), 0);
            falg2 = false;
            falg = false;
            mReleaseWorkTitle.setText("");
            mReleaseWorkContent.setText("");
        }
        if (!falg && falg2) {
            mDialog.show();
            showError(this.getString(R.string.NoRelease), 0);
        }


        return falg;
    }


    //选择图片显示gridview显示图片
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        String deleteList;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context, String sDelete) {
            inflater = LayoutInflater.from(context);
            deleteList = sDelete;
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Photo_Bimp.albumSelectBitmap.size() == 9) {
                return 9;
            }
            return (Photo_Bimp.albumSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_releasegridview,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                holder.mDelete = (ImageView) convertView.findViewById(R.id.item_grida_image_delete);
                holder.paly = (ImageView) convertView.findViewById(R.id.paly);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == Photo_Bimp.albumSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.upimg_logo));
                holder.mDelete.setVisibility(View.INVISIBLE);
                holder.paly.setVisibility(View.INVISIBLE);
                if (position == 9) {

                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Photo_Bimp.albumSelectBitmap.get(position).getBitmap());
                holder.mDelete.setVisibility(View.VISIBLE);
                if (Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                    holder.paly.setVisibility(View.VISIBLE);
                }else {
                    if (isSell == 0) {
                        isSell = 1;
                        mWorkisSell.setImageResource(R.drawable.switch_on);
                        mPriceLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
            //点击删除事件
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Photo_Bimp.tempSelectBitmap.remove(position);
                    Photo_Bimp.albumSelectBitmap.remove(position);
                    v.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    if(Photo_Bimp.albumSelectBitmap.size()==0){
                        if (isSell == 1) {
                            mWorkisSell.setImageResource(R.drawable.switch_off);
                            isSell = 0;
                            mPriceLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public ImageView mDelete;
            public ImageView paly;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Photo_Bimp.max == Photo_Bimp.albumSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Photo_Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }
    //发布作品
    public void setReleaseWork() {
        ShowsAdd mShowsAdd = new ShowsAdd();
        mShowsAdd.setUserId(Login_Static.myUserID);
        mShowsAdd.setShowsType(1);
        mShowsAdd.setShowsClassId(mShowsClassId);
        mShowsAdd.setAmount(1);
        mShowsAdd.setSoType(1);
        mShowsAdd.setIsBabySee(babySee);
        CLog.e("res", mReleaseWorkTitle.getText().toString() + "");
  /*      String newString="";
        Pattern p = Pattern.compile("\\\\||/ ||la");
        Matcher m = p.matcher(mReleaseWorkTitle.getText().toString());
        newString = m.replaceAll("");*/
        // CLog.e("res",newString+"");
        mShowsAdd.setTitle(mEmoji_change.EmojiChange(mReleaseWorkTitle.getText().toString()));
        mShowsAdd.setInfos(mEmoji_change.EmojiChange(mReleaseWorkContent.getText().toString()));
        if (isSell == 1) {
            mShowsAdd.setPrice(Double.parseDouble(mWorkPrice.getText().toString()));
            if (!mWorkFreightPrice.getText().toString().equals(""))
                mShowsAdd.setExpressFee(Double.parseDouble(mWorkFreightPrice.getText().toString()));
        }
        mShowsAdd.setIsSale(isSell);
        mShowsAdd.setIsShow(1);
        mShowsAdd.setIsVideo(IsVideo);
        if (IsVideo == 1) {
            mShowsAdd.setVideoName(VideoName);
        }
        IsVideo = 2;
        mShowsAdd.setIsOver(0);
        if (Photo_Bimp.albumSelectBitmap.size() != 0)
            mShowsAdd.setShowsPhoto(Imageurl);
        String data = mGson.toJson(mShowsAdd);
        CLog.e("data", mReleaseWorkTitle.getText().toString().length() + "");
        CLog.e("data", mReleaseWorkContent.getText().toString().length() + "");
        OKHttp.OkHttpPost("?type=8", "data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                showError("发布作品失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "changeNickName:" + response);
                flag_upload = 0;
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        showError("发布作品成功", 1);
                    } else {
                        showError(Json_Utils.getMessage(response), 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Photo_Bimp.albumSelectBitmap.clear();
            Photo_Bimp.taskSelectBitmap.clear();
            Photo_Bimp.tempSelectBitmap.clear();
            finish();
        }

        return false;
    }

    private static final int TAKE_PICTURE = 0x000001;

    /**
     * 返回的数据处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                if (data != null) {
                    mShowsClassId = data.getExtras().getString("ShowsClassId");
                    mShowsClassName = data.getExtras().getString("ShowsClassName");
                    mWrokClassification.setText(mShowsClassName);
                }
                break;
            case TAKE_PICTURE:
                if (Photo_Bimp.albumSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                /*String fileName = String.valueOf(System.currentTimeMillis());
                Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);*/
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
                    Photo_ImageItem_Beans takePhoto = new Photo_ImageItem_Beans();
                    takePhoto.setImagePath(path);
                    Photo_Bimp.tempSelectBitmap.add(takePhoto);
                    Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                }
                break;
        }
    }

    public void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);

        photoUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PICTURE);
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
        HintText_Dialog hint = new HintText_Dialog(Release_Work_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                if (is == 1) {
                    mSave.Save_PREFS(Release_Work_Activity.this, "intent", "work");//标记发布成功，主页显示的fragment
                    Photo_Bimp.albumSelectBitmap.clear();
                    Photo_Bimp.taskSelectBitmap.clear();
                    Photo_Bimp.tempSelectBitmap.clear();
                    finish();
                }
                mDialog.dismiss();
            }
        }, 1000);
    }

    public void getQiNiuToken(final String path, final String url) {
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=11")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(response);
                                token = jsonArray.getString("Template");
                                //上传视频至七牛云
                                UploadManager uploadManager = new UploadManager();
                                uploadManager.put(path, url, token,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info,
                                                                 JSONObject response) {
                                                //Log.e("ppp", "000"+imageName+"");
                                                if (info.statusCode == 200) {
                                                    CLog.e("path", "" + info.isOK());
                                                    CLog.e("path", "成功");
                                                    CLog.e("path", url);
                                                    VideoName = url;
                                                    IsVideo = 1;
                                                    GetQiniuImageToken();
                                                } else {

                                                    CLog.e("path", "上传失败");
                                                }
                                            }
                                        }, null);
                                CLog.e("token", "" + token);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void GetQiniuImageToken() {
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=11")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(response);
                                token = jsonArray.getString("Template");
                                //上传视频至七牛云
                                for (int i = 0; i < Photo_Bimp.albumSelectBitmap.size(); i++) {
                                    String name = Login_Static.myUserID + System.currentTimeMillis() + ".jpg";
                                    File file = SavePicture_toLocal_Utils.saveCacheFile(Photo_Bimp.albumSelectBitmap.get(i).getBitmap(),
                                            Login_Static.myUserID + System.currentTimeMillis() + ".png",
                                            Release_Work_Activity.this);
                                    UploadQiniuImage(file,name);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void UploadQiniuImage(File file, final String name) {

        UploadManager uploadManager = new UploadManager();
        uploadManager.put(file, name, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info,
                                         JSONObject response) {
                        //Log.e("ppp", "000"+imageName+"");
                        if (info.statusCode == 200) {
                            CLog.e("path", "" + info.isOK());
                            CLog.e("path", "成功");
                            flag_upload++;
                            CLog.e("testing", "上传图片成功：" + response);
                            if (flag_upload != Photo_Bimp.albumSelectBitmap.size()) {
                                Imageurl = Imageurl + name + ",";
                                imageListString.add(Imageurl);
                            } else {
                                Imageurl = Imageurl +name;
                                CLog.e("testing", "flag_upload==" + flag_upload);
                                imageListString.add(Imageurl);
                                setReleaseWork();
                            }
                        } else {
                            Toast.makeText(Release_Work_Activity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            CLog.e("path", "上传失败" + response);
                        }
                    }
                }, null);
        CLog.e("token", "" + token);
    }
}