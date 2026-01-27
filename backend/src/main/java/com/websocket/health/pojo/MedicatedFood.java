package com.websocket.health.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName medicated_food
 */
@Data
public class MedicatedFood implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 药膳名称
     */
    private String name;

    /**
     * 药膳类别
1-汤菜类
2-粥饭类
3-茶饮类
4-其他类
5-糕点类
     */
    private Integer type;

    /**
     * 来源
     */
    private String source;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 
     */
    private String photo;

    /**
     * 
     */
    private String material;

    /**
     * 
     */
    private String method;

    /**
     * 展示简介
     */
    private String good;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MedicatedFood other = (MedicatedFood) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getIntroduce() == null ? other.getIntroduce() == null : this.getIntroduce().equals(other.getIntroduce()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getMaterial() == null ? other.getMaterial() == null : this.getMaterial().equals(other.getMaterial()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getGood() == null ? other.getGood() == null : this.getGood().equals(other.getGood()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getIntroduce() == null) ? 0 : getIntroduce().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getMaterial() == null) ? 0 : getMaterial().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getGood() == null) ? 0 : getGood().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", source=").append(source);
        sb.append(", introduce=").append(introduce);
        sb.append(", photo=").append(photo);
        sb.append(", material=").append(material);
        sb.append(", method=").append(method);
        sb.append(", good=").append(good);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}