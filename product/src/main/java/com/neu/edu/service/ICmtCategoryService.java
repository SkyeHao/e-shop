package com.neu.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.CmtCategory;

import java.util.List;

/**
 * <p>
 * 商品类别表 服务类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
public interface ICmtCategoryService extends IService<CmtCategory> {
//    获取子节点列表
    List<CmtCategory> getListByParentId(int parentId);
//    类别列表过滤器
    List<CmtCategory> catogeryListFilter(List<CmtCategory> list);
}
