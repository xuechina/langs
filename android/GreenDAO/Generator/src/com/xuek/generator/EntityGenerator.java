package com.xuek.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class EntityGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.xuek.greendao.dao");
        schema.enableKeepSectionsByDefault();

        addEntity(schema);
        new DaoGenerator().generateAll(schema, "../GreenDAO/src");
    }


    private static void addEntity(Schema schema) {
    	Entity user = schema.addEntity("User");
//    	user.implementsSerializable();
    	user.addIdProperty().autoincrement().primaryKey();
    	user.addStringProperty("jid");
    	user.addStringProperty("name");
    	
    	Entity msg = schema.addEntity("ImMessage");
//    	msg.implementsSerializable();
    	msg.addIdProperty().autoincrement();
    	msg.addStringProperty("content");
    	msg.addStringProperty("sendTime");
    	msg.addStringProperty("messageStatus");
    	msg.addStringProperty("messageContentType");
    	Property fromUser = msg.addLongProperty("from").getProperty();
    	Property toUser = msg.addLongProperty("to").getProperty();
    	
    	msg.addToOne(user, fromUser, "fromUser");
    	msg.addToOne(user, toUser, "toUser");
    }

}
