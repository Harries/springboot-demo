package com.et.easypoi.service;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.CellStyle;

public interface IExcelExportStyler {
    /**
     * heder style
     * @param headerColor
     * @return
     */
    public CellStyle getHeaderStyle(short headerColor);
    /**
     * title style
     * @param color
     * @return
     */
    public CellStyle getTitleStyle(short color);
    /**
     * getstyle
     * @param Parity
     * @param entity
     * @return
     */
    public CellStyle getStyles(boolean Parity, ExcelExportEntity entity);
}