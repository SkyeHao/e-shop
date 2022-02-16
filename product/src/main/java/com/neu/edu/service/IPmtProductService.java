package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.PmtProduct;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
public interface IPmtProductService extends IService<PmtProduct> {
//    按name模糊搜索商品列表
    List<PmtProduct> getListByName(String name);

//    按id获取商品精简数据
    PmtProduct getMainProductsById(int id);

//    按id获取商品详细信息
    PmtProduct getDetailProductsById(int id);

//    按category获取商品列表
    List<PmtProduct> getListByCategory(int categoryId);

//    PmtProduct列表过滤器
    List<PmtProduct> productListFilter(List<PmtProduct> list);

//    PmtProduct过滤器
    PmtProduct productFilter(PmtProduct product);

//    获取推荐商品
    List<PmtProduct> getDefaultProductList();
}
