package com.et.geotools.pojos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>shp数据模型对象 -- 针对读</p>
 * @author Appleyk
 * @blob https://blog.csdn.net/appleyk
 * @date Created on 下午 2018年10月24日16:31:30
 */
public class ShpDatas {

    private String name;

    /** 属性【字段】集合*/
    private List<Map<String,Object>> props;

    /** shp文件路径地址*/
    private String shpPath;

    public  ShpDatas(){
        props = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getProps() {
        return props;
    }

    public void setProps(List<Map<String, Object>> props) {
        this.props = props;
    }

    public void addProp(Map<String,Object> prop){
        this.props.add(prop);
    }

    public String getShpPath() {
        return shpPath;
    }

    public void setShpPath(String shpPath) {
        this.shpPath = shpPath;
    }

}
