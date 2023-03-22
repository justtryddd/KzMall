package com.gyb.websockt;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @date 2023/3/21 - 19:06
 */
@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    //前端发送请求建立WebSocket连接，就会执行@OnOpen方法
    @OnOpen
    public void open(@PathParam("oid") String oid, Session session){
        sessionMap.put(oid,session);
    }

    //前端关闭⻚⾯或者主动关闭webSocket连接，都会执⾏close
    @OnClose
    public void close(@PathParam("oid") String oid){
        sessionMap.remove(oid);
    }

    /*
    websocket两端是长连接，再渲染支付二维码时就创建了Websocket连接。当我们回调接口收到支付成功时，
    通过socket先前端发送特数意义字符告知前端支付成功可以关机socket连接了
    */
    public static void sendMsg(String orderId,String msg){
        try {
            Session session = sessionMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
