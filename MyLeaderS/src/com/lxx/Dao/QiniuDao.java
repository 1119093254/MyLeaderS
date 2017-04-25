package com.lxx.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.lxx.domain.ResultBean;
import com.lxx.domain.User;
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;
import com.qiniu.util.Auth;

/**
 * 
 * @author fanx
 *
 */
public class QiniuDao {

	/**
	 * 获取七牛云上传Token
	 * @return
	 */
	public String getUpToken() {
		ResultBean mResultBean;
		 String accessKey = "iusVsJk7uzQaKc-2_xGcbKgKNHjejXDbcEvdmCsF";
		    String secretKey = "UB_taPxqn2cUa--EBNNywY9qmwt13rb_gMlGOBbd";
		    String bucket = "mysd";
		    Auth auth = Auth.create(accessKey, secretKey);
		    String upToken = auth.uploadToken(bucket);
		    System.out.println(upToken);
		    Gson sGson = new Gson();
			mResultBean=new ResultBean(0, "OK", upToken);
			String mResultString=sGson.toJson(mResultBean);
		    return mResultString;
	}
}
