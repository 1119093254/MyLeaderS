package com.lxx.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;









import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.lxx.domain.ResultBean;
import com.lxx.domain.ShowsClass;
import com.lxx.domain.User;
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;

/**
 * 
 * @author fanx
 *
 */
public class ShowsClassDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	/**
	 * 取得所有用户
	 * @return
	 */
	public String getShowsClass() {
		List<ShowsClass> mShowsClass =new ArrayList<ShowsClass>();
		ResultBean mResultBean;
		 try {
			 mShowsClass = qr.query("select * from ShowsClass where IsUse='1'", new BeanListHandler<ShowsClass>(ShowsClass.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Gson sGson = new Gson();
		//String musers=sGson.toJson(mUser);
		mResultBean=new ResultBean(0, "OK", sGson.toJson(mShowsClass));
		String muser=sGson.toJson(mResultBean);
		return muser;
	}
}
