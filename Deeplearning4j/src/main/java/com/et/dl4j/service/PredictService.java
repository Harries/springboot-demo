package com.et.dl4j.service;

import org.springframework.web.multipart.MultipartFile;


public interface PredictService {

    /**
     * 取得上传的图片，做转换后识别成数字
     * @param file 上传的文件
     * @param isNeedRevert 是否要做反色处理
     * @return
     */
    int predict(MultipartFile file, boolean isNeedRevert) throws Exception ;
}
