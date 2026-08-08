package org.example.dto;

import javax.validation.constraints.NotNull;

public class ManagerLoginDTO {
    @NotNull(message = "用户名不能为空")
    private String managerName;
    @NotNull(message = "密码不能为空")
    private String managerPassword;

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
}
