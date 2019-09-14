package com.softmax.marvel.entity;

import com.softmax.marvel.entity.enums.UserLevel;
import com.softmax.marvel.entity.enums.UserStatus;
import com.softmax.marvel.entity.enums.UserSex;
import com.framework.common.BaseEntity;

import javax.persistence.Table;
import java.io.Serializable;


/**
 * 用户信息
 *
 * @author : Jarvis
 * @date : 2018/5/13
 */
@Table(name = "user_info")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1701367400616772167L;

    private String userName;
    private String passWord;
    private UserSex userSex;
    private String nickName;
    private UserStatus userStatus;
    private UserLevel userLevel;

    public UserInfo() {
    }

    public UserInfo(String userName, String passWord, UserSex userSex) {
        super();
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserSex getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSex userSex) {
        this.userSex = userSex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }
}