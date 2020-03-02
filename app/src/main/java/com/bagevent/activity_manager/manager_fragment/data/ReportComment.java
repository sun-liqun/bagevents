package com.bagevent.activity_manager.manager_fragment.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/1/2
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ReportComment implements Parcelable {
    private int reportCommentId;
    private Comment comment;
    private List<User> userList;

    protected ReportComment(Parcel in) {
        reportCommentId = in.readInt();
        comment = in.readParcelable(Comment.class.getClassLoader());
        // 传递自定义List 只能用以下方法
        userList = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<ReportComment> CREATOR = new Creator<ReportComment>() {
        @Override
        public ReportComment createFromParcel(Parcel in) {
            return new ReportComment(in);
        }

        @Override
        public ReportComment[] newArray(int size) {
            return new ReportComment[size];
        }
    };

    public int getReportCommentId() {
        return reportCommentId;
    }

    public void setReportCommentId(int reportCommentId) {
        this.reportCommentId = reportCommentId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reportCommentId);
        dest.writeParcelable(comment, 0);
        dest.writeTypedList(userList);
    }

    public static class Comment implements Parcelable {
        private int commentId;
        private int eventId;
        private String images;
        private String commentText;
        private User user;

        protected Comment(Parcel in) {
            commentId = in.readInt();
            eventId = in.readInt();
            images = in.readString();
            commentText = in.readString();
            user = in.readParcelable(User.class.getClassLoader());
        }

        public static final Creator<Comment> CREATOR = new Creator<Comment>() {
            @Override
            public Comment createFromParcel(Parcel in) {
                return new Comment(in);
            }

            @Override
            public Comment[] newArray(int size) {
                return new Comment[size];
            }
        };

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(commentId);
            dest.writeInt(eventId);
            dest.writeString(images);
            dest.writeString(commentText);
            dest.writeParcelable(user, 0);
        }
    }

    public static class User implements Parcelable {
        private String userId;
        private String email;
        private String cellphone;
        private String nickName;
        private String showName;
        private String userName;
        private int state;
        private String avatar;

        protected User(Parcel in) {
            userId = in.readString();
            email = in.readString();
            cellphone = in.readString();
            nickName = in.readString();
            showName = in.readString();
            userName = in.readString();
            state = in.readInt();
            avatar = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userId);
            dest.writeString(email);
            dest.writeString(cellphone);
            dest.writeString(nickName);
            dest.writeString(showName);
            dest.writeString(userName);
            dest.writeInt(state);
            dest.writeString(avatar);
        }
    }
}
