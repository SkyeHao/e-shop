package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.PmtCartMapper;
import com.neu.edu.pojo.PmtCart;
import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.service.IPmtCartService;
import com.neu.edu.service.IPmtProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@Service
public class PmtCartServiceImpl extends ServiceImpl<PmtCartMapper, PmtCart> implements IPmtCartService {

    @Resource
    IPmtCartService iPmtCartService;

    @Override
    public List<PmtCart> getCartListByUserId(int id) {
        QueryWrapper<PmtCart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<PmtCart> list = this.list(wrapper);
        return list;
    }

    @Override
    public IPage<PmtCart> page(Integer pageNo, Integer pageSize, String name) {
        QueryWrapper<PmtCart> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {
            wrapper.like("name",name);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public List<PmtCart> cartListFilter(List<PmtCart> list) {
        List<PmtCart> deleteList = new ArrayList<>();
        for (PmtCart pmtCart : list){
            if (pmtCart.getActive()==0){
                deleteList.add(pmtCart);
            }
        }
        for (PmtCart cart :deleteList){
            list.remove(cart);
        }
        return list;

    }

    @Override
    public PmtCart cartFilter(PmtCart pmtcart) {
        if (pmtcart.getActive() == 1) {
            return pmtcart;
        }else {
            return null;
        }
    }

    @Override
    public PmtCart isExists(PmtCart pmtcart) {
        QueryWrapper<PmtCart> wrapper = new QueryWrapper<>();
        wrapper.eq("active",1).eq("user_id",pmtcart.getUserId()).eq("product_id",pmtcart.getProductId());
        PmtCart cart = this.getOne(wrapper);
        return cart;
    }
}
