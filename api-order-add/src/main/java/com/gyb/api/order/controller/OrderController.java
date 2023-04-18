package com.gyb.api.order.controller;

import com.github.wxpay.sdk.WXPay;
import com.gyb.api.order.config.MyPayConfig;
import com.gyb.api.order.service.OrderService;
import com.gyb.beans.OrderPO;
import com.gyb.vo.ResStatus;
import com.gyb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2023/4/11 - 18:00
 */

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo addOrder(@RequestBody OrderPO order,String cids){

        ResultVo resultVo = null;
        try {
            HashMap<String, String> map = orderService.addOrder(cids, order);

            if (map != null) {
                String orderId = map.get("orderId");
                //设置当前订单信息
                HashMap<String, String> data = new HashMap<>();
                data.put("body", map.get("productNames"));  //商品描述
                data.put("out_trade_no", orderId);               //使用当前用户订单的编号作为当前支付交易的交易号
                data.put("fee_type", "CNY");                     //支付币种
                //data.put("total_fee",order.getActualAmount()*100+"");          //支付金额
                data.put("total_fee", "1");
                data.put("trade_type", "NATIVE");                //交易类型
                //data.put("notify_url","http://47.118.45.73:8080/pay/callback");           //设置支付完成时的回调方法接口
                //data.put("notify_url","http://localhost:8080/pay/callback");           //设置支付完成时的回调方法接口
                data.put("notify_url", "http://ytao.free.idcfengye.com/pay/callback");           //设置支付完成时的回调方法接口

                //发送请求，获取响应
                //微信支付：申请支付连接
                WXPay wxPay = new WXPay(new MyPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                map.put("payUrl", resp.get("code_url"));
                //orderInfo中包含：订单编号，购买的商品名称，支付链接
                resultVo = new ResultVo(ResStatus.OK, "提交订单成功！", map);

            } else {
                resultVo = new ResultVo(ResStatus.NO, "提交订单失败！", null);
            }
        }    catch (
                SQLException e) {
            resultVo = new ResultVo(ResStatus.NO,"提交订单失败！",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVo;
    }
}
