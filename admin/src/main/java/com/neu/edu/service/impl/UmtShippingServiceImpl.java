package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.UmtShippingMapper;
import com.neu.edu.pojo.UmtHistory;
import com.neu.edu.pojo.UmtShipping;
import com.neu.edu.service.IUmtShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhy
 * @since 2021-07-13
 */
@Service
public class UmtShippingServiceImpl extends ServiceImpl<UmtShippingMapper, UmtShipping> implements IUmtShippingService {
    @Resource
    IUmtShippingService iUmtShippingService;
    @Override
    public IPage<UmtShipping> page(Integer pageNo, Integer pageSize, int user_id) {
        QueryWrapper<UmtShipping> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(String.valueOf(user_id))) {
            wrapper.like("user_id",user_id);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public List<UmtShipping> getListByUserId(int userId) {
        QueryWrapper<UmtShipping> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("active",1);
        return list(wrapper);
    }


    @Override
    public List<UmtShipping> shippingListFilter(List<UmtShipping> list) {
        List<UmtShipping> deleteList = new ArrayList<>();
        for (UmtShipping shipping : list){
            if (shipping.getActive()==0){
                deleteList.add(shipping);
            }
        }
        for (UmtShipping shipping :deleteList){
            list.remove(shipping);
        }
        return list;

    }

    @Override
    public UmtShipping shippingFilter(UmtShipping shipping) {
        if (shipping==null){
            return null;
        }
        if (shipping.getActive()==1){
            return shipping;
        }else {
            return null;
        }
    }

}
