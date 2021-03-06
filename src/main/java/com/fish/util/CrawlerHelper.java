package com.fish.util;

import com.fish.bean.SysConstants;
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
    static {
        try {
            Properties prop = new Properties();
            InputStream in = Object.class.getResourceAsStream("/appconfig.properties");
            prop.load(in);
            SysConstants.CRAWL_STORAGE_PATH = prop.getProperty("Root_Storage_Folder").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CrawlConfig GetConfig(String folderName, Integer connectionTimeout, Integer socketTimeout, String userAgent)
    {
        CrawlConfig config=new CrawlConfig();
        config.setCrawlStorageFolder(SysConstants.CRAWL_STORAGE_PATH+"\\"+folderName);
        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(1);
        config.setMaxPagesToFetch(1000);
        config.setIncludeBinaryContentInCrawling(true);
        config.setResumableCrawling(false);
        config.setConnectionTimeout(connectionTimeout);
        config.setSocketTimeout(socketTimeout);
        if(userAgent != null && !userAgent.equals(""))
        {
            config.setUserAgentString(userAgent);
        }
        return config;
    }

    public static CrawlConfig GetConfig(String folderName, Integer connectionTimeout,Integer socketTimeout)
    {
        return GetConfig(folderName,connectionTimeout,socketTimeout,"");
    }
    public static CrawlConfig GetConfig(String folderName, String userAgent)
    {
        return GetConfig(folderName,20000,30000, userAgent);
    }

    public static CrawlConfig GetConfig(String folderName)
    {
        return GetConfig(folderName,20000,30000,"");
    }

    public static CrawlController GetController(CrawlConfig crawlConfig, boolean useRobotstxt)
    {
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        robotstxtConfig.setEnabled(useRobotstxt);
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
