package com.fish.fishcrawler;

import com.fish.bean.Item;
import com.fish.bean.ItemPrice;
import com.fish.bean.Seller;
import com.fish.dao.DBTool;
import com.fish.dao.ItemPriceDAO;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AmazonCNCrawler extends WebCrawler
{
    private static String sellerItemId="";
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL();
        return href.endsWith("B00QJDOLIO") && !href.contains("product-reviews") && !href.contains("customer-reviews")
                && !href.contains("questions");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            String text=htmlParseData.getText();
            Document doc = Jsoup.parse(html);
            String title=doc.select("#productTitle").text();
            String price =doc.select("#priceblock_ourprice").text();
            if(price.equals(""))
            {
                return;
            }
            price=price.replaceAll("[^\\d.]+","");

            ItemPrice itemprice= new ItemPrice();
            itemprice.setItemId(Item.KINDLE_PW_BLK.getCode());
            itemprice.setItemName(Item.KINDLE_PW_BLK.getName());
            itemprice.setSellerItemName(title);
            itemprice.setPrice(Double.parseDouble(price));
            itemprice.setSellerId(Seller.AMAZON_CN.getCode());
            itemprice.setSellerName(Seller.AMAZON_CN.getName());
            itemprice.setSellerItemId("B00QJDOLIO");
            DBTool.getSession().getMapper(ItemPriceDAO.class).AddPriceLog(itemprice);
            DBTool.getSession().commit();
            logger.info(itemprice.toString());
        }
    }
}
