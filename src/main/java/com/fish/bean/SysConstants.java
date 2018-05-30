package com.fish.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class SysConstants
{
    SysConstants(){}

    public static Double USD_EX_RATE;
    public static Double JPY_EX_RATE;
    public static String CRAWL_STORAGE_PATH;
    static
    {
        try
        {
            Properties prop = new Properties();
            InputStream in = Object.class.getResourceAsStream("/appconfig.properties");
            prop.load(in);
            USD_EX_RATE = Double.parseDouble(prop.getProperty("USD_EX_RATE").trim());
            JPY_EX_RATE = Double.parseDouble(prop.getProperty("JPY_EX_RATE").trim());
            CRAWL_STORAGE_PATH = prop.getProperty("Root_Storage_Folder").trim();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
