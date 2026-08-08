package org.example.dto;

import javax.validation.constraints.NotNull;

public class ManagerRegisterDTO {
    @NotNull(message = "用户名不能为空")
    private String managerName;
    @NotNull(message = "密码不能为空")
    private String managerPassword;
    @NotNull(message = "电话号码不能为空")
    private String managerPhoneNumber;
    @NotNull(message = "性别不能为空")
    private String managerSex;

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

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public void setManagerPhoneNumber(String managerPhoneNumber) {
        this.managerPhoneNumber = managerPhoneNumber;
    }

    public String getManagerSex() {
        return managerSex;
    }

    public void setManagerSex(String managerSex) {
        this.managerSex = managerSex;
    }
}
