package com.fish.fishcrawler;

import com.fish.dao.DBTool;
import com.fish.dao.testdao;
import com.fish.util.CrawlerHelper;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws Exception {
        //String abc = DBTool.getSession().getMapper(testdao.class).GetAllEmployees();
        logger.info("Start Crawler...");
        String crawlStorageFolder = CrawlerHelper.crawlStorageFolder;//"D:\\Project\\crawler";
        int numberOfCrawlers = 1;

        CrawlConfig jdConfig = new CrawlConfig();
        jdConfig.setCrawlStorageFolder(crawlStorageFolder+"\\jd");
        jdConfig.setPolitenessDelay(1000);
        jdConfig.setMaxDepthOfCrawling(1);
        jdConfig.setMaxPagesToFetch(1000);
        jdConfig.setIncludeBinaryContentInCrawling(true);
        jdConfig.setResumableCrawling(false);
        PageFetcher jdPageFetcher = new PageFetcher(jdConfig);

        CrawlConfig amazonCNConfig = new CrawlConfig();
        amazonCNConfig.setCrawlStorageFolder(crawlStorageFolder+"\\amazoncn");
        amazonCNConfig.setPolitenessDelay(1000);
        amazonCNConfig.setMaxDepthOfCrawling(1);
        amazonCNConfig.setMaxPagesToFetch(1000);
        amazonCNConfig.setIncludeBinaryContentInCrawling(true);
        amazonCNConfig.setResumableCrawling(false);
        PageFetcher amazonCNPageFetcher = new PageFetcher(amazonCNConfig);

        CrawlConfig amazonJPConfig = new CrawlConfig();
        amazonJPConfig.setCrawlStorageFolder(crawlStorageFolder+"\\amazonjp");
        amazonJPConfig.setPolitenessDelay(1000);
        amazonJPConfig.setMaxDepthOfCrawling(1);
        amazonJPConfig.setMaxPagesToFetch(1000);
        amazonJPConfig.setIncludeBinaryContentInCrawling(true);
        amazonJPConfig.setResumableCrawling(false);
        amazonJPConfig.setConnectionTimeout(60000);
        amazonJPConfig.setSocketTimeout(40000);
        PageFetcher amazonJPPageFetcher = new PageFetcher(amazonJPConfig);

        CrawlConfig amazonUSConfig = new CrawlConfig();
        amazonUSConfig.setCrawlStorageFolder(crawlStorageFolder+"\\amazon");
        amazonUSConfig.setPolitenessDelay(1000);
        amazonUSConfig.setMaxDepthOfCrawling(1);
        amazonUSConfig.setMaxPagesToFetch(1000);
        amazonUSConfig.setIncludeBinaryContentInCrawling(true);
        amazonUSConfig.setResumableCrawling(false);
        amazonUSConfig.setUserAgentString("Googlebot");
        PageFetcher amazonUSPageFetcher = new PageFetcher(amazonUSConfig);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, jdPageFetcher);
        CrawlController jdcontroller = new CrawlController(jdConfig, jdPageFetcher, robotstxtServer);
        CrawlController amazonCNcontroller = new CrawlController(amazonCNConfig, amazonCNPageFetcher, robotstxtServer);
        CrawlController amazonJPcontroller = new CrawlController(amazonJPConfig, amazonJPPageFetcher, robotstxtServer);
        CrawlController amazonUScontroller = new CrawlController(amazonUSConfig, amazonUSPageFetcher, robotstxtServer);

        String jdItemId="2002883";
        String amazonCNItemId="B00QJDOLIO";
        String amazonJPItemId="B00QJDQM9U";
        String amazonUSItemId="B00OQVZDJM";
        jdcontroller.addSeed(String.format("http://item.jd.com/%s.html",jdItemId));
        jdcontroller.addSeed(String.format("http://p.3.cn/prices/mgets?skuIds=%s",jdItemId));
        amazonCNcontroller.addSeed(String.format("https://www.amazon.cn/dp/%s",amazonCNItemId));
        amazonJPcontroller.addSeed(String.format("https://www.amazon.co.jp/dp/%s",amazonJPItemId));
        amazonUScontroller.addSeed(String.format("https://www.amazon.com/dp/%s",amazonUSItemId));
        jdcontroller.startNonBlocking(JDCrawler.class, numberOfCrawlers);
        amazonCNcontroller.startNonBlocking(AmazonCNCrawler.class, numberOfCrawlers);
        amazonJPcontroller.startNonBlocking(AmazonJPCrawler.class, numberOfCrawlers);
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
