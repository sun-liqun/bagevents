package com.bagevent.common;

/**
 * Created by zwj on 2016/5/25.
 */
public class Constants {


    /**
     * Face++
     */
    public static String FACE_KEY = "8GjhbRwVxq16nGu2WAmJ4NcQsdHYOBUh";
    public static String FACE_SECRET = "QiU57afvQbvTB-aK866K6dZMNKC_wPED";


    /**
     * 微信
     */
    public static String WX_APPID = "wx88a7404458473ec0";
    public static String WX_SECRET = "d0d529f0fa54ffc6016cceb7b05715af";
    public static String WX_PARTNERID = "1489827162";
    public static String WX_KEY = "17vd1k0clp75dmf817eu2dvkae0ts94a";

    /**
     * 强制升级
     */
    public static int FORCE_UPDATE = 1;
    public static int NOT_FORCE_UPDATE = 0;


    /**
     * 状态栏透明度
     */
    public static int STATUS_ALPHA = 150;

    /**
     * 采集点人员是否同步
     */
    public static int COLLECT_SYNV = 0;
    public static int COLLECT_NOT_SYNV = 1;
    public static int COLLECT_NOT_ATTENDEE = -1;

    /**
     * 审核人员是否同步
     */
    public static int AUDIT_SYNC = 0;
    public static int AUDIT_NOT_SYNC = 1;

    /**
     * 添加参会人员是否同步
     */
    public static int ADD_SYNC = 0;
    public static int ADD_NOT_SYNC = 1;

    /**
     * 签到状态是否同步
     */

    public static int SYNC = 0;
    public static int NOTSYNC = 1;

    /**
     * 修改用户是否同步
     */
    public static int MODIFY_SYNC = 0;
    public static int MODIFY_NOT_SYNC = 1;

    /**
     * 用户姓名
     */
    public static String USERNAME = "80";

    /**
     * 保存的文件名
     */
    public static final String FORM_FILE_NAME = "Form_offline_cache";
    public static final String BADGE_FORM_FILE_NAME = "Badge_form_offline_cache";

//    public static final String URL = "http://192.168.3.3:9988/api/";
    //  public static final String URL = "http://b8itkz.natappfree.cc/api/";
//    public static final String URL = "http://mxb.s1.natapp.cc/api/v1/";
//     public static final String URL = "http://bzp2zp.natappfree.cc/api/";

//    public static final String URL = "http://123.57.41.96:9001/api/v1/";
//    public static final String NEW_URL = "http://123.57.41.96:9001/api/v2/";

//    public static final String URL = "http://antsitya.mynatapp.cc/api/v1/";
//    public static final String NEW_URL = "http://antsitya.mynatapp.cc/api/v2/";

    public static final String URL = "https://www.bagevent.com/api/v1/";
    public static final String NEW_URL = "https://www.bagevent.com/api/v2/";

//    public static final String URL = "http://192.168.3.194:8080/api/v1/";
//    public static final String NEW_URL = "http://192.168.3.194:8080/api/v2/";

//    public static final String URL = "https://tanlangdark.mynatapp.cc/api/v1/";
//    public static final String URL = "http://245346m5e0.wicp.vip/api/v1/";


//    public static final String NEW_URL = "https://tanlangdark.mynatapp.cc/api/v2/";
//    public static final String NEW_URL = "http://245346m5e0.wicp.vip/api/v2/";


//    public static final String NEW_URL = "http://hexiaofeng.mynatapp.cc/api/v2";

    public static final String LOGINSOURCE = "loginSource=4";
    public static final String LOGINTYPE = "&loginType=0";
    public static final String ACCESS_TOKEN = "&access_token=ipad";
    public static final String ACCESS_TOKENS = "?access_token=ipad";
    public static final String ACCESS_SERCRET = "&access_secret=ipad_secret";
    public static final String SOURCE = "source=0";//注册短信
    public static final String REQUIRE_SOURCE = "source=3";//发布需求短信
    public static final String USER_TOKEN = "User-token";

