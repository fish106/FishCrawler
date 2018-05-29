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

import java.util.regex.Pattern;

public class AmazonUSCrawler extends WebCrawler
{
    private static final Pattern URL_PATTERN =  Pattern.compile("-\\d+");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL();
        boolean visitUrl = href.endsWith("B00OQVZDJM") && (href.indexOf("ask") == -1) && (href.indexOf("customer-reviews") == -1);
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (visitUrl) {
            return true;
        }
        else
        {
            return false;
        }
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
                String usdprice = doc.select("#priceblock_ourprice").text();
                usdprice = usdprice.replaceAll("[^\\d.]+", "");
                double price = Double.parseDouble(usdprice) * SysConstants.USD_EX_RATE;

                ItemPrice itemprice = new ItemPrice();
                itemprice.setItemId(Item.KINDLE_PW_BLK.getCode());
                itemprice.setItemName(Item.KINDLE_PW_BLK.getName());
                itemprice.setSellerItemName(title);
                itemprice.setPrice(price);
                itemprice.setSellerId(Seller.AMAZON.getCode());
                itemprice.setSellerName(Seller.AMAZON.getName());
                itemprice.setSellerItemId("B00OQVZDJM");
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
