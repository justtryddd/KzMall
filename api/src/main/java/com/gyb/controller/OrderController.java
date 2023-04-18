package com.gyb.controller;

import com.github.wxpay.sdk.WXPay;
import com.gyb.service.OrderService;
import com.gyb.vo.ResultVo;
import com.gyb.config.WeChatPayConfig;
import com.gyb.entity.Orders;
import com.gyb.vo.ResStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2023/3/21 - 0:58
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供订单相关操作接口",tags = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("提交订单接口")
    @PostMapping("/add")
    public ResultVo addOrderByEntityAndCids(@RequestBody Orders orders, String cids, @RequestHeader("token")String token){
        ResultVo resultVo = null;

        try {
            HashMap<String, String> map = orderService.addOrder(orders, cids);

            if(map != null) {
                //订单对微信支付接口的实现
                HashMap<String, String> data = new HashMap<>();
                data.put("body", map.get("productName")); //商品描述
                data.put("out_trade_no", map.get("orderId")); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
                data.put("fee_type", "CNY"); //⽀付币种
                data.put("total_fee", orders.getActualAmount() * 100 + "");//⽀付⾦额
                data.put("trade_type", "NATIVE"); //交易类型
                data.put("notify_url", "/pay/success"); //设置⽀付完成时的回调⽅法接⼝

                //发送请求，获取响应
                //微信⽀付：申请⽀付连接
                WXPay wxPay = new WXPay(new WeChatPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                map.put("payUrl", resp.get("code_url"));

                resultVo = new ResultVo(ResStatus.OK, "提交订单成功，应跳转到支付页面", map);
            }else {
                resultVo = new ResultVo(ResStatus.NO, "提交订单失败", null);
            }
        }
        catch (SQLException e){
            resultVo = new ResultVo(ResStatus.NO, "提交订单失败", null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultVo;
    }

/*
    @ApiOperation("显示所有订单信息")
    @PostMapping("/list")
    public ResultVo listAllOrders(String userId,int pageNum,int limit,@RequestHeader("token")String token){
        ResultVo resultVo = orderService.listAllOrdersById(userId,pageNum,limit);
        return resultVo;
    }*/

    @ApiOperation("订单接口查询")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "userId", value = "用户ID",required = true),
            @ApiImplicitParam(dataType = "string",name = "status", value = "订单状态",required = false),
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页条数",required = true)
    })
    public ResultVo listAllDiffStatusOrders(String userId,String status,int pageNum,int limit,@RequestHeader("token")String token){
        ResultVo resultVo = orderService.listAllStatusOrdersById(userId,status,pageNum,limit);
        return resultVo;
    }
}
