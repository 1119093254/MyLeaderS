package com.lxx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxx.Dao.QiniuDao;
import com.lxx.Dao.ShowsAddDao;
import com.lxx.Dao.ShowsClassDao;
import com.lxx.Dao.ShowsDao;
import com.lxx.Dao.SwitchUserDao;
import com.lxx.Dao.UserAPIDao;
import com.lxx.Dao.UserBaseDao;
import com.lxx.Dao.UserDao;
import com.lxx.Dao.UserLoginDao;
import com.lxx.Dao.UserRegisterDao;
import com.lxx.Dao.UserTermDao;
import com.lxx.domain.ShowsAdd;

public class LeaderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LeaderServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//获取请求的类型
		int type = Integer.parseInt(request.getParameter("type"));
		String mData=request.getParameter("data");
		System.out.println(mData);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDao userDao = new UserDao();
		System.out.println("type="+type);
		if(type==1){//查询用户
			String userid=request.getParameter("userid");
			System.out.println("查询用户信息"+userid);
			String users=userDao.getUsers(userid);
			System.out.println(users);
			out.print(users);
		}else if(type==2){//登陆
			UserLoginDao userLoginDao=new UserLoginDao();
			System.out.println("登陆");
			String mPrimaryKey=userLoginDao.getPrimaryKey(mData);
			System.out.println(mPrimaryKey);
			out.print(mPrimaryKey);
		}else if (type==3) {//注册
			UserRegisterDao mUserRegisterDao=new UserRegisterDao();
			System.out.println("注册");
			System.out.println(mData);
			String mPrimaryKey=mUserRegisterDao.setUserRegister(mData);
			System.out.println(mPrimaryKey);
			out.print(mPrimaryKey);
		}else if (type==4) {
			//用UserTerm 获取userbase(宝宝列表)
			System.out.println("获取宝宝列表");
			UserTermDao mUserTermDao=new UserTermDao();
			String mUserBase= mUserTermDao.getUserbase(mData);
			System.out.println(mUserBase);
			out.print(mUserBase);
		}else if (type==5) {
			//增加宝宝
			UserBaseDao mUserBaseDao=new UserBaseDao();
			String mUserBase= mUserBaseDao.addUserbase(mData);
			System.out.println(mUserBase);
			out.print(mUserBase);
		}else if (type==6) {
			//切换身份
			String userid=request.getParameter("userid");
			SwitchUserDao mSwitchUserDao=new SwitchUserDao();
			String mUserBase= mSwitchUserDao.getUserPrimaryKey(userid);
			System.out.println(mUserBase);
			out.print(mUserBase);
		}else if (type==7) {
			//更改资料 未实现
			UserAPIDao mUserAPIDao=new UserAPIDao();
			String mUserAPIDaoString=mUserAPIDao.UpdataUser(mData);
			System.out.println(mUserAPIDaoString);
			out.print(mUserAPIDaoString);
		}else if (type==8) {
			//发布 Shows
			ShowsAddDao mShowsAddDao=new ShowsAddDao();
			String mAddResult=mShowsAddDao.addShows(mData);
			System.out.println(mAddResult);
			out.print(mAddResult);
		}else if (type==9) {
			//获取分类筛选
			ShowsClassDao mShowsClassDao=new ShowsClassDao();
			String mAddResult=mShowsClassDao.getShowsClass();
			System.out.println(mAddResult);
			out.print(mAddResult);
		}else if (type==10) {
			//发布 Shows
			ShowsDao mShowsDao=new ShowsDao();
			String mQiniuString= mShowsDao.getShows(mData);
			System.out.println(mQiniuString);
			out.print(mQiniuString);
		}else if (type==11){
			//用获取七牛云token
			QiniuDao mQiniuDao=new QiniuDao();
			String mQiniuString= mQiniuDao.getUpToken();
			System.out.println(mQiniuString);
			out.print(mQiniuString);
		}else if (type==12){
			//用获取七牛云token
			ShowsDao mShowsDao=new ShowsDao();
			String mQiniuString= mShowsDao.getShows(mData);
			System.out.println(mQiniuString);
			out.print(mQiniuString);
		}

		/******扩展请求类型******/
		 
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
