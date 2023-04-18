package com.gyb.stock.update.service;

import com.gyb.entity.ProductSku;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.stock.update.mapper.StockUpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @date 2023/4/11 - 23:31
 */
public class StockUpdateServiceImpl implements StockUpdateService {

    @Autowired
    private StockUpdateMapper stockUpdateMapper;

    @Override
    public int stockUpdate(List<ShoppingCartVo> list) {

        int j = 1;

        for (ShoppingCartVo sc : list) {
            Integer currentStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());
            ProductSku productSku = new ProductSku();
            productSku.setProductId(sc.getProductId());
            productSku.setStock(currentStock);
            int i = stockUpdateMapper.updateByPrimaryKeySelective(productSku);
            j *= i;
        }
        return j;
    }
}
