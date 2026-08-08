package org.example.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户实体类
 */
@TableName("user")
@ApiModel("用户实体")
public class User {

    @TableId(value = "user_id",type = IdType.AUTO)
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("用户密码")
    private String userPassword;
    @ApiModelProperty("用户性别")
    private String userSex;
    @ApiModelProperty("用户年龄")
    private Integer userAge;
    @ApiModelProperty("用户手机号")
    private String userPhoneNumber;
    @ApiModelProperty("用户状态")
    private Integer userStatus;
    @ApiModelProperty("创建时间")
    private String userCreateTime;
    @ApiModelProperty("更新时间")
    private String userUpdateTime;



    public User() {
    }

    public User(Integer userId, String userName, String userPassword, String userSex, Integer userAge, String userPhoneNumber, Integer userStatus, String userCreateTime, String userUpdateTime) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.userAge = userAge;
        this.userPhoneNumber = userPhoneNumber;
        this.userStatus = userStatus;
        this.userCreateTime = userCreateTime;
        this.userUpdateTime = userUpdateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userAge=" + userAge +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userStatus=" + userStatus +
                ", userCreateTime='" + userCreateTime + '\'' +
                ", userUpdateTime='" + userUpdateTime + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(String userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }
}
