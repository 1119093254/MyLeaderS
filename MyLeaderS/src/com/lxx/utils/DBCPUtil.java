package com.lxx.utils;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 取得DBCP数据源
 * @author fanx
 *
 */
public class DBCPUtil {

	private static DataSource dataSource;
	//初始化数据源
	static{
		InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
		Properties pros =new  Properties();
		try {
			pros.load(in);
			dataSource= BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			 throw new ExceptionInInitializerError(e);
		}
	}
	
	//取得初始化datasource
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	//取得connection
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
}
