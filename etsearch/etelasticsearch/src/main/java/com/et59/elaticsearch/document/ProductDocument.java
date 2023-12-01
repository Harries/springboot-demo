package com.et59.elaticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品实体
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:22
 */
@Document(indexName = "orders", type = "product")
@Mapping(mappingPath = "productIndex.json") // 解决IK分词不能使用问题
public class ProductDocument implements Serializable {

    @Id
    private String id;
    //@Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String productName;
    //@Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String productDesc;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
