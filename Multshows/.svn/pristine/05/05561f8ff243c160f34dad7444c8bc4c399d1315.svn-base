package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.FlowerRewordList_Adapter;
import com.multshows.Beans.Idle_ProduceDetails_Bean;
import com.multshows.Beans.ShowsFlower;
import com.multshows.Beans.ShowsFlowerTerm;
import com.multshows.Beans.ShowsReward;
import com.multshows.Beans.ShowsRewardTerm;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
/**
 * 送花，打赏，赞人列表显示界面
 */
public class FlowerAndReword_Activity extends AppCompatActivity {
    TextView FlowerOrRewardTitle;//送花或打赏
    LinearLayout AllRewardLayout;//送花或打赏
    ListView UserListView;//打赏人列表listview
    ImageView Classification_return;
    RelativeLayout my_Rewordtoplayout;
    //List<UserHead> UsernameList;//送花人姓名
    WorkAll_Beans mBeans;
    Idle_ProduceDetails_Bean mLikeBeans;
    String text = "";//判断传来的内容，本页显示对应的内容
    Gson mGson = new Gson();
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowerandreword);
        //设置状态栏
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(FlowerAndReword_Activity.this, R.color.green);
        Intent intent = getIntent();//接收传过来的值
        mBeans = (WorkAll_Beans) intent.getSerializableExtra("bean");
        mLikeBeans = (Idle_ProduceDetails_Bean) intent.getSerializableExtra("mLikeBeans");
        text = intent.getStringExtra("type");
        //绑定id
        FlowerOrRewardTitle = (TextView) findViewById(R.id.FlowerOrRewardTitle);
        my_Rewordtoplayout = (RelativeLayout) findViewById(R.id.my_Rewordtoplayout);
        AllRewardLayout = (LinearLayout) findViewById(R.id.AllRewardLayout);
        Classification_return = (ImageView) findViewById(R.id.Classification_return);
        UserListView = (ListView) findViewById(R.id.UserListView);
        FlowerOrRewardTitle.setText(text);
       //判断传来的内容，本页显示对应的内容标题
        if (text.equals("赞")) {
            RequestLike();
        }else  if (text.equals("送花")) {
            RequestFlowerList();
        }else if(text.equals("打赏")){
            RequestRewardList();
        }
        //家长端，宝宝端界面显示区分设置
        if(!mSave.Get_PREFS(FlowerAndReword_Activity.this, "Type").equals("mom")){
            tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            tintManageUtils.setSystemBarTintManage_noColor(FlowerAndReword_Activity.this);
            my_Rewordtoplayout.setBackgroundResource(R.drawable.classify_topbg);
            Classification_return.setImageResource(R.drawable.return_arrow);
            FlowerOrRewardTitle.setTextColor(getResources().getColor(R.color.red_grey));
        }else AllRewardLayout.setBackgroundColor(getResources().getColor(R.color.white));
        Classification_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //赞列表
    private void RequestLike() {
        if(mBeans!=null){
            FlowerOrRewardTitle.setText(text+"("+mBeans.getLikeUsername().size()+")");
            FlowerRewordList_Adapter mAdapter = new FlowerRewordList_Adapter(FlowerAndReword_Activity.this,mBeans.getLikeUsername(),2);
            UserListView.setAdapter(mAdapter);
        }else {
            FlowerOrRewardTitle.setText(text+"("+mLikeBeans.getPicLikeString().size()+")");
            FlowerRewordList_Adapter mAdapter = new FlowerRewordList_Adapter(FlowerAndReword_Activity.this,mLikeBeans.getPicLikeString(),2);
            UserListView.setAdapter(mAdapter);
        }

    }

    //请求打赏列表
    private void RequestRewardList() {
        ShowsRewardTerm mShowsRewardTerm = new ShowsRewardTerm();
        mShowsRewardTerm.setShowsId(mBeans.getId());
        mShowsRewardTerm.setState(4);
        String data = mGson.toJson(mShowsRewardTerm);
        OKHttp.OkHttpPost("/Shows/GetListShowsReward", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "成功：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {

                        List<ShowsReward> mRewardList = new ArrayList<ShowsReward>();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        FlowerOrRewardTitle.setText(text+"("+jsonArray.length()+")");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            ShowsReward mShowsFlower = mGson.fromJson(array.toString(), ShowsReward.class);
                            mRewardList.add(mShowsFlower);
                        }
                        FlowerRewordList_Adapter mAdapter = new FlowerRewordList_Adapter(mRewardList, FlowerAndReword_Activity.this,1);
                        UserListView.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //请求送花列表
    private void RequestFlowerList() {
        ShowsFlowerTerm mShowsFlowerTerm = new ShowsFlowerTerm();
        mShowsFlowerTerm.setShowsId(mBeans.getId());
        String data = mGson.toJson(mShowsFlowerTerm);
        OKHttp.OkHttpPost("/Shows/GetListShowsFlower", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "成功：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        List<ShowsFlower> mFlowerList = new ArrayList<ShowsFlower>();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        FlowerOrRewardTitle.setText(text+"("+jsonArray.length()+")");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            ShowsFlower mShowsFlower = mGson.fromJson(array.toString(), ShowsFlower.class);
                            mFlowerList.add(mShowsFlower);
                        }
                        FlowerRewordList_Adapter mAdapter = new FlowerRewordList_Adapter(mFlowerList, FlowerAndReword_Activity.this);
                        UserListView.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
