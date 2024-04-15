package com.et.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName GoodType
 * @Description todo
 * @date 2024年04月15日 11:02
 */
@Data
public class GoodType {
    @Excel(name = "typeId",  width = 20,height = 8)
    private String typeId;
    @Excel(name = "typeName",  width = 20,height = 8)
    private String typeName;
}
