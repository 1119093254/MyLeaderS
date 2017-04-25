package com.multshows.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.Kitting_Model;
import com.multshows.Fragment.My_Fragment_ok;
import com.multshows.Fragment.My_Kitting_Fragment;
import com.multshows.R;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 提现界面
 */
public class My_Kitting_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    //工具
    FragmentManager mFragmentManager;
    //fragment
    My_Kitting_Fragment mKittingFragment;//提现操作界面
    My_Fragment_ok mFragmentOk;//提现返回结果界面
    Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__kitting);
        EventBus.getDefault().register(My_Kitting_Activity.this);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Kitting_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Kitting_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Kitting_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Kitting_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_Kitting_TopTitle);
        mFragmentManager=getSupportFragmentManager();
        mKittingFragment=new My_Kitting_Fragment();

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Kitting_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        //绑定fragmnet
        mFragmentManager.beginTransaction().replace(R.id.My_Kitting_Fragment,mKittingFragment).commit();
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //强制隐藏键盘（如果此界面有获取焦点的控件 。如果配置文件不设置，返回（onDestroy）里尽量都要强制键盘隐藏）
                HideInputManager_Utils.hideInputManager(My_Kitting_Activity.this);
                finish();
            }
        });
    }


    /**
     * 时间总线 接收处理（相当于回调接口的作用）
     * @param model
     */
    @Subscribe
    public void Events(Kitting_Model model){
            mFragmentOk=new My_Fragment_ok();
            mBundle=new Bundle();
            mBundle.putString("message",model.getMessage());
            mFragmentOk.setArguments(mBundle);
            mFragmentManager.beginTransaction().replace(R.id.My_Kitting_Fragment,mFragmentOk).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(My_Kitting_Activity.this);
    }
}
