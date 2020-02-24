package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/29
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class DynamicListData {
    private int respType;
    private int retStatus;
    private RespObjectData respObject;

    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public RespObjectData getRespObject() {
        return respObject;
    }

    public DynamicListData(JsonObject jsonObject) {
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }
        if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonObject()) {
            respObject = new RespObjectData(jsonObject.getAsJsonObject("respObject"));
        }
    }

    public static class RespObjectData {
        private int tipReportCount;
        private ArrayList<CommentList> commentLists;
        private CommentList comment;
        private ArrayList<CommentList> childCommentList;
        private ArrayList<CommentList> organizerCommentList;

        public int getTipReportCount() {
            return tipReportCount;
        }

        public ArrayList<CommentList> getCommentLists() {
            return commentLists;
        }

        public CommentList getComment() {
            return comment;
        }

        public RespObjectData(JsonObject jsonObject) {
            if (jsonObject.get("tipReportCount") != null) {
                tipReportCount = jsonObject.get("tipReportCount").getAsInt();
            }

            if (jsonObject.get("childCommentList") != null && jsonObject.get("childCommentList").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("childCommentList").getAsJsonArray();
                childCommentList = new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    childCommentList.add(new CommentList(jsonArray.get(i).getAsJsonObject()));
                }
            }
            if (jsonObject.get("organizerCommentList") != null && jsonObject.get("organizerCommentList").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("organizerCommentList").getAsJsonArray();
                organizerCommentList = new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    organizerCommentList.add(new CommentList(jsonArray.get(i).getAsJsonObject()));
                }
            }
            if (jsonObject.get("commentList") != null && jsonObject.get("commentList").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("commentList").getAsJsonArray();
                commentLists = new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    commentLists.add(new CommentList(jsonArray.get(i).getAsJsonObject()));
                }
            }

            if (jsonObject.get("comment") != null) {
                comment = new CommentList(jsonObject.getAsJsonObject("comment"));
            }
        }

        public ArrayList<CommentList> getChildCommentList() {
            return childCommentList;
        }

        public ArrayList<CommentList> getOrganizerCommentList() {
            return organizerCommentList;
        }
    }

    public static class CommentList {
        private int commentId;
        private int eventId;
        private String commentKey;
        private String commentVal;
        private User replayToUser;
        private String commentText;
        private String commentSubTime;
        private int status;
        private String avatar;
        private int priority;
        private int type;
        private int identityType;
        private int replyType;
        private int isPublic;
        private String voiceUrl;
        private String duration;
        private String images;
        private String questionName;
        private String questionContent;
        private String questionImage;
        private boolean chooseSpeaker;
        private int likeNumber;
        private int commentNumber;
        private int collectionNumber;
        private String showName;
        private String showAvatar;
        private String showTime;
        private String eventName;
        private String eventLogo;
        private ArrayList<Speaker> speakerLists;
        //        private CommentList replyComment;
        private CommentList parentComment;
        private boolean isFavorite;
        private boolean isCollection;
        private boolean replyComment;
        private User user;

        private ReplyComment replyComment1;

        public ReplyComment getReplyComment1() {
            return replyComment1;
        }

        public int getCommentId() {
            return commentId;
        }

        public int getEventId() {
            return eventId;
        }

        public String getCommentKey() {
            return commentKey;
        }

        public String getCommentVal() {
            return commentVal;
        }

        public User getReplayToUser() {
            return replayToUser;
        }

        public String getCommentText() {
            return commentText;
        }

        public String getCommentSubTime() {
            return commentSubTime;
        }

        public int getStatus() {
            return status;
        }

        public String getAvatar() {
            return avatar;
        }

        public int getPriority() {
            return priority;
        }

        public int getType() {
            return type;
        }

        public int getIdentityType() {
            return identityType;
        }

        public int getReplyType() {
            return replyType;
        }

        public int getIsPublic() {
            return isPublic;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public String getDuration() {
            return duration;
        }

        public String getImages() {
            return images;
        }

        public String getQuestionName() {
            return questionName;
        }

        public String getQuestionContent() {
            return questionContent;
        }

        public String getQuestionImage() {
            return questionImage;
        }

        public boolean isChooseSpeaker() {
            return chooseSpeaker;
        }

        public int getLikeNumber() {
            return likeNumber;
        }

        public int getCommentNumber() {
            return commentNumber;
        }

        public int getCollectionNumber() {
            return collectionNumber;
        }

        public String getShowName() {
            return showName;
        }

        public String getShowAvatar() {
            return showAvatar;
        }

        public String getShowTime() {
            return showTime;
        }

        public String getEventName() {
            return eventName;
        }

        public ArrayList<Speaker> getSpeakerList() {
            return speakerLists;
        }

//        public CommentList getReplyComment() {
//            return replyComment;
//        }

        public CommentList getParentComment() {
            return parentComment;
        }


        public boolean isFavorite() {
            return isFavorite;
        }

        public boolean isCollection() {
            return isCollection;
        }

        public boolean isReplyComment() {
            return replyComment;
        }


        public String getEventLogo() {
            return eventLogo;
        }

        public CommentList(JsonObject jsonObject) {
            commentId = jsonObject.get("commentId").getAsInt();
            eventId = jsonObject.get("eventId").getAsInt();
            commentKey = jsonObject.get("commentKey").getAsString();
            commentVal = jsonObject.get("commentVal").getAsString();
            commentText = jsonObject.get("commentText").getAsString();
            commentSubTime = jsonObject.get("commentSubTime").getAsString();
            status = jsonObject.get("status").getAsInt();
            avatar = jsonObject.get("avatar").getAsString();
            priority = jsonObject.get("priority").getAsInt();
            type = jsonObject.get("type").getAsInt();
            identityType = jsonObject.get("identityType").getAsInt();
            replyType = jsonObject.get("replyType").getAsInt();
            isPublic = jsonObject.get("isPublic").getAsInt();
            voiceUrl = jsonObject.get("voiceUrl").getAsString();
            duration = jsonObject.get("duration").getAsString();
            images = jsonObject.get("images").getAsString();
            questionName = jsonObject.get("questionName").getAsString();
            questionContent = jsonObject.get("questionContent").getAsString();
            questionImage = jsonObject.get("questionImage").getAsString();
            chooseSpeaker = jsonObject.get("chooseSpeaker").getAsBoolean();
            likeNumber = jsonObject.get("likeNumber").getAsInt();
            collectionNumber = jsonObject.get("collectionNumber").getAsInt();
            showName = jsonObject.get("showName").getAsString();
            showAvatar = jsonObject.get("showAvatar").getAsString();
            showTime = jsonObject.get("showTime").getAsString();
            eventName = jsonObject.get("eventName").getAsString();
            eventLogo = jsonObject.get("eventLogo").getAsString();
//            replyComment = jsonObject.get("replyComment").getAsString();
            isFavorite = jsonObject.get("isFavorite").getAsBoolean();
            isCollection = jsonObject.get("isCollection").getAsBoolean();
            if (jsonObject.get("childCommentList").isJsonArray()) {
                JsonArray childCommentList = jsonObject.get("childCommentList").getAsJsonArray();
                commentNumber = childCommentList.size();
            }
            if (jsonObject.get("replayToUser").isJsonObject()) {
                replayToUser = new User(jsonObject.getAsJsonObject("replayToUser"));
            }
            if (jsonObject.get("speakerList").isJsonArray() && jsonObject.getAsJsonArray("speakerList").size() > 0) {
                JsonArray speakerArray = jsonObject.getAsJsonArray("speakerList");
                speakerLists = new ArrayList<>(speakerArray.size());

                for (int i = 0; i < speakerArray.size(); i++) {
                    speakerLists.add(new Speaker(speakerArray.get(i).getAsJsonObject()));
                }
            }
            if (jsonObject.get("replyComment").isJsonObject()) {
//                replyComment = new CommentList(jsonObject.getAsJsonObject("replyComment"));
                replyComment1 = new ReplyComment(jsonObject.getAsJsonObject("replyComment"));
                replyComment = true;
            }
            if (jsonObject.get("parentComment").isJsonObject()) {
                parentComment = new CommentList(jsonObject.getAsJsonObject("parentComment"));
            }
            if (jsonObject.get("user").isJsonObject()) {
                user = new User(jsonObject.getAsJsonObject("user"));
            }
        }

        public User getUser() {
            return user;
        }
    }

    public static class Speaker {
        private int speakerId;
        private String agendaSpeakerId;
        private String speakerName;
        private String enSpeakerName;
        private String position;
        private String enPosition;
        private String intro;
        private String enIntro;
        private String avatar;
        private String createTime;
        private String contactEmail;
        private String contactPhone;
        private boolean hasInvite;

        public int getSpeakerId() {
            return speakerId;
        }

        public String getAgendaSpeakerId() {
            return agendaSpeakerId;
        }

        public String getSpeakerName() {
            return speakerName;
        }

        public String getEnSpeakerName() {
            return enSpeakerName;
        }

        public String getPosition() {
            return position;
        }

        public String getEnPosition() {
            return enPosition;
        }

        public String getIntro() {
            return intro;
        }

        public String getEnIntro() {
            return enIntro;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public boolean isHasInvite() {
            return hasInvite;
        }

        public Speaker(JsonObject jsonObject) {
            speakerId = jsonObject.get("speakerId").getAsInt();
            agendaSpeakerId = jsonObject.get("agendaSpeakerId").getAsString();
            speakerName = jsonObject.get("speakerName").getAsString();
            enSpeakerName = jsonObject.get("enSpeakerName").getAsString();
            position = jsonObject.get("position").getAsString();
            enPosition = jsonObject.get("enPosition").getAsString();
            intro = jsonObject.get("intro").getAsString();
            enIntro = jsonObject.get("enIntro").getAsString();
            avatar = jsonObject.get("avatar").getAsString();
            createTime = jsonObject.get("createTime").getAsString();
            contactPhone = jsonObject.get("contactPhone").getAsString();
            contactEmail = jsonObject.get("contactEmail").getAsString();
            hasInvite = jsonObject.get("hasInvite").getAsBoolean();
        }
    }

    public static class User {
        private int userId;
        private String email;
        private String cellphone;
        private String nickName;
        private String userName;
        private String showName;
        private String unionid;
        private String lastName;
        private String firstName;
        private String userLocal;
        private String avatar;
        private String salt;
        private int state;

        public User(JsonObject jsonObject) {
            userId = jsonObject.get("userId").getAsInt();
            email = jsonObject.get("email").getAsString();
            cellphone = jsonObject.get("cellphone").getAsString();
            nickName = jsonObject.get("nickName").getAsString();
            userName = jsonObject.get("userName").getAsString();
            unionid = jsonObject.get("unionid").getAsString();
            showName = jsonObject.get("showName").getAsString();
            lastName = jsonObject.get("lastName").getAsString();
            firstName = jsonObject.get("firstName").getAsString();
            userLocal = jsonObject.get("userLocal").getAsString();
            avatar = jsonObject.get("avatar").getAsString();
            salt = jsonObject.get("salt").getAsString();
            state = jsonObject.get("state").getAsInt();
        }

        public String getShowName() {
            return showName;
        }

        public int getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getCellphone() {
            return cellphone;
        }

        public String getNickName() {
            return nickName;
        }

        public String getUserName() {
            return userName;
        }

        public String getUnionid() {
            return unionid;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getUserLocal() {
            return userLocal;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getSalt() {
            return salt;
        }

        public int getState() {
            return state;
        }
    }



    public static class ReplyComment{
        private User isReplyToUser;

        public User getIsReplyToUser() {
            return isReplyToUser;
        }

        public ReplyComment(JsonObject jsonObject){
            if (jsonObject.get("user").isJsonObject()) {
                isReplyToUser = new User(jsonObject.getAsJsonObject("user"));
            }
        }
    }

}
