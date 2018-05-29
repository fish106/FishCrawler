package com.fish.util;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CrawlerHelper
{
    public static String crawlStorageFolder;//"D:\\Project\\crawler";
    static {
        try {
            Properties prop = new Properties();
            InputStream in = Object.class.getResourceAsStream("/appconfig.properties");
            prop.load(in);
            crawlStorageFolder = prop.getProperty("Root_Storage_Folder").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CrawlConfig GetConfig(String folderName, Integer connectionTimeout, Integer socketTimeout)
    {
        CrawlConfig config=new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder+"\\amazonjp");
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(1);
        config.setMaxPagesToFetch(1000);
        config.setIncludeBinaryContentInCrawling(true);
        config.setResumableCrawling(false);
        config.setConnectionTimeout(connectionTimeout);
        config.setSocketTimeout(socketTimeout);
        return config;
    }

    public CrawlConfig GetConfig(String folderName)
    {
        return this.GetConfig(folderName,20000,30000);
    }

    public CrawlController GetController(CrawlConfig crawlConfig)
    {
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController crawlController = null;
        try
        {
            crawlController = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return crawlController;
    }
}
