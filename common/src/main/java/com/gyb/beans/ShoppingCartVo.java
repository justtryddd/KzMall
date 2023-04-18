package com.gyb.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @date 2023/4/11 - 21:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartVo {
    private Integer cartId;
    private String productId;
    private String skuId;
    private String userId;
    private String cartNum;
    private String cartTime;
    private BigDecimal productPrice;
    private String skuProps;

    private String productName;
    private String productImg;
    private double originalPrice;
    private double sellPrice;
    private String skuName;
    private int skuStock;  //库存
}
