package com.lxx.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;












import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.lxx.domain.ResultBean;
import com.lxx.domain.ShowsAdd;
import com.lxx.domain.User;
import com.lxx.domain.UserLogin;
import com.lxx.domain.UserPrimaryKey;
import com.lxx.domain.UserRegister;
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;
import com.lxx.utils.JsonUtils;
import com.lxx.utils.Time_Now;

/**
 * 
 * @author fanx
 *
 */
public class ShowsAddDao{
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	/**
	 * 登录获取 用户id  根据登录账号获取到id  根据id查找用户登录相关信息
	 * @return
	 */
	public String addShows(String mJsonString) {
		ResultBean mResultBean;
		int i=0;
		Gson sGson = new Gson();
		UserPrimaryKey mPrimaryKey = null;
		UserLogin userlogin;
		ShowsAdd beans = sGson.fromJson(mJsonString, ShowsAdd.class);
	
		UUID uuid  =  UUID.randomUUID(); 
		String Workid = beans.getUserId()+UUID.randomUUID().toString();//用来生成作品编号
		 try {
			 i=qr.update("insert into Shows(ShowsType,ShowsTypeName,Title,Infos,Amount,SerialNumber,IsSale,Price,ExpressFee,IsOver,Address,ShowsPhoto,PublishTime,PublishTimeStr,IsVideo,VideoName,SoType,UserId,ShowsClassId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					 beans.getShowsType(),beans.getSoType(),beans.getTitle(),beans.getInfos(),beans.getAmount(),Workid,beans.getIsSale(),beans.getPrice(),beans.getExpressFee(),beans.getIsOver(),beans.getAddress(),beans.getShowsPhoto(),Time_Now.getNowTime(),
					 Time_Now.getRongTime(),beans.getIsVideo(),beans.getVideoName(),beans.getSoType(),beans.getUserId(),beans.getShowsClassId());
			 System.out.print("i="+i);
		 } catch (SQLException e) {
			e.printStackTrace();
		}
		/* if(i==1){
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
		}*/
		 if(i==1){
			 mResultBean=new ResultBean(0, "OK", sGson.toJson(mPrimaryKey));
		 }else{
			 mResultBean=new ResultBean(1, "注册失败", sGson.toJson(mPrimaryKey));
		 }
		
		return sGson.toJson(mResultBean);
	}
}
