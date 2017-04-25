package com.multshows.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.multshows.Beans.ChatEvent_Model;
import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
    private IWXAPI api;
	ImageView mReturn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
		manageUtils.setSystemBarTintManage(WXPayEntryActivity.this,R.color.app_topColor);
		mReturn= (ImageView) findViewById(R.id.WeiXin_return);
		api = WXAPIFactory.createWXAPI(this,"wxad057bc461c628e2");
        api.handleIntent(getIntent(), this);
		mReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp baseResp) {
		if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
			Log.e("testing","onPayFinish,errCode="+baseResp.errCode);
			if(baseResp.errCode==0) {
				EventBus.getDefault().post(new ChatEvent_Model("", "ReChange"));
				finish();
			}else if(baseResp.errCode==-1){

			}else if(baseResp.errCode==-2){
				finish();
			}
		}
	}
}