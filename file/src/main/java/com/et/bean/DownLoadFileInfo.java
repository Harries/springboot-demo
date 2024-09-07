package com.et.bean;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName DownLoadFileInfo
 * @Description todo
 * @date 2024/09/06/ 14:35
 */

public class DownLoadFileInfo {
    public long fSize;
    public String fName;

    public DownLoadFileInfo(long fSize, String fName) {
        this.fSize = fSize;
        this.fName = fName;
    }
}
