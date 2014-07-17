package com.xuek.greendao;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xuek.greendao.dao.DaoMaster;
import com.xuek.greendao.dao.DaoMaster.DevOpenHelper;
import com.xuek.greendao.dao.DaoSession;
import com.xuek.greendao.dao.ImMessage;
import com.xuek.greendao.dao.ImMessageDao;
import com.xuek.greendao.dao.User;
import com.xuek.greendao.dao.UserDao;
import com.xuek.greendao.dao.UserDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

public class GreenDaoActivity extends Activity {
	
	public static final String TAG = "GreenDaoActivity";
	
	private DaoMaster mDaoMaster;
	private DaoSession mDaoSession;
	private ImMessageDao mImMessageDao;
	private UserDao mUserDao;

	String str1 = "{\"toUser\":{\"jid\":\"test1\",\"name\":\"aaa\"},\"fromUser\":{\"jid\":\"jltest\",\"name\":\"jl2\"},\"content\":\"Thug\",\"sendTime\":\"1398154532\",\"messageStatus\":\"unRead\",\"messageContentType\":\"TEXT\"}";
	/**
	 * notice: str2 just add extra id value for user.
	 */
	String str2 = "{\"toUser\":{\"jid\":\"test1\",\"name\":\"aaa\",id:4},\"fromUser\":{\"jid\":\"jltest\",\"name\":\"jl2\",id:5},\"content\":\"Thug\",\"sendTime\":\"1398154532\",\"messageStatus\":\"unRead\",\"messageContentType\":\"TEXT\"}";
	
	private ImMessage mImMessage;
	
	/**
	 * different from OrmLite, you can specify id property.
	 */
	private int index = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getDao();
//		generateDatas();
		addByJson(str1);
//		addByJson(str2);
		query();

	}
	
	public void getDao() {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dao.db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		mDaoMaster = new DaoMaster(db);
		mDaoSession = mDaoMaster.newSession();
		mImMessageDao = mDaoSession.getImMessageDao();
		mUserDao = mDaoSession.getUserDao();
	}
	
	
	public void generateDatas(){
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				User fromUser = new User();
				fromUser.setJid("fromjid_" + i);
				fromUser.setName("fromname_" + i);
//				fromUser.setId(Long.valueOf(index++));
				fromUser.setId(null);
				User toUser = new User();
				toUser.setJid("tojid_" + i);
				toUser.setName("toname_" + i);
				toUser.setId(null);
//				toUser.setId(Long.valueOf(index++));
				try {
//					mUserDao.insert(fromUser);
//					mUserDao.insert(toUser);
					mUserDao.insertWithoutSettingPk(fromUser);
					mUserDao.insertWithoutSettingPk(toUser);
					ImMessage imMessageEntity = new ImMessage();
					imMessageEntity.setContent("content_" + i);
					imMessageEntity.setMessageContentType("typt_" + i);
					imMessageEntity.setMessageStatus("status_" + i);
					imMessageEntity.setSendTime("time_" + i);
					imMessageEntity.setFromUser(fromUser);
					imMessageEntity.setToUser(toUser);
					mImMessageDao.insert(imMessageEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	
	/**
	 * notice : if using str1, it says NullPointerException. But str2 is perfect.
	 * @author xuechinahb@gmail.com
	 * May 1, 2014
	 * @param str
	 */
	public void addByJson(String str){
		mImMessage = JSON.parseObject(str, ImMessage.class);
		User from = mImMessage.getFromUser();
		User to = mImMessage.getToUser();
		log("fromUser == " + from);
		log("toUser == " + to);
		mUserDao.insert(mImMessage.getFromUser());
		mUserDao.insert(mImMessage.getToUser());
		mImMessageDao.insert(mImMessage);
	}
	/**
	 * sql: select * from IM_MESSAGE;
	 * @author xuechinahb@gmail.com
	 * May 1, 2014
	 */
	public void query(){
		QueryBuilder<ImMessage> queryBuilder = mImMessageDao.queryBuilder();
		List<ImMessage> msgs = queryBuilder.list();
		log(msgs);
		log(msgs.get(0));
		log(msgs.get(0).getFromUser());
		log(msgs.get(0).getToUser());
	}
	
	/**
	 * sql: select * from IM_MESSAGE  where from in (select id from USER group by jid) order by id desc; 
	 * @author xuechinahb@gmail.com
	 * May 1, 2014
	 */
	public void query_1(){
		QueryBuilder<ImMessage> messageQueryBuilder = mImMessageDao.queryBuilder();
		QueryBuilder<User> userQueryBuilder = mUserDao.queryBuilder();
		List<ImMessage> msgs = messageQueryBuilder.list();
		messageQueryBuilder.join(User.class, UserDao.Properties.Id);
//		messageQueryBuilder.where(com.xuek.greendao.dao.ImMessageDao.Properties.From.in(
//				mUserDao.queryRaw(where, selectionArg)
//				), null)
//		mImMessageDao.
		log(msgs);
		log(msgs.get(0));
		log(msgs.get(0).getFromUser());
		log(msgs.get(0).getToUser());
	}
	
	
	public void log(Object msg){
		Log.i(TAG, msg.toString());
	}
}
