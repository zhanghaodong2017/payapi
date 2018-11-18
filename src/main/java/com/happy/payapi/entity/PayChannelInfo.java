package com.happy.payapi.entity;

import java.util.Date;

public class PayChannelInfo {
    private String paychannelno;

    private String channelname;

    private String paytype;

    private Integer orderno;

    private String state;

    private String remark;

    private Date updatetime;

    private Date createtime;

    public String getPaychannelno() {
        return paychannelno;
    }

    public void setPaychannelno(String paychannelno) {
        this.paychannelno = paychannelno;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}