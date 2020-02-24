package com.bagevent.new_home.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/6
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class CommentEntity implements MultiItemEntity {

    private DynamicListData.CommentList commentList;
    private String name;
    private int type;

    public static final int TYPE_LINE = 0;
    public static final int TYPE_NAME = 1;
    public static final int TYPE_ITEM = 2;

    @Override
    public int getItemType() {
        return type;
    }

    public CommentEntity(int type) {
        this.type = type;
    }

    public CommentEntity(int type, DynamicListData.CommentList commentList) {
        this.type = type;
        this.commentList = commentList;
    }

    public CommentEntity(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public DynamicListData.CommentList getCommentList() {
        return commentList;
    }

    public String getName() {
        return name;
    }
}
