package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.UmtFavoriteMapper;
import com.neu.edu.pojo.UmtFavorite;
import com.neu.edu.service.IUmtFavoriteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UmtFavoriteServiceImpl extends ServiceImpl<UmtFavoriteMapper, UmtFavorite> implements IUmtFavoriteService {
    @Override
    public IPage<UmtFavorite> page(Integer pageNo, Integer pageSize, Integer user_id) {
        QueryWrapper<UmtFavorite> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(String.valueOf(user_id))) {
            wrapper.eq("user_id",user_id);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public List<UmtFavorite> getListByUserId(int userId) {
        QueryWrapper<UmtFavorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("active",1);
        return list(wrapper);
    }


    @Override
    public List<UmtFavorite> favoriteListFilter(List<UmtFavorite> list) {
        List<UmtFavorite> deleteList = new ArrayList<>();
        for (UmtFavorite favorite : list){
            if (favorite.getActive()==0){
                deleteList.add(favorite);
            }
        }
        for (UmtFavorite favorite :deleteList){
            list.remove(favorite);
        }
        return list;

    }

    @Override
    public UmtFavorite favoriteFilter(UmtFavorite favorite) {
        if (favorite.getActive()==1){
            return favorite;
        }else {
            return null;
        }
    }

}
