package com.gyb.mapper;

import com.gyb.entity.Orders;
import com.gyb.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper extends GeneralDao<Orders> {

/*

    //根据用户ID分页查询所有订单信息
    public List<Orders> selectAllOrdersByID(@Param("userID") String userId,
                                            @Param("start") int start,
                                            @Param("limit")int limit);
*/


    //根据用户ID和订单状态码分页查询所有特定状态订单信息
    public List<Orders> selectAllOrdersByIDAndStatus(@Param("userId")String userId,
                                                     @Param("status")String status,
                                                     @Param("start")int start,
                                                     @Param("limit") int limit);



/*      记录一下傻逼的自己
    //根据用户ID分页查询所有待发货订单信息
    public List<Orders> selectAllOrdersByIDAndStatus2(String userId, String status,int start, int limit);


    //根据用户ID分页查询所有待收货订单信息
    public List<Orders> selectAllOrdersByIDAndStatus3(String userId,String status,int start, int limit);


    //根据用户ID分页查询所有待评价订单信息
    public List<Orders> selectAllOrdersByIDAndStatus4(String userId,String status,int start, int limit);


    //根据用户ID分页查询所有已关闭订单信息
    public List<Orders> selectAllOrdersByIDAndStatus6(String userId,String status,int start, int limit);
*/


}