package deep.com.deepexi.service;

import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import deep.com.deepexi.domain.eo.Product;

public interface ProductService {

    PageBean getProductList(Integer page, Integer size, Integer price);

    Product getProductById(String id);

    Integer createProduct(Product product);

    Boolean deleteProductById(String id);

    void testError();

    void testRpcError() throws ApplicationException;
}
