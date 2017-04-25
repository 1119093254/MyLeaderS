package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.My_BabyInformation_Activity;
import com.multshows.Activity.Task_Show_Activity;
import com.multshows.Adapter.Task_MomList_Adapter;
import com.multshows.Beans.Task;
import com.multshows.Beans.TaskClass;
import com.multshows.Beans.TaskClassTag;
import com.multshows.Beans.TaskList_Beans;
import com.multshows.Beans.TaskTerm;
import com.multshows.Beans.Task_List_Bean;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：妈妈端 任务 frament
 * 作者：李萧萧
 * 时间：2016.9/27
 */
public class Home_Task_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {
    //上下文
    Context mContext;
    View view;
    @Bind(R.id.My_Fragment_lookTask_Layout)
    RelativeLayout mMyFragmentLookTaskLayout;//查看已发布的任务
    MyApplication myApplication ;
    RadioGroup mGroup;
    ListView mDIYList;//
    private ListView mAllTaskListview;
    private boolean init;//记录界面是否为第一次加载
    List<TaskClass> mList=new ArrayList<>();//任务列表bean
    List<Task_List_Bean> mBeanList=new ArrayList<>();//任务标签bean
    Task_MomList_Adapter momList_adapter;
    Gson mGson=new Gson();
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String vip="";
    Dialog_Hint hintText_dialog2;
    public static Home_Task_Fragment newInstance() {
        Home_Task_Fragment listFragment = new Home_Task_Fragment();
        return listFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hometask, container, false);

        mAllTaskListview = (ListView) view.findViewById(R.id.AllworkListview);
        init = true;//第一次加载
        ButterKnife.bind(this, view);
        myApplication= (MyApplication) getActivity().getApplication();
        initView(view);
        initData();
        initLister();
        return view;
    }

    /**
     * 初始化
     */
    private void initView(View v) {
        mDIYList= (ListView) v.findViewById(R.id.taskList_DIY);
        mGroup= (RadioGroup) v.findViewById(R.id.Completed);
    }

    /**
     * 数据处理
     */
    private void initData() {
        momList_adapter=new Task_MomList_Adapter(mContext,mBeanList);
        mDIYList.setAdapter(momList_adapter);
    }

    private void initLister() {
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < mBeanList.size() ; i++) {
                    if(mList.get(checkedId).getName().equals(mBeanList.get(i).getClassName())){
                        mDIYList.setSelection(i);
                        return;
                    }
                }
            }
        });


    }

    @Override
    public View getScrollableView() {
        return mAllTaskListview;
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (getUserVisibleHint()) {
            getdata();
        }
        getTaskList();
    }

    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
        if (init) {
            init = false;//第一次加载数据后，设置为false,不在加载数据
            getservciesData();
        }
    }

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据
        if (isVisibleToUser) {
            getdata();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.My_Fragment_lookTask_Layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.My_Fragment_lookTask_Layout:
                vip=mSharedPreferences.Get_PREFS(getActivity(),"Vip");
                if("yes".equals(vip)) {
                    hintText_dialog2 = new Dialog_Hint(mContext, 0,
                            "您还没有添加宝宝,是否前往添加?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hintText_dialog2.dismiss();
                            Intent intent = new Intent(mContext, My_BabyInformation_Activity.class);
                            intent.putExtra("Type", 4);
                            mContext.startActivity(intent);
                        }
                    });
                    hintText_dialog2.show();
                }else {
                    Intent mIntent = new Intent(getActivity(), Task_Show_Activity.class);
                    startActivity(mIntent);
                }
                break;
        }
    }

    private void getTaskList(){

        OkHttpUtils.get().url(myApplication.getUrl()+"/Task/ListTaskClassAll")
                .addParams("PageIndex","1")
                .addParams("PageSize","10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取任务列表失败："+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取任务列表："+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mList.clear();
                                mBeanList.clear();
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    TaskClass taskClass=mGson.fromJson(jsonArray.getString(i),TaskClass.class);
                                    if(taskClass.getType()==1) {
                                        mList.add(taskClass);
                                    }
                                }
                                dataDeal();
                                momList_adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    /**
     * 动态处理数据
     */
    private void dataDeal(){
        //动态添加RadioButton
        mGroup.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            RadioButton tempButton = new RadioButton(mContext);
            if (i == 0) {
                tempButton.setChecked(true);
            }
            tempButton.setButtonDrawable(null);           // 设置按钮的样式
            tempButton.setPadding(5, 30, 5, 30);                 // 设置文字距离按钮四周的距离
            tempButton.setBackgroundResource(R.drawable.radiobutton_back);
            tempButton.setTextColor(getResources().getColorStateList(R.color.radiobutton_text));
            tempButton.setId(i);
            tempButton.setGravity(Gravity.CENTER);
            tempButton.setText(mList.get(i).getName());
            mGroup.addView(tempButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            List<TaskClassTag> mList_=mList.get(i).getTaskTagList();
            Task_List_Bean bean;
            for (int j = 0; j <mList_.size() ; j++) {
                if(j==0){
                    bean=new Task_List_Bean(mList_.get(j),mList.get(i).getName(),0);
                }else {
                    bean=new Task_List_Bean(mList_.get(j),mList.get(i).getName(),1);
                }
                mBeanList.add(bean);
            }
        }
    }

}
