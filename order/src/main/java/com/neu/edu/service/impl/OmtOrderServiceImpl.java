package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.edu.mapper.OmtOrderMapper;
import com.neu.edu.pojo.OmtOrder;
import com.neu.edu.service.IOmtOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@Service
public class OmtOrderServiceImpl extends ServiceImpl<OmtOrderMapper, OmtOrder> implements IOmtOrderService {

    @Override
    public IPage<OmtOrder> page(Integer pageNo, Integer pageSize, String name) {
        QueryWrapper<OmtOrder> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {
            wrapper.like("name",name);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }


    @Override
    public List<OmtOrder> selectByStatus(String status, int userId) {
        List<OmtOrder> result = new ArrayList<>();
        String[] statusList = status.split(",");
        for (String s:statusList){
            QueryWrapper<OmtOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId).eq("status",s);
            List<OmtOrder> list = this.list(wrapper);
            for (OmtOrder order : list){
                result.add(order);
            }
        }


        return result;
    }

    @Override
    public List<OmtOrder> orderListFilters(List<OmtOrder> list) {
        List<OmtOrder> deleteList = new ArrayList<>();
        for (OmtOrder omtOrder : list){
            if (omtOrder.getActive()==0){
                deleteList.add(omtOrder);
            }
        }
        for (OmtOrder order :deleteList){
            list.remove(order);
        }
        return list;
    }

    @Override
    public OmtOrder orderFilters(OmtOrder omtOrder) {
        if (omtOrder.getActive()==1){
            return omtOrder;
        }else {
            return null;
        }
    }

    @Override
    public List<OmtOrder> getOrderList(int userId) {
        QueryWrapper<OmtOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("active",1);
        List<OmtOrder> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<OmtOrder> getOrderListByOrderNo(String orderNo) {
        QueryWrapper<OmtOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo).eq("active",1);
        return list(wrapper);
    }

    @Override
    public boolean check(String orderNo) {
        QueryWrapper<OmtOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo).eq("active",1);
        List<OmtOrder> list = this.list(wrapper);
        if (list.get(0).getStatus()==2){
            return true;
        }else {
            return false;
        }

    }

}
