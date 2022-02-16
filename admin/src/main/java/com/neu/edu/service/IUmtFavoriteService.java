package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.UmtFavorite;

import java.util.List;

public interface IUmtFavoriteService extends IService<UmtFavorite> {
    IPage<UmtFavorite> page(Integer pageNo, Integer pageSize, Integer user_id);
    List<UmtFavorite> getListByUserId(int userId);
    List<UmtFavorite> favoriteListFilter(List<UmtFavorite> list);
    UmtFavorite favoriteFilter(UmtFavorite favorite);
}