    /**
     * 图片url
     */
    public static final String imgsURL = "https://img.bagevent.com";

    //123服务器图片url
//    public static final String imgsURL = "http://o6lfm5xje.bkt.clouddn.com";

    public static final String PHONE_LOGIN = "login.do?";
    /**
     * 快递
     */
    public static String QUERY_EXPRESS_URL = "https://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    public static String TEST_ORDER_SERVICE_URL = "https://testapi.kdniao.cc:8081/api/EOrderService";

    /**
     * 活动流量
     */
    public static final String VISIT_AMOUNT = "event/findVisitAmount/";


    /**
     * 发票列表
     */
    public static final String INVOICE_LIST = "event/findEventInvoiceList/";

    /**
     * 设置密码的验证码
     */
    public static final String PASSWORD_VALID_CODE = "account/getSetDrawPasswordValidCode";

    /**
     * 设置密码
     */

    public static final String SET_PASSWORD = "account/resetDrawPasswords";

    /**
     * 提现记录
     */
    public static final String WITHDRAW_RECORD = "event/recharge/loadWithdrawalList";

    /**
     * 提现申请
     */
    public static final String APPLY_WITHDRAW = "account/applyWithdrawal";

    /**
     * 添加提现账户
     */

    public static final String ADD_WITHDRAW_ACCOUNT = "account/addNewOutComeAccount/";

    /**
     * 重置密码
     */
    public static final String RESET_WITHDRAW_PASSWORD = "account/resetDrawPassword";

    /**
     * 是否存在未读消息
     */
    public static final String EXIST_UNREAD_MSG = "/message/findExistNewMessage?";//userId

    /**
     * 更新聊天时间
     */
    public static final String UPDATE_CHAT_TIME = "contact/lastReviewTime/update";

    /**
     * 发送聊天信息
     */
    public static final String POST_CHAT_MESSAGE = "message/send";

    /**
     * 获得聊天信息
     */
    public static final String MESSAGE_LIST = "message/list";

    /**
     * 删除会话
     */
    public static final String DELETE_CHAT = "/message/interactPeople/delete";//userId,contactId

    /**
     * 获取指定会话
     */
    public static final String SINGLE_CHAT = "/message/designInteractPeople/get";//contactId,userId

    /**
     * 获得消息列表
     */
    public static final String CHAT_LIST = "message/interactPeople/get";

    /**
     * 更换门票
     */
    public static final String CHANGE_TICKET = "event/changeTicket/";

    /**
     * 提现帐号
     */
    public static final String WITHDRAW_ACCOUNT = "account/getWithdrawalAccountHistoryList";

    /**
     * 修改备注
     */
    public static final String UPDATE_NOTES = "event/update_notes/";

    /**
     * 退票
     */
    public static final String QUIT_TICKET = "order/refundTicketOrder/";

    /**
     * 重新发送电子票
     */
    public static final String RESEND_TICKET = "event/reSendTicket/";

    /**
     * 修改发票
     */
    public static final String MODIFY_INVOICE = "event/update_invoice/";

    /**
     * 发票
     */
    public static final String INVOICE = "event/orderInvoice/";

    /**
     * 修改订单
     */

    public static final String MODIFY_ORDER = "event/update_order/";

    /**
     * 修改订单审核状态
     */
    public static final String UPDATE_AUDIT = "event/update_audit/";

    /**
     * 单个订单
     */
    public static final String SINGLE_ORDER = "event/order/";

    /**
     * 订单
     */
    public static final String ORDERS = "event/orders/";

    /**
     * 短信充值记录
     */

    public static final String LOAD_SMS_LIST = "event/recharge/loadSmsList?";

    /**
     * 第三方充值
     */
    public static final String RECHARGE_THIRD_MESSAGE = "servicePayOrder/";

    /**
     * 短信充值
     */

