package com.xuek.greendao.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.xuek.greendao.dao.ImMessage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IM_MESSAGE.
*/
public class ImMessageDao extends AbstractDao<ImMessage, Long> {

    public static final String TABLENAME = "IM_MESSAGE";

    /**
     * Properties of entity ImMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property SendTime = new Property(2, String.class, "sendTime", false, "SEND_TIME");
        public final static Property MessageStatus = new Property(3, String.class, "messageStatus", false, "MESSAGE_STATUS");
        public final static Property MessageContentType = new Property(4, String.class, "messageContentType", false, "MESSAGE_CONTENT_TYPE");
        public final static Property From = new Property(5, Long.class, "from", false, "FROM");
        public final static Property To = new Property(6, Long.class, "to", false, "TO");
    };

    private DaoSession daoSession;


    public ImMessageDao(DaoConfig config) {
        super(config);
    }
    
    public ImMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IM_MESSAGE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'CONTENT' TEXT," + // 1: content
                "'SEND_TIME' TEXT," + // 2: sendTime
                "'MESSAGE_STATUS' TEXT," + // 3: messageStatus
                "'MESSAGE_CONTENT_TYPE' TEXT," + // 4: messageContentType
                "'FROM' INTEGER," + // 5: from
                "'TO' INTEGER);"); // 6: to
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IM_MESSAGE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ImMessage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
 
        String sendTime = entity.getSendTime();
        if (sendTime != null) {
            stmt.bindString(3, sendTime);
        }
 
        String messageStatus = entity.getMessageStatus();
        if (messageStatus != null) {
            stmt.bindString(4, messageStatus);
        }
 
        String messageContentType = entity.getMessageContentType();
        if (messageContentType != null) {
            stmt.bindString(5, messageContentType);
        }
 
        Long from = entity.getFrom();
        if (from != null) {
            stmt.bindLong(6, from);
        }
 
        Long to = entity.getTo();
        if (to != null) {
            stmt.bindLong(7, to);
        }
    }

    @Override
    protected void attachEntity(ImMessage entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ImMessage readEntity(Cursor cursor, int offset) {
        ImMessage entity = new ImMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sendTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // messageStatus
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // messageContentType
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // from
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // to
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImMessage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSendTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMessageStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMessageContentType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFrom(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setTo(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ImMessage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ImMessage entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM IM_MESSAGE T");
            builder.append(" LEFT JOIN USER T0 ON T.'FROM'=T0.'_id'");
            builder.append(" LEFT JOIN USER T1 ON T.'TO'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ImMessage loadCurrentDeep(Cursor cursor, boolean lock) {
        ImMessage entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User fromUser = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setFromUser(fromUser);
        offset += daoSession.getUserDao().getAllColumns().length;

        User toUser = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setToUser(toUser);

        return entity;    
    }

    public ImMessage loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ImMessage> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ImMessage> list = new ArrayList<ImMessage>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ImMessage> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ImMessage> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
