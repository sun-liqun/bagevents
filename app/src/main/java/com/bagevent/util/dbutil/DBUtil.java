package com.bagevent.util.dbutil;

import android.text.TextUtils;

import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.BackupCheckin;
//import com.bagevent.db.BackupCheckin_Table;
import com.bagevent.db.BackupCheckin_Table;
import com.bagevent.db.CollectChild;
//import com.bagevent.db.CollectChild_Table;
import com.bagevent.db.CollectChild_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by zwj on 2016/7/22.
 */
public class DBUtil {

    public static void addCheckinToBackup(Attends attendee) {
       // Log.e("fdf","from audit");
        BackupCheckin backupCheckin;
       // Log.e("fd",attendee.attendId +"F");
      //  Log.e("fd",attendee.refCodes +"aF");
        if(attendee.refCodes != null && !TextUtils.isEmpty(attendee.refCodes)) {
            backupCheckin = new Select().from(BackupCheckin.class).where(BackupCheckin_Table.eventId.is(attendee.eventId)).and(BackupCheckin_Table.refCodes.is(attendee.refCodes)).querySingle();
        }else {
            backupCheckin = new Select().from(BackupCheckin.class).where(BackupCheckin_Table.eventId.is(attendee.eventId)).and(BackupCheckin_Table.attendId.is(attendee.attendId)).querySingle();
        }
        if(backupCheckin == null) {
         //   Log.e("fdf","from auditaa");
            SQLite.insert(BackupCheckin.class)
                    .columns(BackupCheckin_Table.eventId,BackupCheckin_Table.attendId,BackupCheckin_Table.checkins,BackupCheckin_Table.checkinCodes,
                            BackupCheckin_Table.barcodes,BackupCheckin_Table.checkinTimes,BackupCheckin_Table.gsonUser,
                            BackupCheckin_Table.names,BackupCheckin_Table.payStatuss,BackupCheckin_Table.notes,BackupCheckin_Table.refCodes,BackupCheckin_Table.ticketIds)
                    .values(attendee.eventId,attendee.attendId,attendee.checkins,attendee.checkinCodes,attendee.barcodes,attendee.checkinTimes,attendee.gsonUser,
                            attendee.names,attendee.payStatuss,attendee.notes,attendee.refCodes,attendee.ticketIds)
                    .execute();
        }
    }



    /**
     * 更新签到数据--->attendId
     */
    public static void updateAttendId(int checkStatus, int checkEventId, int checkAttendId, String ref_code, String checkInTime, int ISSYNC) {
        if (!TextUtils.isEmpty(ref_code)) {//如果ref_code为空,则不更新数据库,否则会造成数据库中的签到数据错乱
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus),Attends_Table.checkinTimes.is(checkInTime),Attends_Table.isSync.is(ISSYNC))
                    .where(Attends_Table.eventId.is(checkEventId)).and(Attends_Table.refCodes.is(ref_code)).async().execute();

