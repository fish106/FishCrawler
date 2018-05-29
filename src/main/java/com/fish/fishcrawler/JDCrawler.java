package com.fish.fishcrawler;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fish.bean.Item;
import com.fish.bean.ItemPrice;
import com.fish.bean.JDPrice;
import com.fish.bean.Seller;
import com.fish.dao.DBTool;
import com.fish.dao.ItemPriceDAO;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JDCrawler extends WebCrawler
{
    private static final Pattern URL_PATTERN =  Pattern.compile("-\\d+");
    private String itemTitle = "";
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean visitUrl = href.endsWith("2002883.html") || href.endsWith("2002883");
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
            String itemname =doc.select("div.sku-name").text();
            itemTitle=itemname;
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
        else
        {
            String jsonData=new String(page.getContentData());
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, JDPrice.class);
            try
            {
                List<JDPrice> jdPriceList=(List<JDPrice>)mapper.readValue(page.getContentData(), javaType);
                JDPrice jdprice=jdPriceList.get(0);
                ItemPrice itemprice= new ItemPrice();
                itemprice.setItemId(Item.KINDLE_PW_BLK.getCode());
                itemprice.setItemName(Item.KINDLE_PW_BLK.getName());
                itemprice.setSellerItemName(itemTitle);
                String price = jdprice.getTpp()!=null?jdprice.getTpp():jdprice.getP();
                price=price.replaceAll("[^\\d.]+","");
                itemprice.setPrice(Double.parseDouble(price));
                itemprice.setSellerId(Seller.JD.getCode());
                itemprice.setSellerName(Seller.JD.getName());
                itemprice.setSellerItemId(jdprice.getId().substring(2));
                DBTool.getSession().getMapper(ItemPriceDAO.class).AddPriceLog(itemprice);
                DBTool.getSession().commit();
                logger.info(itemprice.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
