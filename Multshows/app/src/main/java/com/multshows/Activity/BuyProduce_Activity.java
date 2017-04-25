package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.OrderAdd;
import com.multshows.Beans.Shows;
import com.multshows.Beans.UserAddress;
import com.multshows.Beans.UserAddressTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Call;

/**
 * 描述：确认订单界面
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class BuyProduce_Activity extends BaseActivity {
    String AddressId;//当前显示收货地址的id
    RelativeLayout buy_produceLayout;//整个页面布局
    RelativeLayout BuyNumLAyout;//购买数量布局
    RelativeLayout BuyBootomDetial;//购买价格详情
    OrderAdd mOrderAdd;//添加订单对象
    Shows mShows;//闲置详情
    TextView purchase_title;//闲置标题
    TextView price;//闲置价格
    TextView Freight_price;//运费
    TextView FromWhere;//地点
    TextView Idle_num_reduce;//减号
    TextView IdleNum;//数量
    TextView Idle_num_add;//加号
    TextView username;//用户姓名
    TextView userphone;//用户账号
    TextView addressText;//地址
    TextView Pricecount;//商品总额
    TextView ContactsellerText;//联系卖家文字
    TextView freightPrice;//运费
    TextView countPrice;//总费用
    TextView buyTitle;//购买标题
    TextView Count;//剩余数量
    Button BuyButton;//购买按钮
    TextView addAddressText;//添加收货地址文字
    int idleNum = 1;//购买数量
    Dialog mDialog;//消息提示框
    Dialog_Hint mDialogHint;//提示余额不足
    UserAddress mUserAddress;
    SimpleDraweeView image;
    TextView DefaultText;
    RelativeLayout addAddressLayout;//添加收货地址布局
    RelativeLayout namePhoneLayout;//地址收货姓名布局
    RelativeLayout addressTextLayout;//地址文字所在布局
    RelativeLayout addressLayout;//有地址时布局
    RelativeLayout BuyDetailLayout;//有地址时布局
    RelativeLayout my_toplayout;//顶部标题布局
    ImageView Classification_return;
    ImageView ContactsellerImage;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    SaveSharedPreferences mSharedPreferences = new SaveSharedPreferences();
    //数据集合
    Gson mGson = new Gson();
    int flag = 0;//记录是否第一次进入页面 0代表第一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_produce);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BuyProduce_Activity.this, R.color.app_topColor);
        Intent intent = getIntent();
        mShows = (Shows) intent.getSerializableExtra("Shows");
        //模拟
        getShipAddress();
        initView();
        initData();
        initListen();

    }

    /**
     * 功能：绑定id
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 10:38
     */
    @Override
    public void findViews() {

    }

    /**
     * 功能：
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 10:39
     */
    @Override
    public void addListeners() {

    }

    /**
     * 功能：初始化数据
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 10:40
     */
    private void initData() {
        mDialog = new HintText_Dialog(BuyProduce_Activity.this, R.style.MyDialog);
        //获取作品第一张图片显示
        if (mShows.getShowsPhoto() != null) {
            String[] imag = mShows.getShowsPhoto().split(",");//分割图片，获取到第一张图片的名称
            Uri mUri = Uri.parse(SubString_Utils.getImageUrl(imag[0]));//设置图片全称
            image.setImageURI(mUri);
        }
        //家长端，宝宝端界面显示区分设置
        if (mSharedPreferences.Get_PREFS(BuyProduce_Activity.this, "Type").equals("baby")) {//宝宝端界面显示
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage_noColor(BuyProduce_Activity.this);//设置状态栏
            Classification_return.setImageResource(R.drawable.return_arrow);//返回
            ContactsellerImage.setImageResource(R.drawable.redcontact_seller);//联系卖家 图标
            buyTitle.setTextColor(getResources().getColor(R.color.red_grey));//标题颜色设置
            ContactsellerText.setTextColor(getResources().getColor(R.color.red_grey));//联系卖家颜色设置
            my_toplayout.setBackgroundResource(R.drawable.classify_topbg);//顶部背景设置
        } else if (mSharedPreferences.Get_PREFS(BuyProduce_Activity.this, "Type").equals("mom")) {
            buy_produceLayout.setBackgroundColor(getResources().getColor(R.color.border_grey));
            BuyDetailLayout.setBackgroundColor(getResources().getColor(R.color.white));
            ContactsellerText.setTextColor(getResources().getColor(R.color.white));//联系卖家颜色设置
            BuyBootomDetial.setBackgroundColor(getResources().getColor(R.color.white));//购买价格详情背景设置
            addAddressLayout.setBackgroundColor(getResources().getColor(R.color.white));//活动地址背景设置
            BuyNumLAyout.setBackgroundColor(getResources().getColor(R.color.white));//购买数量 布局
            addressLayout.setBackgroundColor(getResources().getColor(R.color.white));//活动地址背景设置
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(BuyProduce_Activity.this, R.color.green);////设置状态栏
        }
        //如果是闲置详情界面，返回到闲置列表，会刷新闲置列表
        if (mShows.getShowsType() == 2)
            mSharedPreferences.Save_PREFS(BuyProduce_Activity.this, "idleFresh", "yes");
        purchase_title.setText(mEmoji_change.ChangeEmoji(mShows.getInfos()));
        price.setText("¥" + mShows.getPrice() + "");//价格显示
        Freight_price.setText("（" + mShows.getExpressFee() + "元运费）");//运费显示
        //地址显示
        if (!mShows.getAddress().equals("")) {
            FromWhere.setText("来自" + mShows.getAddress());
        } else FromWhere.setText("");
        //总价 （不含运费）
        Pricecount.setText("¥" + (mShows.getPrice() * idleNum));
        freightPrice.setText("+ ¥" + mShows.getExpressFee() + "");//运费
        //总价 （含运费）
        countPrice.setText("" + (mShows.getExpressFee() + mShows.getPrice() * idleNum));
        Count.setText("还剩" + mShows.getAmount() + "件");
        //订单对象OrderAdd赋值
        mOrderAdd = new OrderAdd();
        mOrderAdd.setShowsId(mShows.getId());
        mOrderAdd.setBuyerId(Login_Static.myUserID);
        mOrderAdd.setSellerId(mShows.getUser().getUserId());
        mOrderAdd.setPrice(mShows.getPrice() * idleNum);
        mOrderAdd.setRealPrice((mShows.getExpressFee() + mShows.getPrice() * idleNum));
        mOrderAdd.setExpressFee(mShows.getExpressFee());
        mOrderAdd.setSellerId(mShows.getUser().getUserId());
        mOrderAdd.setState(2);
    }
    /**
     * 功能：初始化控件
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 10:42
     */
    private void initView() {
        buy_produceLayout = (RelativeLayout) findViewById(R.id.buy_produceLayout);
        BuyBootomDetial = (RelativeLayout) findViewById(R.id.BuyBootomDetial);
        BuyNumLAyout = (RelativeLayout) findViewById(R.id.BuyNumLAyout);
        BuyDetailLayout = (RelativeLayout) findViewById(R.id.BuyDetailLayout);
        Classification_return = (ImageView) findViewById(R.id.Classification_return);
        ContactsellerImage = (ImageView) findViewById(R.id.ContactsellerImage);
        addressLayout = (RelativeLayout) findViewById(R.id.addressLayout);
        purchase_title = (TextView) findViewById(R.id.purchase_title);
        price = (TextView) findViewById(R.id.price);
        Freight_price = (TextView) findViewById(R.id.Freight_price);
        ContactsellerText = (TextView) findViewById(R.id.ContactsellerText);
        Count = (TextView) findViewById(R.id.Count);
        FromWhere = (TextView) findViewById(R.id.fromWhere);
        Idle_num_reduce = (TextView) findViewById(R.id.Idle_num_reduce);
        buyTitle = (TextView) findViewById(R.id.buyTitle);
        IdleNum = (TextView) findViewById(R.id.IdleNum);
        Idle_num_add = (TextView) findViewById(R.id.Idle_num_add);
        DefaultText = (TextView) findViewById(R.id.DefaultText);
        username = (TextView) findViewById(R.id.username);
        userphone = (TextView) findViewById(R.id.userphone);
        addressText = (TextView) findViewById(R.id.addressText);
        Pricecount = (TextView) findViewById(R.id.Pricecount);
        freightPrice = (TextView) findViewById(R.id.freightPrice);
        countPrice = (TextView) findViewById(R.id.countPrice);
        addAddressText = (TextView) findViewById(R.id.addAddressText);
        BuyButton = (Button) findViewById(R.id.BuyButton);
        image = (SimpleDraweeView) findViewById(R.id.purchase_image);
        addAddressLayout = (RelativeLayout) findViewById(R.id.addAddressLayout);
        namePhoneLayout = (RelativeLayout) findViewById(R.id.namePhoneLayout);
        addressTextLayout = (RelativeLayout) findViewById(R.id.addressTextLayout);
        my_toplayout = (RelativeLayout) findViewById(R.id.my_toplayout);
    }

    /**
     * 功能：事件处理
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 10:44
     */
    private void initListen() {
        Classification_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //商品数量增加
        Idle_num_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "+++", Toast.LENGTH_SHORT).show();

                if (idleNum < mShows.getAmount()) {//选择数量小于 库存数
                    idleNum++;
                    IdleNum.setText(idleNum + "");
                    Pricecount.setText("¥" + (mShows.getPrice() * idleNum));//价格显示更新
                    countPrice.setText("" + (mShows.getExpressFee() + mShows.getPrice() * idleNum));
                } else {
                    mDialog.show();
                    showError("商品数量不能大于库存数量！", 0);
                }
            }
        });
        //商品数量减少
        Idle_num_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//选择数量不能低于1
                if (idleNum > 1) {
                    idleNum--;
                    IdleNum.setText(idleNum + "");
                } else {
                    mDialog.show();
                    showError("商品数量不能低于1！", 0);

                }
                //价格显示更新
                Pricecount.setText("¥ " + (mShows.getPrice() * idleNum));
                countPrice.setText((mShows.getExpressFee() + mShows.getPrice() * idleNum) + "");
            }
        });
        //地址显示布局按钮点击
        addAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (addAddressText.getText().equals("新建地址")) {//前往新建地址界面
                    intent = new Intent(BuyProduce_Activity.this, Add_NewAddress_Activity.class);
                }
                if (addAddressText.getText().equals("选择收货地址")) {//前往收货地址界面
                    intent = new Intent(BuyProduce_Activity.this, My_Information_ShipAddress_Activity.class);
                    intent.putExtra("chioce", "yes");
                }
                startActivityForResult(intent, 1);
            }
        });
        //去选择收货地址界面
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyProduce_Activity.this, My_Information_ShipAddress_Activity.class);
                intent.putExtra("chioce", "yes");
                startActivityForResult(intent, 1);
            }
        });
        //提交按钮，调用提交订单接口
        BuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
                mOrderAdd.setCount(idleNum);
                HintText_Dialog hint = new HintText_Dialog(BuyProduce_Activity.this, "正在提交...", "");
                BuyButton.setEnabled(false);
                if (mOrderAdd.getAddressId() != null) {
                    String data = mGson.toJson(mOrderAdd);
                    CLog.e("data", data);
                    OKHttp.OkHttpPost("/Order/AddOrder", "", data, new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            //访问错误回调（断网 或者服务器异常）
                            CLog.e("testing", "错误");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testinggg", "changeNickName:" + response);
                            try {
                                if (Json_Utils.getCode(response) == 0) {
                                    //下订单成功
                                    showError("下订单成功", 1);
                                    BuyButton.setEnabled(true);
                                } else if (Json_Utils.getCode(response) == 1016) {
                                    //余额不足
                                    mDialog.dismiss();
                                    mDialogHint = new Dialog_Hint(BuyProduce_Activity.this, 0,
                                            "余额不足，是否前往充值?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //前往我的钱包界面
                                            BuyButton.setEnabled(true);
                                            mDialogHint.dismiss();
                                            Intent intent = new Intent(BuyProduce_Activity.this, My_MyWallet_Activity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    mDialogHint.show();

                                } else {
                                    //其它原因返回
                                    showError(Json_Utils.getMessage(response), 0);
                                    BuyButton.setEnabled(true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    mDialog.show();
                    showError("请选择收货地址！", 0);
                }

            }
        });
    }

    /**
     * 选择收货地址返回的数据处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            switch (resultCode) {
                case 5://选择收货地址后返回地址信息，更新地址显示
                    mUserAddress = (UserAddress) data.getSerializableExtra("mUserAddress");
                    addAddressLayout.setVisibility(View.GONE);
                    addressLayout.setVisibility(View.VISIBLE);
                    AddressId = mUserAddress.getId();
                    username.setText(mUserAddress.getReceiverName());
                    userphone.setText(mUserAddress.getMobile());
                    addressText.setText(mUserAddress.getProvinceName() + " " + mUserAddress.getCityname() + " " + mUserAddress.getDistrictName() + " " + mUserAddress.getDetailAddress());
                    mOrderAdd.setAddressId(mUserAddress.getId());
                    if (mUserAddress.getIsDefault().equals("1")) {//是否为默认 1为默认
                        DefaultText.setVisibility(View.VISIBLE);//默认文字显示
                    } else {
                        DefaultText.setVisibility(View.INVISIBLE);//默认文字隐藏
                    }
                    break;
                case 6://选择收货地址后返回地址信息，更新地址显示
                    mUserAddress = (UserAddress) data.getSerializableExtra("mUserAddress");
                    addAddressLayout.setVisibility(View.GONE);
                    addressLayout.setVisibility(View.VISIBLE);
                    username.setText(mUserAddress.getReceiverName());
                    userphone.setText(mUserAddress.getMobile());
                    addressText.setText(mUserAddress.getProvinceName() + " " + mUserAddress.getCityname() + " " + mUserAddress.getDistrictName() + " " + mUserAddress.getDetailAddress());
                    mOrderAdd.setAddressId(mUserAddress.getId());
                    if (mUserAddress.getIsDefault().equals("1")) {//是否为默认 1为默认
                        DefaultText.setVisibility(View.VISIBLE);//默认文字显示
                    } else {
                        DefaultText.setVisibility(View.INVISIBLE);//默认文字隐藏
                    }
                    break;
            }
        }
    }

    /**
     * 联系商家
     *
     * @param view
     */
    public void chat(View view) {
        Intent intent = new Intent(BuyProduce_Activity.this, Chat_Activity.class);
        intent.putExtra("userId", mShows.getUser().getUserId());
        startActivity(intent);
    }
    /**
     * 功能：获取收货地址(第一次进入，取默认收货地址，若存在地址，
     * 但无默认地址，显示去选择收货地址布局，若是无收货地址，显示去添加收货地址)
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 11:26
     */
    private void getShipAddress() {
        UserAddressTerm userAddressTerm = new UserAddressTerm(Login_Static.myUserID, -1, 0, 0);
        String data = mGson.toJson(userAddressTerm);
        CLog.e("testing", "data=" + data);
        OKHttp.OkHttpPost("/User/GetAddressList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //访问错误回调（断网 或者服务器异常）
                CLog.e("testing", "获取收货地址列表失败" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取收货地址列表" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {//成功获取到收货地址
                        addAddressLayout.setVisibility(View.GONE);//增加收货地址布局隐藏
                        addressLayout.setVisibility(View.VISIBLE);//收货地址显示布局隐藏
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));//json解析
                        for (int i = 0; i < jsonArray.length(); i++) {
                            mUserAddress = mGson.fromJson(jsonArray.getString(i), UserAddress.class);//json解析为UserAddress格式
                            //查询是否存在默认地址
                            if (mUserAddress.getIsDefault().equals("1")) {//1代表默认地址 显示地址
                                DefaultText.setVisibility(View.VISIBLE);
                                AddressId = mUserAddress.getId();
                                username.setText(mUserAddress.getReceiverName());
                                userphone.setText(mUserAddress.getMobile());
                                addressText.setText(mUserAddress.getProvinceName() + mUserAddress.getCityname() + mUserAddress.getDistrictName() + mUserAddress.getDetailAddress());
                                mOrderAdd.setAddressId(mUserAddress.getId());
                                break;
                            }
                            //没有默认地址，去选择地址
                            if (i == jsonArray.length() - 1) {
                                addressLayout.setVisibility(View.GONE);
                                addAddressLayout.setVisibility(View.VISIBLE);
                                addAddressText.setText("选择收货地址");
                            }
                        }
                        //没有地址，去添加地址
                        if (jsonArray.length() == 0) {
                            addAddressLayout.setVisibility(View.VISIBLE);
                            addressLayout.setVisibility(View.GONE);
                        }
                        //没有地址，去添加地址
                    } else if (Json_Utils.getCode(response) == 1007) {
                        addAddressLayout.setVisibility(View.VISIBLE);
                        addressLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /*
      *获取收货地址，用来检测选中的收货地址是否依然存在（不存在的话，显示选择收货地址界面）
      * 无参
       */
    private void getShipAddress1() {
        UserAddressTerm userAddressTerm = new UserAddressTerm(Login_Static.myUserID, -1, 0, 0);
        String data = mGson.toJson(userAddressTerm);//post请求  请求数据json格式化
        CLog.e("testing", "data=" + data);
        OKHttp.OkHttpPost("/User/GetAddressList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //访问错误回调（断网 或者服务器异常）
                CLog.e("testing", "获取收货地址列表失败" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取收货地址列表" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        addAddressLayout.setVisibility(View.GONE);
                        addressLayout.setVisibility(View.VISIBLE);

                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        Boolean IsTrue = false;//是否存在当前地址
                        for (int i = 0; i < jsonArray.length(); i++) {
                            mUserAddress = mGson.fromJson(jsonArray.getString(i), UserAddress.class);
                            if (mUserAddress.getId().equals(AddressId)) {
                                IsTrue = true;
                            }

                        }//若果不存在，按第一次进入的方式
                        if (!IsTrue) {
                            getShipAddress();
                        } else {
                            if (jsonArray.length() == 0) {
                                addAddressLayout.setVisibility(View.VISIBLE);
                                addressLayout.setVisibility(View.GONE);
                                addAddressText.setText("新建地址");
                            }
                        }
                    //没有地址，去添加地址
                    } else if (Json_Utils.getCode(response) == 1007) {
                        addAddressLayout.setVisibility(View.VISIBLE);
                        addressLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //界面操作提示
    public void showError(String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(BuyProduce_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                mDialog.dismiss();
                if (is == 1) {
                    Intent mIntent = new Intent(BuyProduce_Activity.this, My_OrderList_Activity.class);
                    startActivity(mIntent);
                    mSharedPreferences.Save_PREFS(BuyProduce_Activity.this, "idleFresh", "yes");
                    finish();
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //第一次进入界面，不调用getShipAddress1（)函数
        if (flag >= 1) {
            if (!userphone.getText().equals(""))
                getShipAddress1();
        } else flag++;

    }
}
