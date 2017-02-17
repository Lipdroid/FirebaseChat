package com.example.lipuhossain.firebasechat.model;

/**
 * Created by lipuhossain on 2/17/17.
 */

public class UserObject {
    private String user_id = null;
    private String user_name = null;
    private String is_logged_in = null;
    private String user_status = null;
    private String user_gcm_id = null;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIs_logged_in() {
        return is_logged_in;
    }

    public void setIs_logged_in(String is_logged_in) {
        this.is_logged_in = is_logged_in;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_gcm_id() {
        return user_gcm_id;
    }

    public void setUser_gcm_id(String user_gcm_id) {
        this.user_gcm_id = user_gcm_id;
    }
}
