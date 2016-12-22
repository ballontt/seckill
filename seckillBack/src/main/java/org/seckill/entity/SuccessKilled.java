package org.seckill.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by ballontt on 2016/12/9.
 */
//@Component
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private int state ;
    private Date createTime;
    private Seckill secKill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSecKill() {
        return secKill;
    }

    public void setSecKill(Seckill secKill) {
        this.secKill = secKill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", secKill=" + secKill +
                '}';
    }
}
