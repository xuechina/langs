package com.xuek.demo.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class ImMessageEntity implements Serializable{
	private static final long serialVersionUID = 132445352L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true,canBeNull=false,foreignAutoCreate=true)
	private UserEntity fromUser;
	@DatabaseField(foreign=true,canBeNull=false,foreignAutoCreate=true)
	private UserEntity toUser;
	@DatabaseField
	private String content;
	@DatabaseField
	private String sendTime;
	@DatabaseField
	private String messageStatus;
	@DatabaseField
	private String messageContentType;
	public UserEntity getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserEntity fromUser) {
		this.fromUser = fromUser;
	}
	public UserEntity getToUser() {
		return toUser;
	}
	public void setToUser(UserEntity toUser) {
		this.toUser = toUser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getMessageContentType() {
		return messageContentType;
	}
	public void setMessageContentType(String messageContentType) {
		this.messageContentType = messageContentType;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ImMessageEntity [id=" + id + ", fromUser=" + fromUser
				+ ", toUser=" + toUser + ", content=" + content + ", sendTime="
				+ sendTime + ", messageStatus=" + messageStatus
				+ ", messageContentType=" + messageContentType + "]";
	}
	
	
	

}
