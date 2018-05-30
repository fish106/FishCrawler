package com.fish.fishcrawler;

import com.fish.bean.Item;
import com.fish.bean.ItemPrice;
import com.fish.bean.Seller;
import com.fish.bean.SysConstants;
import com.fish.dao.DBTool;
import com.fish.dao.ItemPriceDAO;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AmazonJPCrawler extends WebCrawler
{
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL();
        return href.endsWith("B00QJDQM9U") && !href.contains("ask") && !href.contains("customer-reviews");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (page.getParseData() instanceof HtmlParseData) {
            try
            {
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                String html = htmlParseData.getHtml();
                String text = htmlParseData.getText();
                Document doc = Jsoup.parse(html);
                String title = doc.select("#productTitle").text();
                String jpyprice = doc.select("#priceblock_ourprice").text();
                if(jpyprice.equals(""))
                {
                    return;
                }
                jpyprice = jpyprice.replaceAll("[^\\d.]+", "");
                double price = Double.parseDouble(jpyprice) * SysConstants.JPY_EX_RATE;

                ItemPrice itemprice = new ItemPrice();
                itemprice.setItemId(Item.KINDLE_PW_BLK.getCode());
                itemprice.setItemName(Item.KINDLE_PW_BLK.getName());
                itemprice.setSellerItemName(title);
                itemprice.setPrice(price);
                itemprice.setSellerId(Seller.AMAZON_JP.getCode());
                itemprice.setSellerName(Seller.AMAZON_JP.getName());
                itemprice.setSellerItemId("B00QJDQM9U");
                DBTool.getSession().getMapper(ItemPriceDAO.class).AddPriceLog(itemprice);
                DBTool.getSession().commit();
                logger.info(itemprice.toString());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
}
