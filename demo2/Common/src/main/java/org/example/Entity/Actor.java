package org.example.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("actor")
@ApiModel("演员实体")
public class Actor {

    @TableId(value = "actor_id",type = IdType.AUTO)
    @ApiModelProperty("演员ID")
    private Integer actorId;

    @ApiModelProperty("演员名称")
    private String actorName;

    public Actor(Integer actorId, String actorName) {
        this.actorId = actorId;
        this.actorName = actorName;
    }

    public Actor() {
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId=" + actorId +
                ", actorName='" + actorName + '\'' +
                '}';
    }
}
