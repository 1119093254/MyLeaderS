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
import com.lxx.domain.UserRegister;
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;
import com.lxx.utils.JsonUtils;

/**
 * 
 * @author fanx
 *
 */
public class UserRegisterDao{
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	/**
	 * 登录获取 用户id  根据登录账号获取到id  根据id查找用户登录相关信息
	 * @return
	 */
	public String setUserRegister(String mJsonString) {
		ResultBean mResultBean;
		int i=0;
		Gson sGson = new Gson();
		UserPrimaryKey mPrimaryKey = null;
		UserLogin userlogin;
		UserRegister beans = sGson.fromJson(mJsonString, UserRegister.class);
		 try {
			 i=qr.update("insert into userlogin(Account,pwd) values (?,?)", beans.getAccount(),beans.getPwd());
			 System.out.print("i="+i);
		 } catch (SQLException e) {
			e.printStackTrace();
		}
		 if(i==1){
			 try {
				userlogin= qr.query("select * from userlogin where Account=?", new BeanHandler<UserLogin>(UserLogin.class), beans.getAccount());
				 i=i+qr.update("insert into UserPrimaryKey(userid,AccountId,IsMobile,IsBind,Type) values (?,?,?,?,?)", userlogin.getLoginID(), beans.getAccount(),beans.getAccount(),beans.getType(),beans.getAccountType());
				 System.out.print("i="+i);
				 i=i+qr.update("insert into UserBase(UserId,NickName,Portrait,IsConfirm,State,UserType,ParentId,PopularizeId,AccountId,Mobile) values (?,?,?,?,?,?,?,?,?,?)", userlogin.getLoginID(), beans.getNickName(),"cU7XFfcUL.png",0,1,0,userlogin.getLoginID(),beans.getAccount(),beans.getAccount(),beans.getAccount());
				 System.out.print("i="+i);
				 i=i+qr.update("insert into user(UserId,NickName,Portrait,IsConfirm,State,UserType,ParentId,Mobile,PopularizeId,AccountId,LoginName) values (?,?,?,?,?,?,?,?,?,?,?)", userlogin.getLoginID(), beans.getNickName(),"cU7XFfcUL.png",0,1,0,userlogin.getLoginID(),beans.getAccount(),beans.getExCode(),beans.getAccount(),beans.getAccount());
				 System.out.print("i="+i);
				 mPrimaryKey= qr.query("select * from UserPrimaryKey where Userid=?", new BeanHandler<UserPrimaryKey>(UserPrimaryKey.class),userlogin.getLoginID());
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 if(i==1){
			 mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
		 }else{
			 mResultBean=new ResultBean(1, "注册失败", sGson.toJson(mPrimaryKey));
		 }
		
		return sGson.toJson(mResultBean);
	}
}
