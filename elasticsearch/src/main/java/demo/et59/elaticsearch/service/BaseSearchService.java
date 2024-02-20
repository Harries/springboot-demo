package demo.et59.elaticsearch.service;

import demo.et59.elaticsearch.page.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:32
 */
public interface BaseSearchService<T> {

    /**
     * 搜 索
     * @param keyword
     * @param clazz
     * @return
     */
    List<T> query(String keyword, Class<T> clazz);

    /**
     * 搜索高亮显示
     * @param keyword       关键字
     * @param indexName     索引库
     * @param fieldNames    搜索的字段
     * @return
     */
    List<Map<String,Object>> queryHit(String keyword, String indexName, String ... fieldNames);

    /**
     * 搜索高亮显示，返回分页
     * @param pageNo        当前页
     * @param pageSize      每页显示的总条数
     * @param keyword       关键字
     * @param indexName     索引库
     * @param fieldNames    搜索的字段
     * @return
     */
    Page<Map<String,Object>> queryHitByPage(int pageNo,int pageSize,String keyword, String indexName, String ... fieldNames);

    /**
     * 删除索引库
     * @param indexName
     * @return
     */
    void deleteIndex(String indexName);
}
