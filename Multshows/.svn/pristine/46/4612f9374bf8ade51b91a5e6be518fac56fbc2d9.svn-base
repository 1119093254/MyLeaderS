package com.multshows.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.multshows.Beans.My_Interests_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的兴趣标签适配器
 */
public class My_Interests_Adapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    List<List<My_Interests_Beans>> mList=new ArrayList<>();
    AddNotific mAddNotific;
    List<My_Interests_Beans> beansList_;
    List<My_Interests_Beans> beans;
    Dialog_Hint mDialogHint;
    Gson mGson=new Gson();
    Dialog mDialog;
    MyApplication myApplication;
    public interface AddNotific{
        void putMessage(boolean flag);
    }
    public My_Interests_Adapter(Context context, List<My_Interests_Beans> list) {
        mContext = context;
        beansList_ = list;
        mAddNotific= (AddNotific) context;
        mInflater=LayoutInflater.from(mContext);
        myApplication= (MyApplication) mContext.getApplicationContext();
        dataDispose();
        CLog.e("testing","SIZE:"+mList.size());
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class InterestViewHolder{
        CheckBox mCheckBox_1,mCheckBox_2,mCheckBox_3,mCheckBox_4,mCheckBox_5;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       /* final InterestViewHolder mViewHolder;
        if(view==null){
            mViewHolder=new InterestViewHolder();*/
            view=mInflater.inflate(R.layout.my_interests_items,null);
        CheckBox mCheckBox_1= (CheckBox) view.findViewById(R.id.my_Interests_items_checkbox1);
        CheckBox mCheckBox_2= (CheckBox) view.findViewById(R.id.my_Interests_items_checkbox2);
        CheckBox mCheckBox_3= (CheckBox) view.findViewById(R.id.my_Interests_items_checkbox3);
        CheckBox mCheckBox_4= (CheckBox) view.findViewById(R.id.my_Interests_items_checkbox4);
        CheckBox mCheckBox_5= (CheckBox) view.findViewById(R.id.my_Interests_items_checkbox5);
            //view.setTag(mViewHolder);
        /*}else {
            mViewHolder= (InterestViewHolder) view.getTag();
        }*/
        final List<My_Interests_Beans> beansList=mList.get(i);
        if(beansList.size()==1){
            mCheckBox_1.setText(beansList.get(0).getContent());
            mCheckBox_2.setVisibility(View.INVISIBLE);
            mCheckBox_3.setVisibility(View.INVISIBLE);
            mCheckBox_4.setVisibility(View.INVISIBLE);
            mCheckBox_5.setVisibility(View.INVISIBLE);
        }else if(beansList.size()==2){
            mCheckBox_1.setText(beansList.get(0).getContent());
            mCheckBox_2.setText(beansList.get(1).getContent());
            mCheckBox_3.setVisibility(View.INVISIBLE);
            mCheckBox_4.setVisibility(View.INVISIBLE);
           mCheckBox_5.setVisibility(View.INVISIBLE);
        }else if(beansList.size()==3){
           mCheckBox_1.setText(beansList.get(0).getContent());
            mCheckBox_2.setText(beansList.get(1).getContent());
            mCheckBox_3.setText(beansList.get(2).getContent());
            mCheckBox_4.setVisibility(View.INVISIBLE);
            mCheckBox_5.setVisibility(View.INVISIBLE);
        }else if(beansList.size()==4){
            mCheckBox_1.setText(beansList.get(0).getContent());
            mCheckBox_2.setText(beansList.get(1).getContent());
            mCheckBox_3.setText(beansList.get(2).getContent());
            mCheckBox_4.setText(beansList.get(3).getContent());
            mCheckBox_5.setVisibility(View.INVISIBLE);
        }else if(beansList.size()==5){
           mCheckBox_1.setText(beansList.get(0).getContent());
            mCheckBox_2.setText(beansList.get(1).getContent());
            mCheckBox_3.setText(beansList.get(2).getContent());
            mCheckBox_4.setText(beansList.get(3).getContent());
            mCheckBox_5.setText(beansList.get(4).getContent());
        }
        mCheckBox_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beansList.get(0).setFlag(b);
                getMessage();
                notifyDataSetChanged();
            }
        });
        mCheckBox_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beansList.get(1).setFlag(b);
                getMessage();
                notifyDataSetChanged();
            }
        });
        mCheckBox_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beansList.get(2).setFlag(b);
                getMessage();
                notifyDataSetChanged();
            }
        });
        mCheckBox_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beansList.get(3).setFlag(b);
                getMessage();
                notifyDataSetChanged();
            }
        });
        mCheckBox_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beansList.get(4).setFlag(b);
                getMessage();
                notifyDataSetChanged();
            }
        });
        //长按删除
        mCheckBox_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTag(beansList_.get(i*5+0).getId(),i*5+0);
                return false;
            }
        });
        mCheckBox_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTag(beansList_.get(i*5+1).getId(),i*5+1);
                return false;
            }
        });
        mCheckBox_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTag(beansList_.get(i*5+2).getId(),i*5+2);
                return false;
            }
        });
        mCheckBox_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTag(beansList_.get(i*5+3).getId(),i*5+3);
                return false;
            }
        });
        mCheckBox_5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTag(beansList_.get(i*5+4).getId(),i*5+4);
                return false;
            }
        });
        if(beansList.size()==1){
            mCheckBox_1.setChecked(beansList.get(0).isFlag());
        }else if(beansList.size()==2){
            mCheckBox_1.setChecked(beansList.get(0).isFlag());
            mCheckBox_2.setChecked(beansList.get(1).isFlag());
        }else if(beansList.size()==3){
            mCheckBox_1.setChecked(beansList.get(0).isFlag());
            mCheckBox_2.setChecked(beansList.get(1).isFlag());
            mCheckBox_3.setChecked(beansList.get(2).isFlag());
        }else if(beansList.size()==4){
            mCheckBox_1.setChecked(beansList.get(0).isFlag());
            mCheckBox_2.setChecked(beansList.get(1).isFlag());
            mCheckBox_3.setChecked(beansList.get(2).isFlag());
            mCheckBox_4.setChecked(beansList.get(3).isFlag());
        }else if(beansList.size()==5){
            mCheckBox_1.setChecked(beansList.get(0).isFlag());
            mCheckBox_2.setChecked(beansList.get(1).isFlag());
            mCheckBox_3.setChecked(beansList.get(2).isFlag());
            mCheckBox_4.setChecked(beansList.get(3).isFlag());
            mCheckBox_5.setChecked(beansList.get(4).isFlag());
        }


        return view;
    }
    public void getMessage(){
        for (int n=0;n<mList.size();n++){
            for (int m=0;m<mList.get(n).size();m++){
                if(mList.get(n).get(m).isFlag()){
                    mAddNotific.putMessage(true);
                    CLog.e("add","到这里了--1");
                    return;
                }
            }
            mAddNotific.putMessage(false);
            CLog.e("add","到这里了--2");
        }
    }


    /**
     * 数据整理
     */
    private void dataDispose(){
        mList.clear();
        beans= new ArrayList<>();
        for (int i=0;i<beansList_.size();i++){
            beans.add(beansList_.get(i));
            if(i%5==4){
                mList.add(beans);
                beans = new ArrayList<>();
            }else if(i==beansList_.size()-beansList_.size()%5){
                beans = new ArrayList<>();
                for(int j=beansList_.size()-beansList_.size()%5;j<beansList_.size();j++){
                    beans.add(beansList_.get(j));
                }
                mList.add(beans);
                beans = new ArrayList<>();
            }
        }
    }

    //删除兴趣标签
    private void deleteTag(final String id_, final int i){
        mDialogHint=new Dialog_Hint(mContext, 0, "是否确定删除该兴趣标签?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.hint_dialog_ok:
                        CLog.e("testing","删除兴趣标签id:"+id_);

                        OkHttpUtils.get().url(myApplication.getUrl()+"/User/DelTagDictionary")
                                .addParams("id",id_)
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        CLog.e("testing","删除兴趣标签失败"+e.toString());
                                        mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
                                        mDialog.show();
                                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,
                                                "网络异常","fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        CLog.e("testing","删除兴趣标签"+response);
                                        try {
                                            if(Json_Utils.getCode(response)==0){
                                                beansList_.remove(i);
                                                dataDispose();
                                                notifyDataSetChanged();
                                            }else {
                                                mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
                                                mDialog.show();
                                                HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,
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
                        mDialogHint.dismiss();
                        break;
                }
            }
        });
        mDialogHint.show();
    }


}
