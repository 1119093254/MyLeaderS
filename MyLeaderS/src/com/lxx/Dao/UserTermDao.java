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
import com.lxx.domain.UserBase;
import com.lxx.domain.UserLogin;
import com.lxx.domain.UserPrimaryKey;
import com.lxx.domain.UserTerm;
import com.lxx.utils.DBCPUtil;
import com.lxx.utils.JsonUtils;

/**
 * 
 * @author 请求宝宝列表
 */
public class UserTermDao{
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 登录获取 用户id  根据登录账号获取到id  根据id查找用户登录相关信息
	 * @return
	 */
	public String getUserbase(String mJsonString) {
		Gson sGson = new Gson();
		UserTerm beans = sGson.fromJson(mJsonString, UserTerm.class);
		List<UserBase> users = new ArrayList<UserBase>();
		ResultBean mResultBean=null; ;//返回的bean对象
		 try {
			 if(beans.getUserId()!=null){
				 users = qr.query("select * from UserBase where ParentId = ? and UserType='1'", new BeanListHandler<UserBase>(UserBase.class),beans.getUserId());
			 }else{
				 users = qr.query("select * from UserBase where Mobile = ?", new BeanListHandler<UserBase>(UserBase.class),beans.getMobile());
			 }
			
			if(users!=null){
				mResultBean=new ResultBean(0, "OK", sGson.toJson(users));
			}else {
				mResultBean=new ResultBean(1007, "OK", sGson.toJson(users));
			} 
		 } catch (SQLException e) {
			e.printStackTrace();
			mResultBean=new ResultBean(1, "异常错误", sGson.toJson(users));
		}
		String mprimaryKey=sGson.toJson(mResultBean);
		return mprimaryKey;
	}
}
