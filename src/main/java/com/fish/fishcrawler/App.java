package com.fish.fishcrawler;

import com.fish.util.CrawlerHelper;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws Exception {
        logger.info("Start Crawler...");
        int numberOfCrawlers = 1;

        CrawlConfig jdConfig = CrawlerHelper.GetConfig("jd");
        CrawlController jdcontroller = CrawlerHelper.GetController(jdConfig,true);
        String jdItemId="2002883";
        jdcontroller.addSeed(String.format("http://item.jd.com/%s.html",jdItemId));
        jdcontroller.addSeed(String.format("http://p.3.cn/prices/mgets?skuIds=%s",jdItemId));
        jdcontroller.startNonBlocking(JDCrawler.class, numberOfCrawlers);

        CrawlConfig amazonCNConfig = CrawlerHelper.GetConfig("amazoncn");
        CrawlController amazonCNcontroller = CrawlerHelper.GetController(amazonCNConfig,true);
        String amazonCNItemId="B00QJDOLIO";
        amazonCNcontroller.addSeed(String.format("https://www.amazon.cn/dp/%s",amazonCNItemId));
        amazonCNcontroller.startNonBlocking(AmazonCNCrawler.class, numberOfCrawlers);

        CrawlConfig amazonJPConfig = CrawlerHelper.GetConfig("amazonjp",60000,40000);
        CrawlController amazonJPcontroller = CrawlerHelper.GetController(amazonJPConfig,true);
        String amazonJPItemId="B00QJDQM9U";
        amazonJPcontroller.addSeed(String.format("https://www.amazon.co.jp/dp/%s",amazonJPItemId));
        amazonJPcontroller.startNonBlocking(AmazonJPCrawler.class, numberOfCrawlers);

        CrawlConfig amazonUSConfig = CrawlerHelper.GetConfig("amazon","Googlebot");
        CrawlController amazonUScontroller = CrawlerHelper.GetController(amazonUSConfig,true);
        String amazonUSItemId="B00OQVZDJM";
        amazonUScontroller.addSeed(String.format("https://www.amazon.com/dp/%s",amazonUSItemId));
        amazonUScontroller.startNonBlocking(AmazonUSCrawler.class, numberOfCrawlers);


        jdcontroller.waitUntilFinish();
        logger.info("JDCrawler is finished.");

        amazonCNcontroller.waitUntilFinish();
        logger.info("AmazonCNCrawler is finished.");

        amazonJPcontroller.waitUntilFinish();
        logger.info("AmazonJPCrawler is finished.");

        amazonUScontroller.waitUntilFinish();
        logger.info("AmazonUSCrawler is finished.");

    }
}
