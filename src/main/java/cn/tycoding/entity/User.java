package cn.tycoding.entity;

import java.io.Serializable;

/**
 * @auther TyCoding
 * @date 2018/9/28
 */
public class User implements Serializable {

    private Long userId; //编号
    private String userName; //用户名
    private String password; //密码
    private String salt;

    public User(String username, String password) {
    }

    public User(Long userId, String userName, String password, String salt) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
