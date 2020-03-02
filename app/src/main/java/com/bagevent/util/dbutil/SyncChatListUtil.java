package com.bagevent.util.dbutil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Chat;
//import com.bagevent.db.Chat_Table;
import com.bagevent.db.Chat_Table;
import com.bagevent.db.EventTicket;
import com.bagevent.new_home.data.ChatData;
import com.bagevent.util.CompareRex;
import com.bagevent.util.PinYinUtils;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

/**
 * Created by ZWJ on 2017/11/22 0022.
 */

public class SyncChatListUtil {
    private Context mContext;
    private ChatData chatData;
    private long userId;
    private boolean isSingleChat = false;
    private boolean isSendEventBus = false;

    public SyncChatListUtil(Context mContext, ChatData data, boolean isSingleChat, boolean isSendEventBus) {
        this.mContext = mContext;
        this.chatData = data;
        this.isSingleChat = isSingleChat;
        this.isSendEventBus = isSendEventBus;
        this.userId = Long.parseLong(SharedPreferencesUtil.get(mContext, "userId", ""));
    }

    public void startSyncChatList() {
        SyncChatListThread thread = new SyncChatListThread();
        thread.start();
    }

    private class SyncChatListThread extends Thread {
        @Override
        public void run() {
            List<Chat> chatList = new ArrayList<Chat>();
            SQLite.update(Chat.class).set(Chat_Table.isRemove.is(false)).where(Chat_Table.userId.is(userId)).execute();
            for (int i = 0; i < chatData.getRespObject().size(); i++) {
                Chat chat = new Chat();
                ChatData.RespObjectBean data = chatData.getRespObject().get(i);
                boolean tempIsTop = false;
                Chat tempChat = new Select().from(Chat.class).where(Chat_Table.userId.is(userId)).and(Chat_Table.contactId.is(data.getContactId())).querySingle();
                if (tempChat != null) {
                    if (tempChat.isTop) {
                        tempIsTop = true;
                    } else {
                        tempIsTop = false;
                    }
                } else {
                    tempIsTop = false;
                }
                Delete.table(Chat.class, OperatorGroup.clause().and(Chat_Table.userId.is(userId)).and(Chat_Table.contactId.is(data.getContactId())));
                chat.userId = userId;
                chat.contactId = data.getContactId();
                if (!TextUtils.isEmpty(data.getInteractPeople().getShowName())) {
                    chat.showName = CompareRex.escapeCharacter(data.getInteractPeople().getShowName());
                }
                chat.sort = data.getSort();
                chat.createTime = TimeUtil.timeStmp(data.getCreateTime());
                if(!TextUtils.isEmpty(data.getLastMessage())) {
                    chat.updateTime = TimeUtil.timeStmp(data.getLastMessageTime());
                }else {
                    chat.updateTime = TimeUtil.timeStmp(data.getUpdateTime());
                }
                chat.unReadCount = data.getUnReadCount();
                chat.lastMessage = data.getLastMessage();
                chat.peopleId = data.getInteractPeople().getPeopleId();
                chat.token = data.getInteractPeople().getToken();
                chat.type = data.getInteractPeople().getType();
                chat.eventId = data.getInteractPeople().getEventId();
                chat.pinyinName = PinYinUtils.getPinYin(data.getInteractPeople().getShowName());
                chat.avatar = data.getInteractPeople().getAvatar();
                chat.organizer = data.getInteractPeople().isOrganizer();
                chat.sys = data.getInteractPeople().isSys();
                chat.isRemove = data.isStatus();
                chat.isTop = tempIsTop;
                chatList.add(chat);
            }

            setDataToDB(chatList);
        }
    }

    private void setDataToDB(List<Chat> list) {
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Chat>() {
                            @Override
                            public void processModel(Chat model, DatabaseWrapper wrapper) {
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
                        if (!isSingleChat) {
                            EventBus.getDefault().post(new MsgEvent(Common.HOME_SYNC_SUCCESS));
                        } else {
                            if (isSendEventBus) {
                                EventBus.getDefault().post(new MsgEvent(Common.CURRENT_CHAT_LIST));
                            }
                        }
                    }
                }).build().execute();
    }

}
