package com.fish.bean;

import java.sql.Timestamp;

public class ItemPrice
{
    private Integer itemId;
    private Double price;
    private Integer sellerId;
    private String sellerName;
    private String sellerItemId;
    private String sellerItemName;
    private String itemName;
    private Timestamp logTime;

    @Override
    public String toString()
    {
        return String.format("ItemPrice [ItemId=%d, Price=%.2f, Seller=%s, SellerItemId=%s, SellerItemName=%s]",
                getItemId(), getPrice(), getSellerName(), getSellerItemId(), getSellerItemName());
    }
    public Integer getItemId()
    {
        return itemId;
    }

    public void setItemId(Integer itemId)
    {
        this.itemId = itemId;
        switch (itemId)
        {
            case 1:
                this.itemName="Kindle Paperwhite 3 black";
                break;
            default:
                this.itemName="Unknown";
                break;
        }
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getSellerItemId()
    {
        return sellerItemId;
    }

    public void setSellerItemId(String sellerItemId)
    {
        this.sellerItemId = sellerItemId;
    }

    public String getSellerItemName()
    {
        return sellerItemName;
    }

    public void setSellerItemName(String sellerItemName)
    {
        this.sellerItemName = sellerItemName;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public Integer getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(Integer sellerId)
    {
        this.sellerId = sellerId;
    }

    public void setSellerName(String sellerName)
    {
        this.sellerName = sellerName;
    }

    public String getSellerName()
    {
        return sellerName;
    }
}
