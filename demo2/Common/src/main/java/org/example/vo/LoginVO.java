package org.example.vo;

public class LoginVO {
    private String token;
    private Integer userId;
    private String role;
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public LoginVO(String token, Integer userId, String role, String userName) {
        this.token = token;
        this.userId = userId;
        this.role = role;
        this.userName = userName;
    }





    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
