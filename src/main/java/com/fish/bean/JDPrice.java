package com.fish.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JDPrice
{
    private String op;//原价
    private String m;//最高价
    private String tpp;//Plus会员价
    private String id;//商品ID
    private String up;//价格优惠方式，tpp为Plus会员
    private String p;//现价

    public String getOp()
    {
        return op;
    }

    public void setOp(String op)
    {
        this.op = op;
    }

    public String getM()
    {
        return m;
    }

    public void setM(String m)
    {
        this.m = m;
    }

    public String getTpp()
    {
        return tpp;
    }

    public void setTpp(String tpp)
    {
        this.tpp = tpp;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUp()
    {
        return up;
    }

    public void setUp(String up)
    {
        this.up = up;
    }

    public String getP()
    {
        return p;
    }

    public void setP(String p)
    {
        this.p = p;
    }
}
