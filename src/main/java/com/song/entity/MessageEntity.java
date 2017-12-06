package com.song.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-28
 */
@Entity
@Table(name = "tb_message")
public class MessageEntity implements Serializable {

    @Id
    private String id;

    private String msgTitle ;

    private String msgContent ;

    private String createUser;

    private Timestamp createTime ;

    private Timestamp startTime ;
    private Timestamp endTime ;

    private Integer deleted;

    private String updateUser;

    private Timestamp updateTime;


    public MessageEntity() {
    }

    public MessageEntity(String id, String msgTitle, String msgContent, String createUser, Timestamp createTime, Timestamp startTime, Timestamp endTime, Integer deleted, String updateUser, Timestamp updateTime) {
        this.id = id;
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
        this.createUser = createUser;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleted = deleted;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