    public static final String RECHARGE_MESSAGE = "service/submitPay?";

    /**
     * 获得版本信息
     */
    public static final String UPDATE_VERSION_INFO = "third/getAppVersionName?";

    /**
     * 获得分享信息
     */
    public static final String GET_SHARE_INFO = "getEventShareInfo/";

    /**
     * 获得订单信息
     */
    public static final String GET_ORDER_INFO = "event/orders/";

    /**
     * 修改用户信息
     */
    public static final String UPDATE_USER_INFO = "user/info/update";

    /**
     * 获取活动收入
     */
    public static final String EVENT_INCOME = "event/income/get?";

    /**
     * 获取用户信息
     */
    public static final String USER_INFO = "user/info/get?";

    /**
     * 发布活动
     */
    public static final String PUBLISH_EVENT = "event/publish/";

    /**
     * 获取活动详情
     */
    public static final String EVENT_DETAIL = "event_detail/";

    /**
     * 更新表单item
     */
    public static final String UPDATE_FORM_ITEM = "event/fieldItem/update/";

    /**
     * 更新表单
     */
    public static final String UPDATE_FORM = "event/formField/update/";

    /**
     * 删除表单item
     */
    public static final String DELETE_FORM_ITEM = "event/fieldItem/delete/";

    /**
     * 添加表单item
     */
    public static final String ADD_FORM_ITEM = "event/fieldItem/add/";

    /**
     * 添加表单
     */
    public static final String ADD_FORM = "event/formField/add/";

    /**
     * 删除表单
     */
    public static final String DELETE_FORM = "event/formField/delete/";

    /**
     * 获得所有表单
     */
    public static final String GET_FIELDLIST = "event/fieldList/";

    /**
     * 保存活动基本信息
     */
    public static final String SAVE_EVENT_INFO = "event/addEventInfo/";

    /**
     * 删除门票
     */
    public static final String DELETE_TICKET = "event/ticket/delete/";

    /**
     * 添加门票
     */
    public static final String SAVE_TICKET = "event/ticket/saveOrUpdate/";

    /**
     * 创建活动
     */
    public static final String CREATE_EVENT = "event/create/";

    /**
     * 提交活动需求
     */
    public static final String SUMMIT_REQUIRE_CONTENT = "event/eventOffer";

    /**
     * 添加主办方
     */
    public static final String ADD_ORGANIZER = "organizer/addOrganizer?";

    /**
     * 获得主办方
     */
    public static final String ORGANIZER = "organizer/getMyOrganizers?";

    /**
     * 获得活动需求内容
     */
    public static final String REQUIRE_CONTENT = "/event/eventOfferType?";

    /**
     * 验证绑定手机号
     */
    public static final String BIND_USER_CELLPHONE = "bindUserCellPhone?";

    /**
     * 获取收入
     */
    public static final String INCOME = "event/getMyEventsIncomeStatistic?";

    /**
     * 获取报名人数
     */
    public static final String NEW_ATTENDEE_COUNT = "event/getMyEventsAttendeeStatistic?";

    /**
     * 昨天收入
     */
    public static final String NEW_INCREASE = "event/statistic/newIncrease?";

    /**
     * 动态
     */
    public static final String FIND_COMMENT_LIST = "appComment/findCommentList";

    /**
     * 登录
     */
    public static final String logins = "login.do?loginSource=4&loginType=0&access_token=ipad&access_secret=ipad_secret";
    public static final String AUTO_LOGIN = "login.do?loginSource=4&loginType=1&access_token=ipad&access_secret=ipad_secret";
    /**
     * 检查手机号是否存在
     */
    public static final String PHONEISEXIST = "check_account.do?";

    /**
     * 获取短信验证码
     */
    public static final String GETSMS = "get_sms_code.do?";

    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = "reset_password.do?";

    /**
     * 注册
     */

