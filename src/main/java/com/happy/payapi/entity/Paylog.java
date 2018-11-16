package com.happy.payapi.entity;

import java.util.Date;

public class Paylog extends PaylogKey {
    private Integer qrcodeid;

    private String paytype;

    private Integer appmount;

    private String subject;

    private String transdata;

    private String notifyurl;

    private String paystate;

    private Date paytime;

    private String payuser;

    private String thirdtradeno;

    private String notifystate;

    private String notifydata;

    private Date notifytime;

    private String remark;

    private Date updatetime;

    private Date createtime;

    public Integer getQrcodeid() {
        return qrcodeid;
    }

    public void setQrcodeid(Integer qrcodeid) {
        this.qrcodeid = qrcodeid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Integer getAppmount() {
        return appmount;
    }

    public void setAppmount(Integer appmount) {
        this.appmount = appmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTransdata() {
        return transdata;
    }

    public void setTransdata(String transdata) {
        this.transdata = transdata;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public String getPayuser() {
        return payuser;
    }

    public void setPayuser(String payuser) {
        this.payuser = payuser;
    }

    public String getThirdtradeno() {
        return thirdtradeno;
    }

    public void setThirdtradeno(String thirdtradeno) {
        this.thirdtradeno = thirdtradeno;
    }

    public String getNotifystate() {
        return notifystate;
    }

    public void setNotifystate(String notifystate) {
        this.notifystate = notifystate;
    }

    public String getNotifydata() {
        return notifydata;
    }

    public void setNotifydata(String notifydata) {
        this.notifydata = notifydata;
    }

    public Date getNotifytime() {
        return notifytime;
    }

    public void setNotifytime(Date notifytime) {
        this.notifytime = notifytime;
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