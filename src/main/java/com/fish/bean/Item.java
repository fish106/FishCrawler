package com.fish.bean;

public enum Item
{
    KINDLE_PW_BLK(1,"Kindle PaperWhite 3 Black"),UNKNOWN(99,"Unknown Item");
    // 定义私有变量
    private int code;
    private String name;

    // 构造函数，枚举类型只能为私有
    private Item(int code, String name) {
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
