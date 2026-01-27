package com.websocket.health.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName benefit
 */
@Data
public class Benefit implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 药膳益处
     */
    private String benefit;

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
        Benefit other = (Benefit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBenefit() == null ? other.getBenefit() == null : this.getBenefit().equals(other.getBenefit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBenefit() == null) ? 0 : getBenefit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", benefit=").append(benefit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}