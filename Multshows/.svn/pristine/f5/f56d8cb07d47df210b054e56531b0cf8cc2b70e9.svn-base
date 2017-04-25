
package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.UserAddress;
import com.multshows.Beans.UserAddressTerm;
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
import com.multshows.Views.MyPicPopupWindow_place;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 添加新的收货地址
 */
public class Add_NewAddress_Activity extends AppCompatActivity implements MyPicPopupWindow_place.getProvinceAndCity {
    public static final int RESULT_CODE = 1;
    //返回
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ImageView mImageView;//设置默认
    RadioGroup mGroup;
    RadioButton mRadioButton1, mRadioButton2;
    RelativeLayout mAddressLayout;
    TextView mPlace;
    EditText mName;//收件人
    EditText mPhone;//手机
    EditText mMen;//门牌号
    EditText mPlaceOther;
    Gson mGson = new Gson();
    UserAddress address;//收货地址
    MyPicPopupWindow_place mPopWindow;
    //保存
    private TextView mSave;
    //设置默认标志  0不默认1默认   默认0
    String flag = "0";
    //添加地址0   修改地址1
    int goFlag = 0;
    //省市区名
    String Province, City, District;
    //确定后的省市区名
    String Provinces, Citys, Districts;
    //省市区id
    String province_id = "", city_id = "", district_id = "";
    //确定后的省市区id
    String province_ids = "", city_ids = "", district_ids = "";

    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Add_NewAddress_Activity.this, R.color.app_topColor);

        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.add_NewAddress_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.add_NewAddress_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.add_NewAddress_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.add_NewAddress_TopTitle);
        mAddressLayout = (RelativeLayout) findViewById(R.id.add_NewAddress_schoolLayout);
        mPlace = (TextView) findViewById(R.id.add_NewAddress_placeText);
        mSave = (TextView) findViewById(R.id.add_NewAddress_save);
        mImageView = (ImageView) findViewById(R.id.add_NewAddress_image);
        mName = (EditText) findViewById(R.id.add_NewAddress_Name);
        mPhone = (EditText) findViewById(R.id.add_NewAddress_Phone);
        mMen = (EditText) findViewById(R.id.add_NewAddress_Men);
        mPlaceOther = (EditText) findViewById(R.id.add_NewAddress_place_OtherLayout);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Add_NewAddress_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle,mSave);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            address = (UserAddress) bundle.getSerializable("Address");
            goFlag = 1;
            mName.setText(address.getReceiverName());
            mPhone.setText(address.getMobile());
            mPlaceOther.setText(address.getByName());
            //
            mMen.setText(address.getDetailAddress());
            //
            Provinces = address.getProvinceName();
            Citys = address.getCityname();
            Districts = address.getDistrictName();
            Province = address.getProvinceName();
            City = address.getCityname();
            District = address.getDistrictName();
            province_ids = address.getProvinceId();
            city_ids = address.getCityId();
            district_ids = address.getDistrictId();
            province_id = address.getProvinceId();
            city_id = address.getCityId();
            district_id = address.getDistrictId();
            //
            mPlace.setText(Provinces + " " + Citys + " " + Districts);

            flag = address.getIsDefault();
            CLog.e("LOL", flag);
            if ("0".equals(flag)) {
                mImageView.setImageResource(R.drawable.switch_off);
            } else {
                mImageView.setImageResource(R.drawable.switch_on);
            }
        }

    }

    /**
     * 事件处理
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //选择省市区
        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Add_NewAddress_Activity.this);
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow = new MyPicPopupWindow_place(Add_NewAddress_Activity.this,
                        itemsOnClick, province_ids, city_ids, district_ids);
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(Add_NewAddress_Activity.this.
                                findViewById(R.id.add_NewAddress_ParentsLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        //保存
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new HintText_Dialog(Add_NewAddress_Activity.this, R.style.MyDialog);
                mDialog.show();
                if (goFlag == 0) {
                    //添加地址保存
                    if (mName.getText().toString().equals("") || mPhone.getText().toString().equals("")
                            || mMen.getText().toString().equals("") || mPlace.getText().toString().equals("")) {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "请将信息填写完整", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    } else if (!Verification_Utils.checkMobileNumber(mPhone.getText().toString())) {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "请输入正确的手机号码", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    } else {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "正在添加...", "");
                        UserAddress userAddress = new UserAddress();
                        userAddress.setUserId(Login_Static.myUserID);
                        userAddress.setReceiverName(mName.getText().toString());
                        userAddress.setMobile(mPhone.getText().toString());
                        userAddress.setByName(mPlaceOther.getText().toString());
                        userAddress.setDetailAddress(mMen.getText().toString());
                        userAddress.setProvinceName(Provinces);
                        userAddress.setProvinceId(province_ids);
                        userAddress.setCityId(city_ids);
                        userAddress.setCityname(Citys);
                        userAddress.setDistrictName(Districts);
                        userAddress.setDistrictId(district_ids);
                        userAddress.setIsDefault(flag);
                        String data = mGson.toJson(userAddress);
                        CLog.e("testing", "添加收货地址data：" + data);
                        OKHttp.OkHttpPost("/User/AddAddress", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "添加收货地址失败：" + e.toString());
                                HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "网络异常", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "添加收货地址：" + response);
                                try {
                                    if (Json_Utils.getCode(response) == 0) {
                                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "添加成功", "success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                getShipAddress(1);
                                            }
                                        }, Login_Static.hintTime);
                                    } else {
                                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this,
                                                Json_Utils.getMessage(response), "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                } else {
                    //修改地址保存
                    if (mName.getText().toString().equals("") || mPhone.getText().toString().equals("")
                            || mMen.getText().toString().equals("") || mPlace.getText().toString().equals("")) {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "请将信息填写完整", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    } else if (!Verification_Utils.checkMobileNumber(mPhone.getText().toString())) {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "请输入正确的手机号码", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    } else {
                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "正在修改...", "");
                        UserAddress userAddress = new UserAddress();
                        userAddress.setId(address.getId());
                        userAddress.setUserId(address.getUserId());
                        userAddress.setReceiverName(mName.getText().toString());
                        userAddress.setMobile(mPhone.getText().toString());
                        userAddress.setDetailAddress(mMen.getText().toString());
                        userAddress.setByName(mPlaceOther.getText().toString());
                        userAddress.setProvinceName(Provinces);
                        userAddress.setProvinceId(province_ids);
                        userAddress.setCityId(city_ids);
                        userAddress.setCityname(Citys);
                        userAddress.setDistrictName(Districts);
                        userAddress.setDistrictId(district_ids);
                        userAddress.setIsDefault(flag);
                        String data = mGson.toJson(userAddress);
                        CLog.e("testing", "修改收货地址data：" + data);
                        OKHttp.OkHttpPost("/User/UpdateAddress", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "修改收货地址失败：" + e.toString());
                                HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "网络异常", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "修改收货地址：" + response);
                                try {
                                    if (Json_Utils.getCode(response) == 0) {
                                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this, "修改成功", "success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                finish();
                                            }
                                        }, Login_Static.hintTime);
                                    } else {
                                        HintText_Dialog mHintTextDialog = new HintText_Dialog(Add_NewAddress_Activity.this,
                                                Json_Utils.getMessage(response), "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
        //通知按钮
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(flag)) {
                    mImageView.setImageResource(R.drawable.switch_off);
                    flag = "0";
                } else {
                    mImageView.setImageResource(R.drawable.switch_on);
                    flag = "1";
                }
                CLog.e("LOL123", flag);
            }
        });

    }

    //为弹出窗口实现监听类
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
                    mPlace.setText(Provinces + " " + Citys + " " + Districts);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };


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


    private void getShipAddress(final int pageIndex) {
        UserAddressTerm userAddressTerm = new UserAddressTerm(Login_Static.myUserID, -1, pageIndex, 10);
        String data = mGson.toJson(userAddressTerm);
        OKHttp.OkHttpPost("/User/GetAddressList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取收货地址列表失败" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取收货地址列表" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {

                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        UserAddress userAddress = mGson.fromJson(jsonArray.getString(0), UserAddress.class);
                        Intent intent2 = getIntent();
                        //intent2.setClass(Add_NewAddress_Activity.this, BuyProduce_Activity.class);
                       /* Bundle bundle2 = new Bundle();
                        bundle2*/
                        CLog.e("mUserAddress",userAddress.getReceiverName());
                        intent2.putExtra("mUserAddress", userAddress);
                        setResult(6, intent2);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