    public static final String RRGISTER = "register.do?";
    public static final String REGISTERSOURCE = "registerSource=0";
    /**
     * 获取进行中的活动列表
     */
    public static final String EXEREVENTLIST = "events/";
    public static final String APITYPE = "apiType=1";

    /**
     * 获得门票信息
     */
    public static final String TICKET = "event/tickets/";
    public static final String T_ACCESS_TOKEN = "?access_token=ipad";

    /**
     * 获得参会人员列表
     */
    public static final String ATTENDEE = "event/attendee/";
    public static final String ATTENDEE_FILE = "event/getAttendeeJsonFile/";
    public static final String SYNCCALL = "?sync_all=1";

    /*
     * 获取表单信息
     */
    public static final String FORMTYPE = "event/form/";

    /**
     * 单个参会人员--orderId
     */
    public static final String SINGLE_ATTENDEE = "event/findAttendeeInfoByOrderId/";

    /**
     * 单个参会人员--attendeeId
     */
    public static final String SINGLE_ATTENDEE_ATTENDEEID = "event/findAttendeeInfoByAttendeeId/";

    /**
     * 修改参会人员信息
     */
    public static final String MODIFYINFO = "order/edit_attendee_info?";

    /**
     * 添加参会人员
     */
    public static final String SUBMIT_ORDER = "order/submit_order?";
    public static final String SUBMIT_DELEGATE_ORDER = "event/add_delegate?";

    /**
     * 采集点信息
     */
    public static final String COLLECT_CHILD = "event/getCheckinListForCollection/";

    /**
     * 导出数据
     */
    public static final String EXPORT_COLLECT = "event/exportAttendeeListForCollection/";

    /**
     * 上传采集人员
     */
    public static final String POST_COLLECT_INFO = "event/recordCollectionData/";

    /**
     * 获得进出控制人员数量
     */
    public static final String COLLECT_IN_OUT_NUM = "event/recordCollectionData/getInVenueCount/";

    /**
     * 获得采集点配置信息
     */
    public static final String COLLECTION_CONFIG_INFO = "event/collectionPoint/collectionPointInfo/";

    /**
     * 获得采集点管理列表
     */
    public static final String COLLECT_MANAGER = "event/getCollectionList/";

    /**
     * 设置采集点进出口
     */
    public static final String COLLECT_IN_OUT = "event/recordCollectionData/setAttendeeState/";

    /**
     * 上传文件
     */
    public static final String COMMON_FILEUPLOAD = "common/commonFileUpload";

    /**
     * 获取动态详情
     */
    public static final String GET_COMMENT_DETAIL = "getCommentDetail";

    /**
     * 回复评论
     */
    public static final String ORGANIZER_REPLY_COMMENT = "comment/organizerReplyComment";


    public static final String COMMON_PIC_UPLOAD = "common/commonFileUpload";

    /**
     * 意见反馈
     */
    public static final String SUGGESTION_UPLOAD = "suggestion/save";

    /**
     * 发布动态
     */
    public static final String PublishDynamic = "comment/organizerPublishDynamic";

    /**
     * 创建通知
     */
    public static final String NOTICE_UPLOAD = "comment/organizerNotice";

    /**
     * 点赞
     */
    public static final String EDIT_COMMENT_LIKE = "comment/editCommentLike";


    /**
     * 屏蔽
     */
    public static final String SHIELD_COMMENT = "comment/shieldComment";

    /**
     * 置顶
     */
    public static final String PRIORITY_COMMENT = "comment/priorityComment";

    /**
     * 举报消息列表
     */
    public static final String FIND_REPORT_COMMENT = "comment/findReportComment";

    /**
     * 忽略举报信息
     */
    public static final String IGNORE_REPORT_COMMENT = "comment/ignoreReportComment";

    /**
     * 邀请嘉宾
     */
    public static final String SEND_INVITE_GUEST = "speaker/sendInviteGuest";

    /**
     * 绑定手机号
     */
    public static final String BIND_SPEAKER_CELLPHONE = "speaker/bindSpeakerCellPhone";

