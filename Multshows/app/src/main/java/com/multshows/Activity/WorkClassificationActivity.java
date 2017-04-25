package com.multshows.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.ShowsClass;
import com.multshows.Beans.ShowsClassTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.MyGridView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

//作品分类（多选）
public class WorkClassificationActivity extends AppCompatActivity {

    @Bind(R.id.Classification_return)
    ImageView mClassificationReturn;//返回
    @Bind(R.id.WorkClassfication)
    RelativeLayout mWorkClassfication;
    @Bind(R.id.my_toplayout)
    RelativeLayout my_toplayout;////头部布局，设置颜色样式
    @Bind(R.id.ClassificationGridview)
    MyGridView mClassificationGridview;//分类内容显示gridview
    @Bind(R.id.classificationScroll)
    ScrollView mClassificationScroll;//滑动布局
    @Bind(R.id.SureChioce)
    Button mSureChioce;//确定选择按钮
    @Bind(R.id.Work_classFicationTitle)
    TextView Work_classFicationTitle;//标题文字
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    private Gson mGson=new Gson();
    int[] m;//记录多选按钮是否选中，1代表选中，0代表未选中
    List<ShowsClass> mClassList=new ArrayList<>();//分类集合
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_classification);
        ButterKnife.bind(this);
        if (mSharedPreferences.Get_PREFS(WorkClassificationActivity.this, "Type").equals("baby")) {
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            tintManageUtils.setSystemBarTintManage_noColor(WorkClassificationActivity.this);
            my_toplayout.setBackgroundResource(R.drawable.classify_topbg);
            mClassificationReturn.setImageResource(R.drawable.return_arrow);
            mClassificationGridview.setBackgroundResource(R.color.gridView2);
            Work_classFicationTitle.setTextColor(getResources().getColor(R.color.red_grey));
        }else  if (mSharedPreferences.Get_PREFS(WorkClassificationActivity.this, "Type").equals("mom")) {
            mWorkClassfication.setBackgroundColor(getResources().getColor(R.color.bg));
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(WorkClassificationActivity.this, R.color.green);
            mClassificationGridview.setBackgroundResource(R.color.gridView);
        }
        getclassfication();
    }
//获取作品分类
    private void getclassfication() {
        ShowsClassTerm mShowsTerm=new ShowsClassTerm();
        mShowsTerm.setIsUse("-1");
        String data = mGson.toJson(mShowsTerm);
        OKHttp.OkHttpPost("?type=9", "data", data,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "分类：" + response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            ShowsClass Shows = mGson.fromJson(array.toString(), ShowsClass.class);
                            mClassList.add(Shows);
                        }
                        mClassificationGridview.setAdapter(new MyGridviewAdapter(WorkClassificationActivity.this));
                        mClassificationScroll.smoothScrollTo(0,20);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        mSureChioce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_Static.ShowsClassId="";
                for(int n=0;n<m.length;n++){
                    if(m[n]==1){
                        if(n==0)
                        Login_Static.ShowsClassId=mClassList.get(n).getId();
                        else {
                            Login_Static.ShowsClassId=Login_Static.ShowsClassId+","+mClassList.get(n).getId();
                        }
                    }
                    if(mSave.Get_PREFS(WorkClassificationActivity.this,"Type").equals("mom")){
                        mSave.Save_PREFS(WorkClassificationActivity.this,"Refresh1","yes");//刷新妈妈端作品列表
                    }else if(mSave.Get_PREFS(WorkClassificationActivity.this,"Type").equals("baby")) {
                        mSave.Save_PREFS(WorkClassificationActivity.this, "Refresh2", "yes");//刷新宝宝端全部作品数据列表
                        mSave.Save_PREFS(WorkClassificationActivity.this, "Refresh3", "yes");//刷新宝宝端好友作品数据列表
                    }
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.Classification_return)
    public void onClick() {
        finish();
    }

    //自定义分类gridView适配器
    class MyGridviewAdapter extends BaseAdapter {
        //上下文对象
        private Context mContext;
        ViewHolder viewHolder = null;
        //黑白图片数组





        MyGridviewAdapter(Context context) {
            this.mContext = context;
            m = new int[mClassList.size()];
            String[] n=Login_Static.ShowsClassId.split(",");
            for (int i = 0; i < m.length; i++) {
                if(n.length>0){
                    for(int j=0;j<n.length;j++){
                        CLog.e("m",""+mClassList.get(i).getId());
                        CLog.e("n",""+n[j]);
                        if(mClassList.get(i).getId().equals(n[j])){
                            m[i] = 1;
                            break;
                        }
                        if(n.length-1==j){
                            m[i] = 0;
                        }
                    }
                }else {
                    m[i] = 0;
                }



            }
        }

        public int getCount() {
            return mClassList.size();
        }

        public Object getItem(int item) {
            return mClassList.get(item);
        }

        public long getItemId(int id) {
            return id;
        }

        //创建View方法
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_classification_gridview, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.classificationgridviewItem);
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.classificationText);
                viewHolder.Classfication_topLayout = (RelativeLayout) convertView.findViewById(R.id.classfication_topLayout);
                viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.classificationText_select);
                convertView.setTag(viewHolder);

                /*  imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(75, 75));//设置ImageView对象布局
                imageView.setAdjustViewBounds(false);//设置边界对齐
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
                imageView.setPadding(8, 8, 8, 8);//设置间距*/
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mSave.Get_PREFS(WorkClassificationActivity.this, "Type").equals("baby")){
                viewHolder.Classfication_topLayout.setBackgroundResource(R.drawable.gv_two_selector);
            }
            ShowsClass bean=mClassList.get(position);
            viewHolder.mTextView.setText(bean.getClassName());
            if(bean.getGrayIcon()!=null){
                Uri uri = Uri.parse(SubString_Utils.getImageUrl(bean.getGrayIcon()));
                viewHolder.imageView.setImageURI(uri);
            }

            if (m[position] == 0) {
                if(bean.getGrayIcon()!=null){
                    Uri uri = Uri.parse(SubString_Utils.getImageUrl(bean.getGrayIcon()));
                    viewHolder.imageView.setImageURI(uri);
                }

                viewHolder.mCheckBox.setChecked(false);
            }
            if (m[position] == 1) {
                if(bean.getIcon()!=null){
                    Uri uri = Uri.parse(SubString_Utils.getImageUrl(bean.getIcon()));
                    viewHolder.imageView.setImageURI(uri);
                }
                viewHolder.mCheckBox.setChecked(true);
            }
            viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        m[position] = 1;
                    } else {
                        m[position] = 0;
                    }
                    notifyDataSetChanged();
                }
            });
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m[position] == 0) {
                        m[position] = 1;
                    } else {
                        m[position] = 0;
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            SimpleDraweeView imageView;//分类图片
            TextView mTextView;//分类名称
            CheckBox mCheckBox;//多选按钮，选中监听;
            RelativeLayout Classfication_topLayout;
        }
    }
}
