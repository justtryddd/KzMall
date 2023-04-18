package com.gyb.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.gyb.service.OrderService;
import com.gyb.websockt.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2023/3/21 - 18:23
 */

@RestController
@RequestMapping("/pay")
@Api(value = "接受第三方支付API的回调内容接口",tags = "支付服务")
public class PayController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("")
    @RequestMapping("/callback")
    public String PaySuccess(HttpServletRequest request) throws Exception {
        // 1.接收微信支付平台传递的数据（使用request的输入流接收）。没办法人家提供啥咋就用啥，没有json格式
        System.out.println("--------------------callback");
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while((len = is.read(bs))!=-1){
            builder.append(new String(bs,0,len));
        }
        String s = builder.toString();
        //使用帮助类将xml接口的字符串装换成map
        Map<String, String> map = WXPayUtil.xmlToMap(s);

        //判断是否支付成功,支付成功就修改订单状态
        if(map != null && map.get("result_code").equals("success")){
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId);
            //告诉前端支付成功
            WebSocketServer.sendMsg(orderId,"1");

            //.响应微信⽀付平台
            if(i>0){
                HashMap<String,String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                resp.put("appid",map.get("appid"));
                resp.put("result_code","success");
                return WXPayUtil.mapToXml(resp);
            }
        }
        return null;
    }

}