            SQLite.update(BackupCheckin.class).set(BackupCheckin_Table.checkins.is(checkStatus),
                    BackupCheckin_Table.checkinTimes.is(checkInTime))
                    .where(BackupCheckin_Table.eventId.is(checkEventId)).and(BackupCheckin_Table.refCodes.is(ref_code)).async().execute();
        } else {
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus),
                    Attends_Table.checkinTimes.is(checkInTime),
                    Attends_Table.isSync.is(ISSYNC))
                    .where(Attends_Table.eventId.is(checkEventId)).and(Attends_Table.attendId.is(checkAttendId)).async().execute();

            SQLite.update(BackupCheckin.class).set(BackupCheckin_Table.checkins.is(checkStatus),
                    BackupCheckin_Table.checkinTimes.is(checkInTime))
                    .where(BackupCheckin_Table.eventId.is(checkEventId)).and(BackupCheckin_Table.attendId.is(checkAttendId))
                    .async()
                    .execute();
        }
    }

    public static void updateAttendIdNoRefcode(int checkStatus, int checkEventId, int checkAttendId, String checkInTime, int ISSYNC) {
        SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus),
                Attends_Table.isSync.is(ISSYNC),
                Attends_Table.checkinTimes.is(checkInTime))
                .where(Attends_Table.eventId.is(checkEventId)).and(Attends_Table.attendId.is(checkAttendId)).async().execute();

        SQLite.update(BackupCheckin.class).set(BackupCheckin_Table.checkins.is(checkStatus),
               BackupCheckin_Table.checkinTimes.is(checkInTime))
                .where(BackupCheckin_Table.eventId.is(checkEventId)).and(BackupCheckin_Table.attendId.is(checkAttendId)).async().execute();
    }

    /**
     * 更新签到数据--->ref_code
     */

    public static void updateRef_code(int checkStatus, int checkEventId, String ref_code, String checkInTime, int ISSYNC) {
        SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkStatus),
               Attends_Table.isSync.is(ISSYNC),
                Attends_Table.checkinTimes.is(checkInTime))
                .where(Attends_Table.eventId.is(checkEventId)).and(Attends_Table.refCodes.is(ref_code)).async().execute();

        SQLite.update(BackupCheckin.class).set(BackupCheckin_Table.checkins.is(checkStatus),
                BackupCheckin_Table.checkinTimes.is(checkInTime))
                .where(BackupCheckin_Table.eventId.is(checkEventId)).and(BackupCheckin_Table.refCodes.is(ref_code)).async().execute();

    }


    /**
     * 审核
     */
    public static void updateAudit(int audit, int eventId, int attendId, String auditTime, int ISSYNC) {

        SQLite.update(Attends.class).set(Attends_Table.audits.is(audit),
               Attends_Table.auditSync.is(ISSYNC),
                Attends_Table.auditTimes.is(auditTime))
                .where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).async().execute();
    }

    /**
     * 添加参会人员
     */
    public static void updateAddPeople(int eventId, String map, String gsonUser, String ref_code, int ISSYNC) {

        SQLite.update(Attends.class).set(Attends_Table.gsonUser.is(gsonUser),
                Attends_Table.addMap.is(map),
                Attends_Table.addSync.is(ISSYNC))
                .where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).async().execute();
    }

    /**
     * 修改参会人员
     */
    public static void updateModify(int eventId, int attendId, String gsonUser, String modifyMap, boolean isContainPic, String imgPath, String ref_code, int ISSYNC) {

        if (!TextUtils.isEmpty(ref_code)) {
            /*if(isContainPic && !TextUtils.isEmpty(imgPath)) {
                SQLite.update(Attends.class).set(Attends_Table.imgPath.is(imgPath)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).execute();
            }*/

            SQLite.update(Attends.class).set(Attends_Table.modifyMap.is(modifyMap),
                    Attends_Table.gsonUser.is(gsonUser),
                   Attends_Table.modifyIsSync.is(ISSYNC))
                    .where(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).async().execute();

        } else {
            /*if(isContainPic && !TextUtils.isEmpty(imgPath)) {
                SQLite.update(Attends.class).set(Attends_Table.imgPath.is(imgPath)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).execute();
            }*/

            SQLite.update(Attends.class).set(Attends_Table.modifyMap.is(modifyMap),
                   Attends_Table.gsonUser.is(gsonUser),
                    Attends_Table.modifyIsSync.is(ISSYNC))
                    .where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendId)).async().execute();
        }
    }

    /**
     * 采集点人员
     */
    public static void collect(int eventId, int collectionId, String barcode, int ISSYNC) {
        SQLite.update(CollectChild.class).set(CollectChild_Table.collectIsSync.is(ISSYNC))
                .where(CollectChild_Table.eventId.is(eventId)).and(CollectChild_Table.collectionId.is(collectionId)).and(CollectChild_Table.attendeeBarcode.is(barcode)).async().execute();
    }

}
