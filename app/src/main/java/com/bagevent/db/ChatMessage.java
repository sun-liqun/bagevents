package com.bagevent.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ZWJ on 2017/11/27 0027.
 */
@Table(database = AppDatabase.class)
public class ChatMessage extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;
    @Column
    public long userId;
    @Column
    public int chatId;
    @Column
    public int contactId;
    @Column
    public int peopleId;
    @Column
    public Long sendTime;
    @Column
    public String content;
    @Column
    public int status;
    @Column
    public String senderToken;
    @Column
    public String receiverToken;
    @Column
    public String eventId;
    @Column
    public String attendeeId;
    @Column
    public boolean org;
    @Column
    public int sendSeconds;
    @Column
    public boolean todaySend;
    @Column
    public String sendDay;
    @Column
    public boolean isSending;
}
