package com.multshows.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.multshows.Beans.Place_Beans;
import com.multshows.Beans.DictAddress;
import com.multshows.R;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 描述：自定义popwindow，用于实现三级联动底部弹出框样式
 * 作者：贾强胜
 * 时间：2016.7.15
 */
public class MyPicPopupWindow_place extends PopupWindow {
    public static final String CITY_INFO = "City_Info";
    private View mMenuView;
    Context mContext;
    TextView btn_Cancel;//取消
    TextView btn_Save;//保存
    PickerView province_pv;
    PickerView city_pv;
    PickerView dis_pv;
    List<DictAddress> mProvince=new ArrayList<>();
    List<DictAddress> mCity=new ArrayList<>();
    List<DictAddress> mDistrict=new ArrayList<>();
    String province_id="",city_id="",district_id="";
    getProvinceAndCity mProvinceAndCity;
    MyApplication mMyApplication;
    //缓存

    Gson mGson=new Gson();

    public interface getProvinceAndCity{
        void setProvince(String Province);
        void setCity(String City);
        void setDistrict(String District);
        void setProvinceID(String province_id);
        void setCityID(String city_id);
        void setDistrictID(String district_id);
    }
    public MyPicPopupWindow_place(Activity context, final View.OnClickListener itemsOnClick,
                                  String province_ids, String city_ids, String district_ids) {
        super(context);
        mContext = context;
        mMyApplication= (MyApplication) context.getApplication();
        mProvinceAndCity= (getProvinceAndCity) context;
        //mSharedPreferences=Xutis.setCache(mContext);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.place_look_dialog, null);
        btn_Cancel = (TextView) mMenuView.findViewById(R.id.myPopupWindow_place_cancel);
        btn_Save = (TextView) mMenuView.findViewById(R.id.myPopupWindow_place_save);
        province_pv = (PickerView) mMenuView.findViewById(R.id.province_pv);
        city_pv = (PickerView) mMenuView.findViewById(R.id.city_pv);
        dis_pv = (PickerView) mMenuView.findViewById(R.id.dis_pv);

        province_id=province_ids;
        city_id=city_ids;
        district_id=district_ids;

        Log.e("testing",province_id+":"+city_id+":"+district_id);
        //取消按钮
        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //保存按钮
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);

            }
        });

        if(!"".equals(province_id) && !"".equals(city_id) && !"".equals(district_id)){
            getProvinceInfo();
            getCityInfo(province_id);
            getDistrictInfo(city_id);

        }else {
            getProvinceInfo();
        }
        /*mProvinceAndCity.setProvince("湖北省");
        mProvinceAndCity.setProvinceID("1LgcKyi");*/
        province_pv.setOnSelectListener(new PickerView.onSelectListener()
        {
            @Override
            public void onSelect(DictAddress text)
            {
                //Log.e("testing",text.toString());
                mProvinceAndCity.setProvince(text.getFullName());
                mProvinceAndCity.setProvinceID(text.getId());
                //获取市信息缓存

                //访问服务器
                getCityInfo(text.getId());
            }
        });

        //getCityInfo("1LgcKyi");
        /*mProvinceAndCity.setCity("荆门市");
        mProvinceAndCity.setCityID("1Lt6Syq");*/
        city_pv.setOnSelectListener(new PickerView.onSelectListener()
        {
            @Override
            public void onSelect(DictAddress text)
            {
                //Log.e("testing",text.toString());
                mProvinceAndCity.setCity(text.getFullName());
                mProvinceAndCity.setCityID(text.getId());
                getDistrictInfo(text.getId());
            }
        });
        //getCityInfo("1Lt6Syq");
        /*mProvinceAndCity.setDistrict("京山县");
        mProvinceAndCity.setDistrictID("1LtrVyt");*/
        dis_pv.setOnSelectListener(new PickerView.onSelectListener()
        {
            @Override
            public void onSelect(DictAddress text)
            {
                //Log.e("testing",text.toString());
                mProvinceAndCity.setDistrict(text.getFullName());
                mProvinceAndCity.setDistrictID(text.getId());
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.myPopupWindow_place).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    /**
     * 获取省信息
     */
    private void getProvinceInfo() {
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetListDicAddress")
                .addParams("provinceId","0")
                .addParams("cityId","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing","获取省信息失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing","获取省信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mProvince.clear();
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    DictAddress bean=mGson.fromJson(jsonArray.getString(i),DictAddress.class);
                                    mProvince.add(bean);
                                }
                                if("".equals(province_id)) {
                                    province_pv.setData(mProvince, "0");
                                }else {
                                    province_pv.setData(mProvince,province_id);
                                    province_id="";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 通过省id获取市信息
     * @param Pid
     */
    private void getCityInfo(String Pid) {
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetListDicAddress")
                .addParams("provinceId",Pid)
                .addParams("cityId","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing","获取市信息失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing","获取市信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mCity.clear();
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    DictAddress bean=mGson.fromJson(jsonArray.getString(i),DictAddress.class);
                                    mCity.add(bean);
                                }
                                if("".equals(city_id)) {
                                    city_pv.setData(mCity, "0");
                                }else {
                                    city_pv.setData(mCity,city_id);
                                    city_id="";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 通过市id获取区信息
     * @param Cid
     */
    private void getDistrictInfo(String Cid) {
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetListDicAddress")
                .addParams("provinceId","0")
                .addParams("cityId",Cid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing","获取区信息失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing","获取区信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mDistrict.clear();
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    DictAddress bean=mGson.fromJson(jsonArray.getString(i),DictAddress.class);
                                    mDistrict.add(bean);
                                }
                                if("".equals(district_id)) {
                                    dis_pv.setData(mDistrict, "0");
                                }else {
                                    dis_pv.setData(mDistrict,district_id);
                                    district_id="";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
