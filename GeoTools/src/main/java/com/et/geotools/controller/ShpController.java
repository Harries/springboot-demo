package com.et.geotools.controller;


import com.et.geotools.pojos.ShpInfo;
import com.et.geotools.result.ResponseResult;
import com.et.geotools.service.ShpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RequestMapping("/shper")
@RestController
public class ShpController {

    @Autowired
    private ShpService shpService;

    @GetMapping("/hello")
    public  String sayHello(){
        return "Hello Appleyk's Controller !";
    }


    /**
     * 写一个shp文件
     * @param shpInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/write")
    public ResponseResult write(@RequestBody ShpInfo shpInfo) throws  Exception{
       return  shpService.writeShp(shpInfo);
    }

    /**
     * 查询一个shp文件
     * @param shpFilePath 文件绝对路径
     * @param limit 指定显示多少条shp特征【features】
     * @return
     * @throws Exception
     */
    @GetMapping("/query")
    public ResponseResult  query(@RequestParam(value = "path",required = true) String shpFilePath,
                                   @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit ) throws  Exception{
            return  shpService.getShpDatas(shpFilePath,limit);
    }

    /**
     * 将shp文件转换成png图片，图片或写入文件或通过response输出到界面【比如，客户端浏览器】
     * @param path shp文件路径
     * @param imagePath 如果imagePath不等于空，则shp文件转成图片文件存储进行存
     * @param color 渲染颜色
     */
    @GetMapping("/show")
    public  void show(@RequestParam(value = "path",required = true) String path,
                      @RequestParam(value = "imagePath",required = false) String imagePath,
                      @RequestParam(value = "color",required = false) String color,
                      HttpServletResponse response) throws  Exception{

        // 设置响应消息的类型
        response.setContentType("image/png");

        // 设置页面不缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        shpService.showShp(path, imagePath,color ,response);
    }

}
