package org.example.Entity;

import com.baomidou.mybatisplus.annotation.TableField;

public abstract class BaseEntity {

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
