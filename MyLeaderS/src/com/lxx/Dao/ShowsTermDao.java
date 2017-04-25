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

/**
 * 
 * @author fanx
 *
 */
public class ShowsTermDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 取得所有用户
	 * @return
	 */
	public String getUsers(String userId) {
		List<UserTag> UserTag = new ArrayList<UserTag>();
		ResultBean mResultBean;
		User mUser=new User();
		 try {
			 UserTag = qr.query("select * from UserTag where UserId=?", new BeanListHandler<UserTag>(UserTag.class),userId);
			 mUser= qr.query("select * from User where UserId=?", new BeanHandler<User>(User.class),userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(UserTag!=null)
		mUser.setListUserTag(UserTag);
		Gson sGson = new Gson();
		//String musers=sGson.toJson(mUser);
		mResultBean=new ResultBean(0, "OK", sGson.toJson(mUser));
		String muser=sGson.toJson(mResultBean);
		return muser;
	}
}
