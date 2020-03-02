package com.bagevent.common;

/**
 * Created by zwj on 2017/5/31.
 */

public class Common {

    /**
     * 扫描登录
     */
    public static final String BARCODE_LOGIN = "barcode_login";//扫描登录

    //采集点登录
    public static final String COLLECT_INFO_KEY = "collect_info_key";
    public static final String COLLECT_LOGIN_TYPE = "collect_login_type";
    public static final String COLLECT_LOGIN_TYPE_BARCODE = "collect_login_type_barcode";//扫描登录
    public static final String COLLECT_LOGIN_TYPE_AUTOLOGIN = "collect_login_type_autologin";//自动登录
    public static final String COLLECT_LOGIN_TYPE_HOMEPAGE = "collect_login_type_homepage";//首页登录
    public static final String COLLECT_LOGIN_TYPE_MANAGER = "collect_login_type_manager";//管理页面登录

    public static final String COLLECT = "collect";//采集点
    public static final String COLLECT_MANAGER = "collect_manager";//通过采集点管理界面进入采集点详情界面
    public static final String COLLECT_CHILD = "collect_child";//通过帐号分配的采集点进入采集点详情界面

    //同步参会人员
    public static final String ATTENDEE_PAGE_SUCCESS = "attendSuccess";
    public static final String ATTENDEE_PAGE = "attendee_page";
    public static final String ORDER_PAGE = "order_page";
    public static final String SYNC_ORDER = "SYNC_SUCCESS";
    //同步活动
    public static final String SYNC_EVENT = "event_page";

    //提现
    public static final String WITHDRAW_APPLY = "14";

    //添加提现帐号成功

    public static final String ADD_ACCOUNT_SUCCESS = "13";

    //选择提现账户
    public static final String SELECT_ACCOUNT = "12";

    //同步单个订单xinx
    public static final String SINGLE_DATA = "11";

    //接收推送消息
    public static final String CHATTING_MESSAGE_RECEIVED_ACTION = "com.bagevent.MESSAGE_RECEIVED";

    //聊天列表页更新
    public static final String CURRENT_CHAT_LIST = "10";

    //推送消息
    public static final String PUSH_MESSAGE = "9";

    //同步聊天信息
    public static final String SYNC_CHAT_MESSAGE = "6";

    //发送聊天消息
    public static final String POST_MESSAGE = "8";

    //下拉加载
    public static final String UPFETCH_MESSAGE = "7";

    //首页同步数据成功
    public static final String HOME_SYNC_SUCCESS = "5";

    //同步信息成功
    public static final String SYNC_SUCCESS = "1";

    public static final String SYNC_INFO_SUCCESS = "2";

    //同步参会人员信息成功
    public static final String SYNC_ATTEND_INFO_SUCCESS = "15";

    //同步参会人员失败
    public static final String SYNC_ATTEND_INFO_FAIED = "16";


    public static final String SYNC_INFO_MORE = "20";


    //更换门票
    public static final String CHANGE_TICKET = "3";

    //退票
    public static final String QUIT_TICKET = "4";

    //审核参会人员
    public static final String AUDIT_ATTENDEE = "auditAttendee";

    //添加参会人员
    public static final String ADD_ATTENDEE_SUCCESS = "addSuccess";

    //修改参会人员
    public static final String MODIFY_ATTNEDEE_SUCCESS = "modifyAttendee";
    public static final String MODIFY_ATTENDEE_REFCODE_SUCCESS = "modifyAttRefcode";

    //签到成功
    public static final String CHECKIN_SUCCESS = "checkInSuccess";

    //修改订单信息成功
    public static final String MODIFY_ORDER = "modify_order_success";

    //修改发票成功
    public static final String MODIFY_INVOICE = "modify_invoice_success";

    //确认收款
    public static final String CONFIRM_ORDER = "confirmOrder";

    //更改备注
    public static final String UPDATE_NOTES = "updateNotes";

    //审核订单
    public static final String AUDIT_ORDER = "audit_order";

    public static final String SIGN_FAILED="sign_failed";
}
