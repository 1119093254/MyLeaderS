package com.multshows.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_Information_ShipAddress_Adapter;
import com.multshows.Beans.My_ShipAddress_Beans;
import com.multshows.Beans.UserAddress;
import com.multshows.Beans.UserAddressTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的信息——我的收货地址
 */
public class My_Information_ShipAddress_Activity extends AppCompatActivity {
    public static final int REQUEST_CODE = 2;
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    RelativeLayout mAddAddress;//添加收货地址
    ListView mListView;
    public String chioce="";//是否从订单页跳进来
    //适配器
    My_Information_ShipAddress_Adapter mShipAddressAdapter;
    //数据集合
    List<UserAddress> mList = new ArrayList<>();
    Gson mGson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information_shipadress);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Information_ShipAddress_Activity.this,R.color.app_topColor);
        if(getIntent()!=null)
        chioce=getIntent().getStringExtra("chioce");
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.My_Inforation_ShipAddress_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Inforation_ShipAddress_TopLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.My_Inforation_ShipAddress_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_Inforation_ShipAddress_TopTitle);
        mAddAddress = (RelativeLayout) findViewById(R.id.My_Inforation_ShipAddress_BottomLayout);
        mListView = (ListView) findViewById(R.id.My_Inforation_ShipAddress_ListView);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Information_ShipAddress_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);

    }

    /**
     * 数据处理
     */
    private void initData() {
        mShipAddressAdapter = new My_Information_ShipAddress_Adapter(My_Information_ShipAddress_Activity.this, mList,chioce);
        mListView.setAdapter(mShipAddressAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getShipAddress(1);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* UserAddress mUserAddress=mList.get(position);
                Intent intent2 = new Intent();
                intent2.setClass(My_Information_ShipAddress_Activity.this, BuyProduce_Activity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("mUserAddress", mUserAddress);
                setResult(5, intent2);
                finish();*/
            }
        });
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        //添加新地址
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(My_Information_ShipAddress_Activity.this,Add_NewAddress_Activity.class);
                startActivity(intent);
            }
        });

    }


    private void getShipAddress(final int pageIndex){
        UserAddressTerm userAddressTerm=new UserAddressTerm(Login_Static.myUserID,-1,pageIndex,10);
        String data=mGson.toJson(userAddressTerm);
        OKHttp.OkHttpPost("/User/GetAddressList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取收货地址列表失败"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取收货地址列表"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        if(pageIndex==1){
                            mList.clear();
                        }
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i=0;i<jsonArray.length();i++){
                            UserAddress userAddress=mGson.fromJson(jsonArray.getString(i),UserAddress.class);
                            mList.add(userAddress);
                        }
                        mShipAddressAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }




}
