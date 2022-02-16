package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.UmtShipping;

import java.util.List;

public interface IUmtShippingService extends IService<UmtShipping> {
    IPage<UmtShipping> page(Integer pageNo, Integer pageSize, int user_id);
    List<UmtShipping> getListByUserId(int userId);
    List<UmtShipping> shippingListFilter(List<UmtShipping> list);
    UmtShipping shippingFilter(UmtShipping shipping);
}
