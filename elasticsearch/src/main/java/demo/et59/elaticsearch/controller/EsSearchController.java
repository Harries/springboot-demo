package demo.et59.elaticsearch.controller;

import demo.et59.elaticsearch.service.EsSearchService;
import demo.et59.elaticsearch.document.ProductDocument;
import demo.et59.elaticsearch.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch 搜索
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:09
 */
@RestController
public class EsSearchController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private EsSearchService esSearchService;

    /**
     * 新增 / 修改索引
     * @return
     */
    @RequestMapping("save")
    public String add(@RequestBody ProductDocument productDocument) {
        esSearchService.save(productDocument);
        return "success";
    }

    /**
     * 删除索引
     * @return
     */
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        esSearchService.delete(id);
        return "success";
    }
    /**
     * 清空索引
     * @return
     */
    @RequestMapping("delete_all")
    public String deleteAll(@PathVariable String id) {
        esSearchService.deleteAll();
        return "success";
    }

    /**
     * 根据ID获取
     * @return
     */
    @RequestMapping("get/{id}")
    public ProductDocument getById(@PathVariable String id){
        return esSearchService.getById(id);
    }

    /**
     * 根据获取全部
     * @return
     */
    @RequestMapping("get_all")
    public List<ProductDocument> getAll(){
        return esSearchService.getAll();
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    @RequestMapping("query/{keyword}")
    public List<ProductDocument> query(@PathVariable String keyword){
        return esSearchService.query(keyword,ProductDocument.class);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit?keyword=无印良品荣耀&indexName=orders&fields=productName,productDesc
     * @param keyword   关键字
     * @param indexName 索引库名称
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @RequestMapping("query_hit")
    public List<Map<String,Object>> queryHit(@RequestParam String keyword, @RequestParam String indexName, @RequestParam String fields){
        String[] fieldNames = {};
        if(fields.contains(",")) fieldNames = fields.split(",");
        else fieldNames[0] = fields;
        return esSearchService.queryHit(keyword,indexName,fieldNames);
    }

    /**
     * 搜索，命中关键字高亮
     * http://localhost:8080/query_hit_page?keyword=无印良品荣耀&indexName=orders&fields=productName,productDesc&pageNo=1&pageSize=1
     * @param pageNo    当前页
     * @param pageSize  每页显示的数据条数
     * @param keyword   关键字
     * @param indexName 索引库名称
     * @param fields    搜索字段名称，多个以“，”分割
     * @return
     */
    @RequestMapping("query_hit_page")
    public Page<Map<String,Object>> queryHitByPage(@RequestParam int pageNo,@RequestParam int pageSize
                                                    ,@RequestParam String keyword, @RequestParam String indexName, @RequestParam String fields){
        String[] fieldNames = {};
        if(fields.contains(",")) fieldNames = fields.split(",");
        else fieldNames[0] = fields;
        return esSearchService.queryHitByPage(pageNo,pageSize,keyword,indexName,fieldNames);
    }

    /**
     * 删除索引库
     * @param indexName
     * @return
     */
    @RequestMapping("delete_index/{indexName}")
    public String deleteIndex(@PathVariable String indexName){
        esSearchService.deleteIndex(indexName);
        return "success";
    }
}