    /**
     * 绑定邮箱
     */
    public static final String BIND_SPEAKER_EMIAL = "speaker/bindSpeakerEmial";

    /**
     * 所有展商
     */
    public static final String EXHIBITOR_LIST = "exhibitor/getExhibitorList";

    /**
     * 展商信息
     */
    public static final String EXHIBITORINFO = "exhibitor/queryExhibitorInfo";

    /**
     * 展商动态
     */
    public static final String EXHIBITORCOMMENTLIST = "comment/getExhibitorCommentList";

    /**
     * 展商发布动态
     */
    public static final String PUBLISHDYFOREXHIBITOR = "comment/publishDynamicForExhibitor";

    /**
     * 生成展商分享图片
     */
    public static final String CREATESHAREIMAGE = "shareTemplate/createShareImage";

    /**
     * 采集点参会人员
     */
    public static final String ATTENDEELIST = "exhibitor/getExhibitorAttendeeListInfoByTags";

    /**
     * 展商采集点创建的所有标签列表，以及该展商绑定的参会者的数量
     */
    public static final String EXHIBITORTAGS = "exhibitor/getExhibitorTags";

    /**
     * 展商参会者详情
     */
    public static final String EXHIBITITOEATTENDEEDETAIL = "exhibitor/getExhibitorAttendeeDetail";

    /**
     * 展商添加修改标签
     */
    public static final String ADD_OR_UPDATETAG = "exhibitor/addOrUpdateExhibitorAttendeeTag";

    /**
     * 展商通过标签管理参会者的列表信息
     */
    public static final String ATTENDEE_OF_TAG = "exhibitor/getExhibitorAttendeeListInfobyTag";

    /**
     * 不含添加过该标签的参会者的信息列表
     */
    public static final String ATTENDEE_LIST = "exhibitor/getExhibitorAttendeeListInfo";


    /**
     * 在标签下添加参会人员
     */
    public static final String ADD_ATTENDEE_OF_TAG = "exhibitor/batchAddExhibitorAttendeesRemark";

    /**
     * 为展商参会者绑定标签
     */
    public static final String ADD_TAG_FOR_ATTENDEE = "exhibitor/addTagsForExAttendee";

    /**
     * 删除参会者绑定的标签
     */
    public static final String DELETE_TAG_FOR_ATTENDEE = "exhibitor/deleteExhibitorAttendeeRemark";

    /**
     * 删除标签以及该标签下的所有参会人员
     */
    public static final String DELETE_TAG_AND_ATTENDEE = "exhibitor/deleteTag";

    /**
     * 展商管理中心列表以及详情
     */
    public static final String QUERY_EXHIBITORS = "queryExhibitors";

    /**
     * 查询展商转账记录和详情
     */
    public static final String EXHIBITOR_DETAIL = "exhibitor/getExhibitorDetail";

    /**
     * 展商审核或者备注
     */
    public static final String UPDATE_EXHIBITOR = "updateExhibitor";

    /**
     * 展商logo
     */

    public static final String UPDATE_EXHIBITOR_LOGO = "exhibitor/updateExhibitorLogo";

    /**
     * 展商回复评论
     */

    public static final String EXHIBITOR_REPLY = "comment/exhibitorReply";


    /**
     * 查询是否是展商用户
     */

    public static final String CHECK_IS_EXHIBITOR = "exhibitor/checkIsExhibitor";

    /**
     * 云信获取accid 和 token的接口
     */
    public static final String YUNXIN_TOKEN = "yunxin/getToken";


    /**
     * 根据参会人员获取对应的accid
     */
    public static final String YUNXIN_ACCID = "yunxin/";
    public static final String YUNXIN_ACCID2 = "/yunxinToken";

    //刷新token
    public static final String REFRESH_TOKEN = "expand/expire_time/auto_login";

    //登录pc端
    public static final String LOGIN_PC = "login/confirmQrCodeLogin/";
}
