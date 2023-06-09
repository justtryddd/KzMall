package com.gyb.service;

import com.gyb.vo.ResultVo;
import com.gyb.entity.Orders;

import java.util.HashMap;

/**
 * @date 2023/3/20 - 20:59
 */
public interface OrderService {

    //添加限时订单,并返回支付链接等信息
    public HashMap<String,String> addOrder(Orders orders, String cids);

    //支付成功后更改订单状态
    public int updateOrderStatus(String orderId);

    //订单取消释放内存
    public void restoreSkuStock(String orderId);

/*    //根据用户ID分页查询所有订单信息
    public ResultVo listAllOrdersById(String userId,int pageNum,int limit);*/

    //显示
    public ResultVo listAllStatusOrdersById(String userId, String status, int pageNum, int limit);
}
