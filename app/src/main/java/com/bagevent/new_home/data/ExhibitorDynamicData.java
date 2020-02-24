package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/02/27
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorDynamicData {
    private int respType;
    private int retStatus;
    private RespObjectBean respObject;

    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public RespObjectBean getRespObject() {
        return respObject;
    }

    public ExhibitorDynamicData(JsonObject jsonObject){
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }
        if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonArray()) {
            respObject = new RespObjectBean(jsonObject.get("respObject").getAsJsonArray());
        }

    }
    public static class RespObjectBean{
        private ArrayList<DynamicList> dynamicLists;

        public ArrayList<DynamicList> getDynamicLists() {
            return dynamicLists;
        }

        public RespObjectBean(JsonArray jsonArray){
            dynamicLists=new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                dynamicLists.add(new DynamicList(jsonArray.get(i).getAsJsonObject()));
            }
        }
    }

    public static class DynamicList{

        private ArrayList<ChildCommentList> childCommentLists ;
        private ArrayList<Speaker> speakerLists;
        private ArrayList<ParentComment> parentComment;
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
        private boolean isFavorite;
        private boolean isCollection;
        private boolean replyComment;
        private User user;


        public ArrayList<ChildCommentList> getChildCommentLists() {
            return childCommentLists;
        }

        public ArrayList<Speaker> getSpeakerLists() {
            return speakerLists;
        }

        public ArrayList<ParentComment> getParentComment() {
            return parentComment;
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

        public String getEventLogo() {
            return eventLogo;
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

        public User getUser() {
            return user;
        }

        public DynamicList(JsonObject jsonObject){
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
                JsonArray childCommentArray = jsonObject.get("childCommentList").getAsJsonArray();
                commentNumber = childCommentArray.size();
                childCommentLists = new ArrayList<>(commentNumber);
                for (int i = 0; i < commentNumber ;i++) {
                    childCommentLists.add(new ChildCommentList(childCommentArray.get(i).getAsJsonObject()));
                }
            }
            if (jsonObject.get("replayToUser").isJsonObject()) {
                replayToUser = new User(jsonObject.getAsJsonObject("replayToUser"));
            }
//            if (jsonObject.get("speakerList").isJsonArray() && jsonObject.getAsJsonArray("speakerList").size() > 0) {
//                JsonArray speakerArray = jsonObject.getAsJsonArray("speakerList");
//                speakerLists = new ArrayList<>(speakerArray.size());
//
//                for (int i = 0; i < speakerArray.size(); i++) {
//                    speakerLists.add(new Speaker(speakerArray.get(i).getAsJsonObject()));
//                }
//            }
            if (jsonObject.get("replyComment").isJsonObject()) {
                replyComment = true;
            }
//            if (jsonObject.get("parentComment").isJsonObject()) {
//                parentComment = new ParentComment(jsonObject.getAsJsonObject("parentComment"));
//            }
            if (jsonObject.get("user").isJsonObject()) {
                user = new User(jsonObject.getAsJsonObject("user"));
            }
        }
    }


    public static class ChildCommentList{

        private User replayToUser;
        private ReplyComment replyComment;
        String commentId;
        int eventId;
        String commentText;
        int status;
        String priority;
        int type;
        String identityType;
        String voiceUrl;
        String images;
        int replyType;
        int likeNumber;
        String showName;
        String showAvater;
        String showTime;
        String eventName;
        private User user;
        private boolean isReplyComment;

        public User getReplayToUser() {
            return replayToUser;
        }

        public ReplyComment getReplyComment() {
            return replyComment;
        }

        public boolean isReplyComment() {
            return isReplyComment;
        }

        public User getUser() {
            return user;
        }



        public String getCommentId() {
            return commentId;
        }

        public int getEventId() {
            return eventId;
        }

        public String getCommentText() {
            return commentText;
        }

        public int getStatus() {
            return status;
        }

        public String getPriority() {
            return priority;
        }

        public int getType() {
            return type;
        }

        public String getIdentityType() {
            return identityType;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public String getImages() {
            return images;
        }

        public int getReplyType() {
            return replyType;
        }

        public int getLikeNumber() {
            return likeNumber;
        }


        public String getShowName() {
            return showName;
        }

        public String getShowAvater() {
            return showAvater;
        }

        public String getShowTime() {
            return showTime;
        }

        public String getEventName() {
            return eventName;
        }

        public ChildCommentList(JsonObject jsonObject){
            commentId = jsonObject.get("commentId").getAsString();
            eventId = jsonObject.get("eventId").getAsInt();
            commentText = jsonObject.get("commentText").getAsString();
            priority = jsonObject.get("priority").getAsString();
            type = jsonObject.get("type").getAsInt();
            identityType = jsonObject.get("identityType").getAsString();
            images = jsonObject.get("images").getAsString();
            showName = jsonObject.get("showName").getAsString();
            showAvater = jsonObject.get("showAvatar").getAsString();
            showTime = jsonObject.get("showTime").getAsString();
            eventName = jsonObject.get("eventName").getAsString();
            status = jsonObject.get("status").getAsInt();
            replyType = jsonObject.get("replyType").getAsInt();
            likeNumber = jsonObject.get("likeNumber").getAsInt();
            if (jsonObject.get("user").isJsonObject()) {
                user = new User(jsonObject.getAsJsonObject("user"));
            }
            if (jsonObject.get("replayToUser").isJsonObject()) {
                replayToUser = new User(jsonObject.getAsJsonObject("replayToUser"));
            }
            if (jsonObject.get("replyComment").isJsonObject()) {
                isReplyComment = true;
                replyComment=new ReplyComment(jsonObject.getAsJsonObject("replyComment"));
            }
        }
    }

    public static class Speaker{

    }
    public static class ParentComment{

    }
    public static class User{
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
