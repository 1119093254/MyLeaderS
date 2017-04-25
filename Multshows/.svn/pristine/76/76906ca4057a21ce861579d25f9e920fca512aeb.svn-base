package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.BankType_Adapter;
import com.multshows.Beans.SystemBankCard;
import com.multshows.Beans.SystemBankTerm;
import com.multshows.Beans.UserBankAdd;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyPicPopupWindow_place;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 添加银行卡
 */
public class Add_BankCord_Activity extends AppCompatActivity implements MyPicPopupWindow_place.getProvinceAndCity{
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局\
    TextView mTopTitle;//大标题
    EditText mName;
    EditText mBankNumber;
    EditText mIDCard;
    EditText mPhone;
    RelativeLayout mCityLayout;//开户城市
    RelativeLayout mBankType;//银行卡类型
    TextView mCityText;
    ImageView mBankTypeImage;
    TextView mBankTypeText;//银行卡类型文本
    LinearLayout mBankTypeContent;//银行卡类型内容布局
    ListView mListView;//银行卡类型内容
    CheckBox mIsDefault;
    Button mOk;
    TextView mXieYi;//协议
    Dialog mDialog;
    //适配器
    BankType_Adapter mBankTypeAdapter;
    //数据集合
    List<SystemBankCard> mList=new ArrayList<>();

