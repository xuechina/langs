package com.xuek.ormlite;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import com.xuek.entity.ImMessageEntity;
import com.xuek.entity.UserEntity;

public class OrmLiteActivity extends OrmLiteBaseActivity<DBHelper> {
	private static final String TAG = "OrmLiteActivity";
	String str = "{\"toUser\":{\"jid\":\"test1\",\"name\":\"aaa\"},\"fromUser\":{\"jid\":\"test2\",\"name\":\"bbb\"},\"content\":\"abcdefg\",\"sendTime\":\"1398154532\",\"messageStatus\":\"unRead\",\"messageContentType\":\"TEXT\"}";

	Dao<ImMessageEntity, Integer> mImMessageDao;
	Dao<UserEntity, Integer> mUserDao;
	/**
	 * if declared @DatabaseField(generatedId=true), not care id index. It is different from greenDAO.
	 */
	int index = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			mUserDao = getHelper().getUserDao();
			mImMessageDao = getHelper()
					.getImMessageDao();
			
//			createFromJson();
			generateDatas();
//			query_3();
			query_2();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void generateDatas(){
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				UserEntity fromUser = new UserEntity();
				fromUser.setJid("fromjid_" + i);
				fromUser.setName("fromname_" + i);
				fromUser.setId(index++);
				UserEntity toUser = new UserEntity();
				toUser.setJid("tojid_" + i);
				toUser.setName("toname_" + i);
				toUser.setId(index++);
				try {
					mUserDao.create(fromUser);
					mUserDao.create(toUser);
					ImMessageEntity imMessageEntity = new ImMessageEntity();
					imMessageEntity.setContent("content_" + i);
					imMessageEntity.setMessageContentType("typt_" + i);
					imMessageEntity.setMessageStatus("status_" + i);
					imMessageEntity.setSendTime("time_" + i);
					imMessageEntity.setFromUser(fromUser);
					imMessageEntity.setToUser(toUser);
					mImMessageDao.create(imMessageEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	public void createFromJson() {
		ImMessageEntity imMessageEntity = JSON.parseObject(str,ImMessageEntity.class);
		log("imMessageEntity.getFromUser() = " + imMessageEntity.getFromUser());
		try {
			mUserDao.create(imMessageEntity.getFromUser());
			mUserDao.create(imMessageEntity.getToUser());
			mImMessageDao.create(imMessageEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * sql: select * from immessageentity; 
	 * @author xuechinahb@gmail.com
	 * Apr 28, 2014
	 */
	public void query() {
		try {
			List<ImMessageEntity> list = mImMessageDao.queryForAll();
			ImMessageEntity msg = list.get(0);
			UserEntity from = msg.getFromUser();
			//手动刷新 if not declared "foreignAutoCreate=true", it will work. 
			mUserDao.refresh(from);
			UserEntity to = msg.getToUser();
			//手动刷新 if not declared "foreignAutoCreate=true", it will work. 
			mUserDao.refresh(to);
			log("msg = " + msg);
			log("fromuser == " + from);
			log("touser == " + to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 查询
	 * sql: select * from immessageentity  where fromUser_id in (select id from userentity group by jid) order by id desc; 
	 * @author xuechinahb@gmail.com
	 * Apr 28, 2014
	 */
	public void query_1(){
		try {
			QueryBuilder<ImMessageEntity, Integer> msgQuery = mImMessageDao.queryBuilder();
			QueryBuilder<UserEntity, Integer> userQuery = mUserDao.queryBuilder();
			msgQuery.where().in("fromUser_id", userQuery.selectColumns("id").groupBy("jid"));
			msgQuery.orderBy("id", false);
			List<ImMessageEntity> queryDatas = msgQuery.query();
			UserEntity fromUser= queryDatas.get(0).getFromUser();
			//手动刷新 if not declared "foreignAutoCreate=true", it will work. 
			mUserDao.refresh(fromUser);
			log("fromUser == " + fromUser);
			log("queryDatas == " + queryDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sql: select * from userentity order by id desc limit 10 offset 0;
	 * @author xuechinahb@gmail.com
	 * Apr 28, 2014
	 */
	public void query_2(){
		try {
			QueryBuilder<UserEntity, Integer> userQuery = mUserDao.queryBuilder();
			userQuery.orderBy("id", false).limit(10L).offset(0L);
			List<UserEntity> users = userQuery.query();
			log("users == " + users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * query user's message, include sent message and received message.
	 * 查询某个用户的消息（包括其发送或接收的）
	 * sql: select * from immessageentity where (fromUser_id in (select id from userentity where jid = "fromjid_1")) or 
	 * 		(toUser_id in (select id from userentity where jid = "tojid_1"));
	 * @author xuechinahb@gmail.com
	 * Apr 28, 2014
	 */
	@SuppressWarnings("unchecked")
	public void query_3(){
		try {
			QueryBuilder<ImMessageEntity, Integer> msgQuery = mImMessageDao.queryBuilder();
			QueryBuilder<UserEntity, Integer> fromUserQuery = mUserDao.queryBuilder();
			QueryBuilder<UserEntity, Integer> toUserQuery = mUserDao.queryBuilder();
			
			fromUserQuery.selectColumns("id").where().eq("jid", "fromjid_1");
			toUserQuery.selectColumns("id").where().eq("jid", "tojid_1");
			
			Where<ImMessageEntity, Integer> imMessageWhere = msgQuery.where();
			imMessageWhere.or(imMessageWhere.in("fromUser_id", fromUserQuery), imMessageWhere.in("toUser_id", toUserQuery));
			//下面的方式会抛异常 below code not work
//			imMessageWhere.or(imMessageWhere.in("fromUser_id", userQuery.selectColumns("id").where().eq("jid", "fromjid_1")), 
//					imMessageWhere.in("toUser_id", userQuery.selectColumns("id").where().eq("jid", "tojid_1")));
			//add order, limit, offset 
//			msgQuery.orderBy("id", false).limit(2L).offset(0L);
			List<ImMessageEntity> datas = msgQuery.query();
			for(ImMessageEntity entity : datas){
				UserEntity fromUser = entity.getFromUser();
				mUserDao.refresh(fromUser);
				UserEntity toUser = entity.getToUser();
				mUserDao.refresh(toUser);
			}
			log("query_3----datas = " + datas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void log(String msg){
		Log.i(TAG, msg);
	}
}
