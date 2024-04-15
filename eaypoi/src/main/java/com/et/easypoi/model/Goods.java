package com.et.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Goods implements Serializable {



    @Excel(name = "NO",needMerge = true ,width = 20)
    private Integer no;

    @Excel(name = "name",needMerge = true ,width = 20)
    private String name;

    @Excel(name = "shelfLife",width = 20,needMerge = true ,exportFormat = "yyyy-MM-dd")
    private Date shelfLife;

    @ExcelCollection(name = "goodTypes")
    private List<GoodType> goodTypes;

}
