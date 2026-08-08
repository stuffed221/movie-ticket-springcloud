package org.example.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员实体类
 */
@ApiModel("管理员实体")
@TableName("manager")
public class Manager {

    @TableId(value = "manager_id",type = IdType.AUTO)
    @ApiModelProperty("管理员编号")
    private Integer managerId;

    @TableField("manager_name")
    @ApiModelProperty("管理员名称")
    private String managerName;



    @TableField("manager_password")
    @ApiModelProperty("管理员密码")
    private String managerPassword;

    @ApiModelProperty("管理员性别")
    private String managerSex;

    @ApiModelProperty("管理员手机号")
    private String managerPhoneNumber;

    @ApiModelProperty("管理员状态")
    private String managerStatus;

    @ApiModelProperty("管理员创建时间")
    private String managerCreateTime;



    @ApiModelProperty("管理员更新时间")
    private String managerUpdateTime;

    public Manager(Integer managerId, String managerName, String managerPassword, String managerSex, String managerPhoneNumber, String managerStatus, String managerCreateTime, String managerUpdateTime) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerPassword = managerPassword;
        this.managerSex = managerSex;
        this.managerPhoneNumber = managerPhoneNumber;
        this.managerStatus = managerStatus;
        this.managerCreateTime = managerCreateTime;
        this.managerUpdateTime = managerUpdateTime;
    }

    public Manager() {

    }

    public String getManagerCreateTime() {
        return managerCreateTime;
    }

    public void setManagerCreateTime(String managerCreateTime) {
        this.managerCreateTime = managerCreateTime;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public String getManagerSex() {
        return managerSex;
    }

    public void setManagerSex(String managerSex) {
        this.managerSex = managerSex;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public String getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String managerStatus) {
        this.managerStatus = managerStatus;
    }

    public String getManagerUpdateTime() {
        return managerUpdateTime;
    }

    public void setManagerUpdateTime(String managerUpdateTime) {
        this.managerUpdateTime = managerUpdateTime;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", managerName='" + managerName + '\'' +
                ", managerPassword='" + managerPassword + '\'' +
                ", managerSex='" + managerSex + '\'' +
                ", managerPhoneNumber='" + managerPhoneNumber + '\'' +
                ", managerStatus='" + managerStatus + '\'' +
                ", managerCreateTime='" + managerCreateTime + '\'' +
                ", managerUpdateTime='" + managerUpdateTime + '\'' +
                '}';
    }
}
