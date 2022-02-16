package com.neu.edu.controller;

import com.neu.edu.pojo.OmtOrder;
import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.service.IOmtOrderService;
import com.neu.edu.service.IPmtProductService;
import com.neu.edu.util.ResultJson;
import io.minio.errors.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@RestController
@RequestMapping("/order")
public class OmtOrderController {

    @Resource
    IPmtProductService iPmtProductService;

    @Resource
    IOmtOrderService iOmtOrderService;

    /**
     * 获取用户全部订单
     * /order/selectByUserId
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/selectByUserId")
    ResultJson selectByUserId(int userId) throws InterruptedException {
        return ResultJson.success(iOmtOrderService.orderListFilters(iOmtOrderService.getOrderList(userId))) ;
    }



    /**
     * 根据状态获取订单
     * /order/selectByStatus
     * @param status
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/selectByStatus")
    ResultJson selectByStatus(String status,int userId) throws InterruptedException {
        return ResultJson.success(iOmtOrderService.orderListFilters(iOmtOrderService.selectByStatus(status,userId))) ;
    }


    /**
     * 添加订单
     * /order/add
     * @param omtOrder
     */

    @PostMapping("/add")
    ResultJson add(OmtOrder omtOrder) {
        String orderNo = omtOrder.getOrderNo();
        System.out.println("orderNo:"+orderNo);
        omtOrder.setPaymentType(1);
        LocalDateTime time = LocalDateTime.now();
        omtOrder.setCreateTime(time);
//        修改库存
        PmtProduct pmtProduct = iPmtProductService.getById(omtOrder.getProductId());
        int stock = pmtProduct.getStock();
        int need = omtOrder.getQuantity();
        System.out.println(stock);
        System.out.println(need);
        pmtProduct.setStock(stock-need);
        iPmtProductService.updateById(pmtProduct);
        if ("".equals(orderNo)){
//        生成订单编号
            StringBuilder builder = new StringBuilder();
            LocalDateTime now = LocalDateTime.now();
            builder.append(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
            builder.append(RandomStringUtils.random(8,false,true));
            orderNo = builder.toString();
            omtOrder.setOrderNo(orderNo);
            omtOrder.setActive(1);
            omtOrder.setStatus(1);
            iOmtOrderService.save(omtOrder);
        }else {
            omtOrder.setActive(1);
            omtOrder.setStatus(1);
            iOmtOrderService.save(omtOrder);
        }
        return ResultJson.success(omtOrder,"添加订单成功");
    }

    /**
     * 获取某个订单
     * /order/select
     * @param id
     * @return
     */

    @GetMapping("/select")
    ResultJson select(int id) {
        return ResultJson.success(iOmtOrderService.orderFilters(iOmtOrderService.getById(id)));
    }

    /**
     * 修改订单状态
     * @param id
     * @param oldStatus
     * @return
     */

    @PostMapping("/update")
    ResultJson update(int id, int oldStatus) {
        OmtOrder omtOrder = iOmtOrderService.getById(id);
        if(oldStatus==1){
            LocalDateTime time = LocalDateTime.now();
            omtOrder.setEndTime(time);
            omtOrder.setStatus(0);
//            取消订单时修改库存
            PmtProduct pmtProduct = iPmtProductService.getById(iOmtOrderService.getById(id).getProductId());
            pmtProduct.setStock(pmtProduct.getStock()+iOmtOrderService.getById(id).getQuantity());
            iPmtProductService.updateById(pmtProduct);
            return ResultJson.success(iOmtOrderService.updateById(omtOrder),"修改订单成功");
        }else if (oldStatus ==4){
            LocalDateTime time = LocalDateTime.now();
            omtOrder.setCloseTime(time);
            omtOrder.setStatus(5);
            return ResultJson.success(iOmtOrderService.updateById(omtOrder),"修改订单成功");
        }
        return ResultJson.success(iOmtOrderService.updateById(omtOrder),"修改订单失败");
    }

    /**
     * 删除订单信息
     * /order/delete
     * @param id
     * @return
     */

    @PostMapping("/delete")
    ResultJson delete(int id) {
        OmtOrder omtOrder = iOmtOrderService.getById(id);
        omtOrder.setActive(0);
        return ResultJson.success(iOmtOrderService.updateById(omtOrder),"删除订单成功");
    }
}
