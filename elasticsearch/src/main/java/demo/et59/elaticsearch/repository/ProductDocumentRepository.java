package demo.et59.elaticsearch.repository;

import demo.et59.elaticsearch.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 17:35
 */
@Component
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument,String> {
}
