package com.xuek.demo.ormlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xuek.demo.entity.ImMessageEntity;
import com.xuek.demo.entity.UserEntity;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "orm.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<UserEntity, Integer> userDao;
	private Dao<ImMessageEntity, Integer> imMessageDao;
	public DBHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	public DBHelper(Context context){
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource	, UserEntity.class);
			TableUtils.createTable(connectionSource	, ImMessageEntity.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, ImMessageEntity.class, true);
			TableUtils.dropTable(connectionSource, UserEntity.class, true);
			onCreate(arg0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		super.close();
		imMessageDao = null;
		userDao = null;
	}
	public Dao<UserEntity, Integer> getUserDao() throws SQLException {
		if (null == userDao) {
			userDao = getDao(UserEntity.class);
		}
		return userDao;
	}

	public Dao<ImMessageEntity, Integer> getImMessageDao() throws SQLException {
		if (null == imMessageDao) {
			imMessageDao = getDao(ImMessageEntity.class);
		}
		return imMessageDao;
	}
	
	

}
