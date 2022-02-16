package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.PmtCart;
import com.neu.edu.pojo.PmtProduct;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
public interface IPmtCartService extends IService<PmtCart> {
//    按userId查询
    List<PmtCart> getCartListByUserId(int id);

//    分页查询
    IPage<PmtCart> page(Integer pageNo, Integer pageSize, String name);

//    购物车列表过滤器
    List<PmtCart> cartListFilter(List<PmtCart> list);

//    购物车过滤器
    PmtCart cartFilter(PmtCart pmtcart);

//    判断该商品之前是否存在
    PmtCart isExists(PmtCart pmtcart);
}