    Gson mGson=new Gson();
    MyPicPopupWindow_place mPopWindow;
    //省市区名
    String Province, City, District;
    //确定后的省市区名
    String Provinces, Citys, Districts;
    //省市区id
    String province_id = "", city_id = "", district_id = "";
    //确定后的省市区id
    String province_ids = "", city_ids = "", district_ids = "";
    //显示银行卡类型标志
    boolean typeFlag=false;
    String BankName="";//银行简称
    int flags=0;//0不默认1默认

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_cord);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Add_BankCord_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 功能: 初始化控件
     *
     * 作者: 贾强胜
     * 时间: 2017/2/13 9:17
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Add_BankCord_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Add_BankCord_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Add_BankCord_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Add_BankCord_Title);
        mName= (EditText) findViewById(R.id.Add_BankCord_nameEdit);
        mBankNumber= (EditText) findViewById(R.id.Add_BankCord_BankEdit);
        mIDCard= (EditText) findViewById(R.id.Add_BankCord_IDCardEdit);
        mPhone= (EditText) findViewById(R.id.Add_BankCord_phoneEdit);
        mIsDefault= (CheckBox) findViewById(R.id.Add_BankCord_Message);
        mOk= (Button) findViewById(R.id.Add_BankCord_Button);
        mXieYi= (TextView) findViewById(R.id.Add_BankCord_XieYi);
        mCityLayout= (RelativeLayout) findViewById(R.id.Add_BankCord_CityLayout);
        mCityText= (TextView) findViewById(R.id.Add_BankCord_CityText);
        mBankType= (RelativeLayout) findViewById(R.id.Add_BankCord_BankTypeLayout);
        mBankTypeImage= (ImageView) findViewById(R.id.Add_BankCord_BankTypeImage);
        mBankTypeText= (TextView) findViewById(R.id.Add_BankCord_BankTypeText);
        mBankTypeContent= (LinearLayout) findViewById(R.id.Add_BankCord_BankTypeContent);
        mListView= (ListView) findViewById(R.id.Add_BankCord_BankTypeContentListView);
        mDialog=new HintText_Dialog(Add_BankCord_Activity.this,R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Add_BankCord_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    private void initData() {
        mBankTypeAdapter=new BankType_Adapter(Add_BankCord_Activity.this,mList);
        mListView.setAdapter(mBankTypeAdapter);
        getBankInformation();
    }

    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideInputManager_Utils.hideInputManager(Add_BankCord_Activity.this);
                finish();
            }
        });
        //提交
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                String bankCard=mBankNumber.getText().toString();
                String name=mName.getText().toString();
                String IDCard=mIDCard.getText().toString();
                String phone=mPhone.getText().toString();
                if("".equals(bankCard) || "".equals(IDCard)
                        || "".equals(phone) || "".equals(name) || "".equals(mBankTypeText.getText().toString()) ||
                        "".equals(mCityText.getText().toString())){
                    HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,"请完善信息","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          mDialog.dismiss();
                        }
                    },Login_Static.hintTime);
                }else {
                    if(!Verification_Utils.isIDCard(IDCard)){
                        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                                "请输入正确的身份证号","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }else if(!Verification_Utils.checkBankCard(bankCard)){
                        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                                "请输入正确的银行卡号","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }else if(!Verification_Utils.checkMobileNumber(phone)){
                        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                                "请输入正确的手机号","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }else {
                        addBank(bankCard,name,IDCard);
                    }

                }

            }
        });
        //默认开关
        mIsDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    flags=1;
                }else {
                    flags=0;
                }
            }
        });
        //协议
        mXieYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Add_BankCord_Activity.this,Login_Agreement_Activity.class);
                startActivity(intent);
            }
        });
        //点击开户城市
        mCityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Add_BankCord_Activity.this);
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow = new MyPicPopupWindow_place(Add_BankCord_Activity.this,
                        itemsOnClick, province_ids, city_ids, district_ids);
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(Add_BankCord_Activity.this.
                                findViewById(R.id.Add_BankCord_ParentsLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        //选择银行卡类型
        mBankType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Add_BankCord_Activity.this);
                if(typeFlag){
                    mBankTypeImage.setImageResource(R.drawable.item_register_chocedown);
                    mBankTypeContent.setVisibility(View.GONE);
                    typeFlag=false;
                }else {
                    mBankTypeImage.setImageResource(R.drawable.item_register_choceup);
                    mBankTypeContent.setVisibility(View.VISIBLE);
                    typeFlag=true;
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBankTypeText.setText(mList.get(position).getName());
                BankName=mList.get(position).getCode();
                mBankTypeImage.setImageResource(R.drawable.item_register_chocedown);
                mBankTypeContent.setVisibility(View.GONE);
                typeFlag=false;
            }
        });

    }
    /**
     * 功能: 为弹出窗口实现监听类
     *
     * 作者: 贾强胜
     * 时间: 2017/2/13 9:25
     */
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.myPopupWindow_place_save:
                    //保存
                    province_ids = province_id;
                    city_ids = city_id;
                    district_ids = district_id;
                    Provinces = Province;
                    Citys = City;
                    Districts = District;
                    mCityText.setText(Provinces + " " + Citys + " " + Districts);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };
    /**
     * 添加银行卡
     * @param bankCard 卡号
     * @param name 真实姓名
     * @param IDCard 身份证号码
     */
    private void addBank(String bankCard,String name,String IDCard){
        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                "提交中...","");
        UserBankAdd userBankAdd=new UserBankAdd();
        userBankAdd.setUserId(Login_Static.myUserID);
        userBankAdd.setBank(BankName);
        userBankAdd.setBankName(mBankTypeText.getText().toString());
        userBankAdd.setProName(Province);
        userBankAdd.setCityName(City);
        userBankAdd.setCard(bankCard);
        userBankAdd.setIdName(name);
        userBankAdd.setIdCard(IDCard);
        userBankAdd.setIsDefault(flags);//0不默认1默认
        String data=mGson.toJson(userBankAdd);
        CLog.e("testing","添加银行卡data"+data);
        OKHttp.OkHttpPost("/User/AddUserBank", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","添加银行卡失败"+e.toString());
                HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                        "添加银行卡失败","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","添加银行卡"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                                "添加成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                finish();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mTextDialog=new HintText_Dialog(Add_BankCord_Activity.this,
                                Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setProvince(String Province) {
        this.Province = Province;
    }

    @Override
    public void setCity(String City) {
        this.City = City;
    }

    @Override
    public void setDistrict(String District) {
        this.District = District;
    }

    @Override
    public void setProvinceID(String province_id) {
        this.province_id = province_id;
    }

    @Override
    public void setCityID(String city_id) {
        this.city_id = city_id;
    }

    @Override
    public void setDistrictID(String district_id) {
        this.district_id = district_id;
    }

    /**
     * 功能: 获取可绑定银行卡列表
     *
     * 作者: 贾强胜
     * 时间: 2017/2/13 9:26
     */
    private void getBankInformation(){
        SystemBankTerm systemBankTerm=new SystemBankTerm();
        String data=mGson.toJson(systemBankTerm);
        OKHttp.OkHttpPost("/SystemSet/GetListSystemBankCard", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取可绑定银行卡列表失败"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取可绑定银行卡列表"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        mList.clear();
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SystemBankCard bean=mGson.fromJson(jsonArray.getString(i),SystemBankCard.class);
                            mList.add(bean);
                        }
                        mBankTypeAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
