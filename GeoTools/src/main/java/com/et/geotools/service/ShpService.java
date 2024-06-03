package com.et.geotools.service;


import com.et.geotools.geotools.ShpTools;
import com.et.geotools.pojos.ShpDatas;
import com.et.geotools.pojos.ShpInfo;
import com.et.geotools.result.ResponseMessage;
import com.et.geotools.result.ResponseResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Shp业务逻辑类</p>
 * @author Appleyk
 * @blob https://blog.csdn.net/appleyk
 * @date Created on 下午 2018年10月24日17:17:46
 */
@Service
public class ShpService {

    public ResponseResult getShpDatas(String shpPath, Integer limit) throws  Exception{
        ShpDatas shpDatas = ShpTools.readShpByPath(shpPath, limit);
        return new ResponseResult(ResponseMessage.OK,shpDatas);
    }

    public void showShp(String shpPath,String imagePath,String color, HttpServletResponse response) throws  Exception{
        ShpTools.shp2Image(shpPath, imagePath ,color,response);
    }

    public ResponseResult writeShp(ShpInfo shpInfo) throws  Exception{
        return  ShpTools.writeShpByGeom(shpInfo);
    }
}
