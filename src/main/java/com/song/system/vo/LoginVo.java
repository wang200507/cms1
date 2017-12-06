package com.song.system.vo;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
public class LoginVo {
    private String username;
    private String password;

    public LoginVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginVo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
