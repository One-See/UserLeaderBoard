package com.crio.board.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String userId;
    private String userName;
    private int score;
    private String[] badges;

    public User(String userId, String userName, int score, String[] badges) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
        this.badges = badges;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[] getBadges() {
        return badges;
    }

    public void setBadges(String[] badges) {
        this.badges = badges;
    }

    @Override
    public String toString() {
        return "user [userId=" + userId + ", userName=" + userName + ", score=" + score + ", badges="
                + Arrays.toString(badges) + "]";
    }

}
