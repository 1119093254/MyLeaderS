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
import com.lxx.domain.UserLogin;
import com.lxx.domain.UserPrimaryKey;
import com.lxx.utils.DBCPUtil;
import com.lxx.utils.JsonUtils;

/**
 * 
 * @author fanx
 *
 */
public class UserLoginDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 登录获取 用户id  根据登录账号获取到id  根据id查找用户登录相关信息
	 * @return
	 */
	public String getPrimaryKey(String mJsonString) {
		Gson sGson = new Gson();
		UserPrimaryKey mPrimaryKey=new UserPrimaryKey();
		UserLogin beans = sGson.fromJson(mJsonString, UserLogin.class);
		UserLogin users = new UserLogin();
		ResultBean mResultBean=null; ;//返回的bean对象
		 try {
			users = qr.query("select * from UserLogin where Account = ? and UserType=?", new BeanHandler<UserLogin>(UserLogin.class),beans.getAccount(),0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 if(users.getPwd().equals(beans.getPwd())){
			 try {
				 String mmusers=sGson.toJson(users);
				
				mPrimaryKey= qr.query("select * from UserPrimaryKey where UserId = ?", new BeanHandler<UserPrimaryKey>(UserPrimaryKey.class),users.getLoginID());
				mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mResultBean=new ResultBean(-1, "异常错误", null);
			}
		 }else{
			 mResultBean=new ResultBean(-1, "密码错误", null);
		 }
		String mprimaryKey=sGson.toJson(mResultBean);
		return mprimaryKey;
	}
}
