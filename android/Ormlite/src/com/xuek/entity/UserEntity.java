package com.xuek.entity;

import java.io.Serializable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 26495064L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String jid;
	@DatabaseField
	private String name;
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	@ForeignCollectionField(eager=true)
//	private ForeignCollection<ImMessageEntity> imMessageEntity;
//	public ForeignCollection<ImMessageEntity> getImMessageEntity() {
//		return imMessageEntity;
//	}
//	public void setImMessageEntity(
//			ForeignCollection<ImMessageEntity> imMessageEntity) {
//		this.imMessageEntity = imMessageEntity;
//	}
//	@Override
//	public String toString() {
//		return "UserEntity [id=" + id + ", jid=" + jid + ", name=" + name
//				+ ", imMessageEntity=" + imMessageEntity + "]";
//	}
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", jid=" + jid + ", name=" + name + "]";
	}
	
	
}
