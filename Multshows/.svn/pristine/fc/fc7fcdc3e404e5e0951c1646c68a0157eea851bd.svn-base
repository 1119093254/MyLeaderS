package com.multshows.Activity;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.multshows.Adapter.Poi_Adapter;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.ChatEvent_Model2;
import com.multshows.Beans.MapEvent_Model;
import com.multshows.Beans.PoiItem_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 高德地图集成
 */
public class Map_Activity extends AppCompatActivity implements LocationSource{
    //地图控件
    private MapView mMapView;
    private ImageView mReturn;//返回
    RelativeLayout mParentsLayout;
    RelativeLayout mReturnLayout;
    TextView mTopTitle;//大标题
    private ListView mListView;//地址位置展示
    private RelativeLayout mSearchLayout;//搜索布局
    //实例化AMap
    private AMap mMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private AMapOptions mMapOptions;

   /* //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    AMapLocationClient mlocationClient = new AMapLocationClient(this);*/

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    //获取周边位置信息
    PoiSearch.Query query;
    PoiSearch poiSearch;

    //数据集合
    List<PoiItem_Beans> mPoiList=new ArrayList<>();//周边数据集合
    //适配器
    Poi_Adapter mPoiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Map_Activity.this,R.color.bg_topbar);
        Login_Static.mPlaceActivity.clear();
        Login_Static.mPlaceActivity.add(Map_Activity.this);
        initView();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        //初始化地图变量
        if (mMap == null) {
            mMap = mMapView.getMap();
        }
        mUiSettings = mMap.getUiSettings();//实例化UiSettings类
        //初始化定位
        initLocation();
        //设置定位监听  ,  周边
        initData();
        initListen();
        startLocation();
    }

    /**
     * 初始化
     */
    private void initView() {
        mMapView= (MapView) findViewById(R.id.Map_MapView);
        mReturn= (ImageView) findViewById(R.id.Map_return);
        mParentsLayout= (RelativeLayout) findViewById(R.id.Map_ParentsLayout);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Map_TopLayout);
        mTopTitle= (TextView) findViewById(R.id.Map_TopTitle);
        mListView= (ListView) findViewById(R.id.Map_ListView);
        mSearchLayout= (RelativeLayout) findViewById(R.id.Map_SearchLayout);
        //绑定适配器
        mPoiAdapter=new Poi_Adapter(Map_Activity.this,mPoiList);
        mListView.setAdapter(mPoiAdapter);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Map_Activity.this,mReturnLayout,mParentsLayout,mReturn,mTopTitle);
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

        query = new PoiSearch.Query("","汽车服务|汽车销售|" +
                "汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|" +
                "住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|" +
                "金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施");
        //第一个keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //第三个cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch = new PoiSearch(this, query);

    }
    /**
     * 数据处理
     */
    private void initData() {
        mMap.setLocationSource(this);// 设置定位监听
        mUiSettings.setMyLocationButtonEnabled(false); //隐藏默认的定位按钮
        mUiSettings.setZoomControlsEnabled(false);//隐藏放大缩小按钮
        mMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<mPoiList.size();i++){
                    mPoiList.get(i).setFlag(false);
                }
                mPoiList.get(position).setFlag(true);
                mPoiAdapter.notifyDataSetChanged();

                MapEvent_Model message=new MapEvent_Model();
                message.setIdle_Place(mPoiList.get(position));//mPoiList.get(position).getPoiTitle()
                EventBus.getDefault().post(message);
                finish();
            }
        });
        //搜索框
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Map_Activity.this,Search_Place_Activity.class);
                intent.putExtra("local", (Serializable) mPoiList);
                startActivity(intent);
            }
        });
    }
    /**
     * 周边监听
     */
    PoiSearch.OnPoiSearchListener mPoiSearchListener=new PoiSearch.OnPoiSearchListener() {
        @Override
        public void onPoiSearched(PoiResult poiResult, int i) {
            //PoiItem_Beans
                if (i== 1000) {
                    //成功
                        if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                            if (poiResult.getQuery().equals(query)) {// 是否是同一条
                                for (int n = 0; n < poiResult.getPois().size(); n++) {
                                    PoiItem_Beans bean = new PoiItem_Beans(poiResult.getPois().get(n).getPoiId(),
                                            poiResult.getPois().get(n).getTitle(), poiResult.getPois().get(n).getSnippet()
                                            , poiResult.getPois().get(n).getLatLonPoint().getLatitude(),
                                            poiResult.getPois().get(n).getLatLonPoint().getLongitude(),
                                            poiResult.getPois().get(n).getProvinceName(),
                                            poiResult.getPois().get(n).getCityName(),
                                            poiResult.getPois().get(n).getAdName(),
                                            poiResult.getPois().get(n).getDistance(), false);
                                    CLog.e("GD", bean.toString());
                                    mPoiList.add(bean);
                                }
                                mPoiAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(Map_Activity.this, "没有相关周边数据", Toast.LENGTH_SHORT).show();
                        }
                }else {
                    CLog.e("GD","获取周边失败");
                }
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            CLog.e("GD","设置定位监听");
            if (loc != null) {
                if (loc.getErrorCode() == 0) {
                    CLog.e("GD","设置定位监听成功"+"地址："+loc.getAddress()+","+loc.getCountry()+
                            loc.getProvince()+"省"+loc.getCity()+loc.getDistrict()+loc.getStreet()+
                    loc.getStreetNum()+"---"+loc.getAoiName()+"-----"+loc.getPoiName());
                    String address=loc.getProvince()+loc.getCity()+loc.getDistrict()+loc.getStreet()+
                            loc.getStreetNum();
                    PoiItem_Beans bean=new PoiItem_Beans();
                    bean.setPoiTitle(loc.getAoiName());//loc.getAoiName()+loc.getPoiName()
                    bean.setPoiSnippet(address);
                    bean.setLatitude(loc.getLatitude());
                    bean.setLongitude(loc.getLongitude());
                    bean.setPoiCity(loc.getCity());
                    bean.setPoiProvince(loc.getProvince());
                    bean.setPoiAdName(loc.getDistrict());
                    bean.setFlag(true);
                    mPoiList.add(bean);
                    //定位成功回调信息，设置相关消息
                    loc.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    double v=loc.getLatitude();//获取纬度
                    double y=loc.getLongitude();//获取经度
                    loc.getAccuracy();//获取精度信息
                    //mMapOptions.camera(CameraPosition.fromLatLngZoom(new LatLng(v,y),2));
                    changeCamera(
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    new LatLng(v,y), 18, 30, 0)));
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(v,y))
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(loc.getTime());
                    df.format(date);//定位时间
                    stopLocation();

                    //获取周边
                    poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(v,
                            y), 300));//设置周边搜索的中心点以及半径
                    poiSearch.setOnPoiSearchListener(mPoiSearchListener);
                    poiSearch.searchPOIAsyn();

                } else {
                    CLog.e("GD","设置定位监听失败");
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    CLog.e("AmapError","location Error, ErrCode:"
                            + loc.getErrorCode() + ", errInfo:"
                            + loc.getErrorInfo());
                }
            }
        }
    };
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        //mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setLocationCacheEnable(true);//启用缓存策略
        mOption.setNeedAddress(true);//返回地址信息
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        return mOption;
    }
    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }
    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }
    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        mMap.moveCamera(update);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        destroyLocation();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    //激活位置接口
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        CLog.e("GD","定位1");
    }
    //处理定位更新的接口
    @Override
    public void deactivate() {
        CLog.e("GD","定位222");
    }

}
