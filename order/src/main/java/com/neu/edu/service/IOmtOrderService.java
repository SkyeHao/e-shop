package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.OmtOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
public interface IOmtOrderService extends IService<OmtOrder> {

    IPage<OmtOrder> page(Integer pageNo, Integer pageSize, String name);

    //    按状态获取订单
    List<OmtOrder> selectByStatus(String status, int userId);

//    orderList过滤器
    List<OmtOrder> orderListFilters(List<OmtOrder> list);

//    order过滤器
    OmtOrder orderFilters(OmtOrder omtOrder);

//    获取用户全部订单
    List<OmtOrder> getOrderList(int userId);

//    根据订单编号获取订单
    List<OmtOrder> getOrderListByOrderNo(String orderNo);

//    检查支付状态
    boolean check(String orderNo);

}
