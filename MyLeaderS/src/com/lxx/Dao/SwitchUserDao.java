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
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;

/**
 * 
 * @author fanx
 *
 */
public class SwitchUserDao{
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 切换用户 根据用户id获取用户UserPrimaryKey信息
	 * @return
	 */
	public String getUserPrimaryKey(String usetId) {
		UserPrimaryKey mPrimaryKey=new UserPrimaryKey();
		ResultBean mResultBean;
		 try {
			 mPrimaryKey= qr.query("select * from UserPrimaryKey where UserId = ?", new BeanHandler<UserPrimaryKey>(UserPrimaryKey.class),usetId);
		 } catch (SQLException e) {
			e.printStackTrace();
		}
		Gson sGson = new Gson();
		mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
		String muser=sGson.toJson(mResultBean);
		return muser;
	}
}
