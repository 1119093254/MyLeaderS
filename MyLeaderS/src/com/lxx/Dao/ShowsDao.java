package com.lxx.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.lxx.domain.ResultBean;
import com.lxx.domain.Shows;
import com.lxx.domain.ShowsClass;
import com.lxx.domain.ShowsClassBase;
import com.lxx.domain.User;
import com.lxx.domain.UserAPI;
import com.lxx.domain.UserBase;
import com.lxx.domain.UserHead;
import com.lxx.domain.UserTag;
import com.lxx.utils.DBCPUtil;
import com.qiniu.util.Auth;

/**
 * 
 * @author fanx
 *
 */
public class ShowsDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	/**
	 * 获取作品列表
	 * @return
	 */
	public String getShows(String mJsonString) {
		Gson mGson = new Gson();
		List<Shows> mShowList=new ArrayList<Shows>();
		ShowsTerm beans = mGson.fromJson(mJsonString,ShowsTerm.class);
		try {
			mShowList= qr.query("select * from Shows ORDER BY PublishTime DESC limit ?,?", new BeanListHandler<Shows>(Shows.class),(beans.getPageIndex()-1)*beans.getPageSize(),beans.getPageIndex()*beans.getPageSize());
			for (int i = 0; i < mShowList.size(); i++) {
				Shows mShowsTemp=mShowList.get(i);
				ShowsClass mShowsClass= qr.query("select * from ShowsClass where Id=?", new BeanHandler<ShowsClass>(ShowsClass.class),mShowsTemp.getShowsClassId());
				System.out.println(mShowsClass.getClassName());
				ShowsClassBase mShowsClassBase=new ShowsClassBase(mShowsClass.getId(), mShowsClass.getClassName());
				mShowList.get(i).setShowsClass(mShowsClassBase);
				mShowList.get(i).setUser(getUserHead(mShowsTemp.getUserId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultBean mResultBean=new ResultBean(0, "Ok", mShowList);
		String mResultString=mGson.toJson(mResultBean);
		return mResultString;
	}
	public UserHead getUserHead(String userid) {
		UserHead mUserHead=new UserHead();
		try {
			UserBase mUserBase= qr.query("select * from UserBase where UserId=?", new BeanHandler<UserBase>(UserBase.class),userid);
			mUserHead.setNickName(mUserBase.getNickName());
			mUserHead.setPortrait(mUserBase.getPortrait());
			mUserHead.setUserId(mUserBase.getUserId());
			mUserHead.setUserType(mUserBase.getUserType());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mUserHead;
		
	}
}
