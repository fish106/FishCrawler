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

import java.util.regex.Pattern;

public class AmazonCNCrawler extends WebCrawler
{
    private static final Pattern URL_PATTERN =  Pattern.compile("-\\d+");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL();
        boolean visitUrl = href.endsWith("B00QJDOLIO") && (href.indexOf("product-reviews") == -1);
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (URL_PATTERN.matcher(href).find() && visitUrl) {
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
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            String text=htmlParseData.getText();
            Document doc = Jsoup.parse(html);
            String title=doc.select("#productTitle").text();
            String price =doc.select("#priceblock_ourprice").text();
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
            /*Elements eles1 = doc.select("div.pls.favatar");
            HashSet<String> usernameSet=new HashSet<String>();
            for(Element item:eles1)
            {
                String username=item.select("a[data-title=用户名]").text();
                if(usernameSet.contains(username))
                {
                    continue;
                }
                usernameSet.add(username);
                logger.debug("UserName:{}",username);
                logger.debug("UserImageUrl:{}",item.select("img[nodata-echo=\"yes\"]").attr("src"));
                logger.debug("HomePage:{}",item.baseUri()+item.select("a[data-title=用户名]").attr("href"));
                logger.debug("Points:{}",item.select("dl.cl").select("dd").get(1).text());
            }
            usernameSet.clear();*/
        }

    }
}
