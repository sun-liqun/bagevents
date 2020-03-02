package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.ChatMessage;
//import com.bagevent.db.ChatMessage_Table;
import com.bagevent.db.ChatMessage_Table;
import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by ZWJ on 2017/11/27 0027.
 */

public class SyncChatMessageUtil {
    private Context mContext;
    private ChatMessageData messageData;
    private long userId;
    private int contactId;
    private boolean isUpFetch = false;

    public SyncChatMessageUtil(Context mContext, ChatMessageData messageData,int contactId,boolean isUpFetch) {
        this.mContext = mContext;
        this.messageData = messageData;
        this.isUpFetch = isUpFetch;
        this.contactId = contactId;
        this.userId = Long.parseLong(SharedPreferencesUtil.get(mContext,"userId",""));
    }

    public void startSyncChatListMessage() {
        SyncChatMessageThread thread = new SyncChatMessageThread();
        thread.start();
    }

    public class SyncChatMessageThread extends Thread {
        @Override
        public void run() {
            List<ChatMessage> messagesList = new ArrayList<ChatMessage>();
//            Delete.table(ChatMessage.class);
            for (int i = 0;i < messageData.getRespObject().size(); i++) {
                ChatMessageData.RespObjectBean bean = messageData.getRespObject().get(i);
                Delete.table(ChatMessage.class, OperatorGroup.clause().and(ChatMessage_Table.userId.is(userId)).and(OperatorGroup.clause().and(ChatMessage_Table.chatId.is(bean.getChatId()))));
                ChatMessage message = new ChatMessage();
                message.contactId = contactId;
                message.userId = userId;
                message.chatId = bean.getChatId();
                message.sendTime = TimeUtil.timeStmp(bean.getSendTime());
                message.content = bean.getContent();
                message.status = bean.getStatus();
                message.senderToken = bean.getSenderToken();
                message.receiverToken = bean.getReceiverToken();
                message.eventId = bean.getEventId();
                message.attendeeId = bean.getAttendeeId();
                message.org = bean.isOrg();
                message.isSending = false;
                messagesList.add(message);
            }
            setDataToDB(messagesList);
        }
    }

    private void setDataToDB(List<ChatMessage> list) {
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<ChatMessage>() {
                            @Override
                            public void processModel(ChatMessage model, DatabaseWrapper wrapper) {
                                model.save();
                            }
                        }).addAll(list).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@Nonnull Transaction transaction, @Nonnull Throwable error) {
                        Log.e("list", "Database transaction failed.", error);
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@Nonnull Transaction transaction) {
                        if(isUpFetch) {
                            EventBus.getDefault().post(new MsgEvent(Common.UPFETCH_MESSAGE));
                        }else {
                            EventBus.getDefault().post(new MsgEvent(Common.SYNC_CHAT_MESSAGE));
                        }
                    }
                }).build().execute();
    }

}
