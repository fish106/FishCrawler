package com.fish.bean;

public enum Seller
{
    JD(1,"京东商城"),AMAZON_CN(2,"亚马逊中国"),AMAZON(3,"美国亚马逊"), AMAZON_JP(4,"日本亚马逊");
    // 定义私有变量
    private int code;
    private String name;

    // 构造函数，枚举类型只能为私有
    Seller(int code, String name) {
        this.setCode(code);
        this.setName(name);
    }

    public static String GetNameFromCode(int code) {
        for (Seller c : Seller.values()) {
            if (c.getCode() == code) {
                return c.getName();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
