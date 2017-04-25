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
public class UserBaseDao{
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 登录获取 用户id  根据登录账号获取到id  根据id查找用户登录相关信息
	 * @return
	 */
	public String addUserbase(String mJsonString) {
		Gson sGson = new Gson();
		int i=0;

		UserBase beans = sGson.fromJson(mJsonString, UserBase.class);
		ResultBean mResultBean=null; ;//返回的bean对象
		UserPrimaryKey mPrimaryKey = null;
		UserLogin userlogin;
		 try {
			 userlogin= qr.query("select * from userlogin where Account=? and UserType=?", new BeanHandler<UserLogin>(UserLogin.class),beans.getAccountId(),1);
			 i=qr.update("insert into userlogin(Account,pwd,UserType) values (?,?,?)", beans.getAccountId(),beans.getAccountId(),1);
			 System.out.print("i="+i);
		 } catch (SQLException e) {
			e.printStackTrace();
		}
		 if(i==1){
			 try {
				 List<UserLogin> mLogins=qr.query("select * from userlogin where Account=? and UserType=?", new BeanListHandler<UserLogin>(UserLogin.class),beans.getAccountId(),1);
				 userlogin=mLogins.get(mLogins.size()-1);
				 i=i+qr.update("insert into UserPrimaryKey(userid,AccountId,IsMobile,IsBind,Type) values (?,?,?,?,?)", userlogin.getLoginID(), beans.getAccountId(),beans.getAccountId(),0,3);
				 System.out.print("i="+i);
				 i=i+qr.update("insert into UserBase(UserId,NickName,Portrait,IsConfirm,State,UserType,ParentId,PopularizeId,AccountId,Mobile) values (?,?,?,?,?,?,?,?,?,?)", userlogin.getLoginID(), beans.getNickName(),"cU7XFfcUL.png",0,1,1,beans.getParentId(),beans.getAccountId(),beans.getAccountId(),beans.getAccountId());
				 System.out.print("i="+i);
				 i=i+qr.update("insert into user(UserId,NickName,Portrait,IsConfirm,State,UserType,ParentId,Mobile,PopularizeId,AccountId,LoginName) values (?,?,?,?,?,?,?,?,?,?,?)", userlogin.getLoginID(), beans.getNickName(),"cU7XFfcUL.png",0,1,1,beans.getParentId(),beans.getAccountId(),beans.getAccountId(),beans.getAccountId(),beans.getAccountId());
				 System.out.print("i="+i);
				 mPrimaryKey= qr.query("select * from UserPrimaryKey where Userid=?", new BeanHandler<UserPrimaryKey>(UserPrimaryKey.class),userlogin.getLoginID());
				 mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(i>=2){
					mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
				
				}else{
					mResultBean=new ResultBean(1, "添加失败", sGson.toJson(mPrimaryKey));
				}
				
			}
		}else{
			mResultBean=new ResultBean(1, "添加失败", sGson.toJson(mPrimaryKey));
		}
		String mprimaryKey=sGson.toJson(mResultBean);
		return mprimaryKey;
	}
}
