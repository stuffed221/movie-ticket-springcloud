package org.example.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

public class ManagerListVO {

    private Integer managerId;

    private String managerName;

    private String managerSex;

    private String managerPhoneNumber;

    private String managerStatus;

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

    public ManagerListVO(Integer managerId, String managerName, String managerSex, String managerPhoneNumber, String managerStatus) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerSex = managerSex;
        this.managerPhoneNumber = managerPhoneNumber;
        this.managerStatus = managerStatus;
    }
}
