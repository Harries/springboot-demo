package com.et.ipfilter.util;

import org.lionsoul.ip2region.xdb.Searcher;
import java.io.IOException;
 
public class SearcherIPUtils {
    public static String  getCachePosition(String ip) {
        return SearcherIPUtils.getCachePosition("src/main/resources/ip2region/ip2region.xdb", ip, true);
    }
 
    public static String  getPosition(String dbPath,String ip,boolean format) {
        // 1、create searcher object
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
 
        // 2、query
        try {
            String region = searcher.search(ip);
            if (format){
                return region;
            }
            String[] split = region.split("\\|");
            String s = split[0] + split[2] + split[3];
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
 
    /**
     * @Description :
     * @Author : mabo
    */
    public static String getIndexCachePosition(String dbPath, String ip, boolean format) {
        Searcher searcher = null;
        byte[] vIndex;
        try {
            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String region = searcher.search(ip);
            if (format){
                return region;
            }
            String[] split = region.split("\\|");
            String s = split[0] + split[2] + split[3];
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * @Description :
     * @Author : mabo
     */
    public static String  getCachePosition(String dbPath,String ip,boolean format) {
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
        Searcher searcher;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String region = searcher.search(ip);
            if (format){
                return region;
            }
            String[] split = region.split("\\|");
            String s = split[0] + split[2] + split[3];
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
    }
}
 