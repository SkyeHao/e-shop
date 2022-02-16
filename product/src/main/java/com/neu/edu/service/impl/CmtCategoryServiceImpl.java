package com.neu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.CmtCategoryMapper;
import com.neu.edu.pojo.CmtCategory;
import com.neu.edu.service.ICmtCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品类别表 服务实现类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@Service
public class CmtCategoryServiceImpl extends ServiceImpl<CmtCategoryMapper, CmtCategory> implements ICmtCategoryService {

    @Override
    public List<CmtCategory> getListByParentId(int parentId) {
        QueryWrapper<CmtCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        List<CmtCategory> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<CmtCategory> catogeryListFilter(List<CmtCategory> list) {
        List<CmtCategory> deleteList = new ArrayList<>();
        for (CmtCategory cmtCategory : list){
            if (cmtCategory.getActive()==0){
                deleteList.add(cmtCategory);
            }
        }
        for (CmtCategory c :deleteList){
            list.remove(c);
        }
        return list;
    }
}
