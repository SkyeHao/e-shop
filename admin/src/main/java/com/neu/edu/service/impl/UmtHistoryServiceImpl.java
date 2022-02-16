package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.UmtHistoryMapper;
import com.neu.edu.pojo.UmtFavorite;
import com.neu.edu.pojo.UmtHistory;
import com.neu.edu.service.IUmtHistoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UmtHistoryServiceImpl extends ServiceImpl<UmtHistoryMapper, UmtHistory> implements IUmtHistoryService {
    @Override
    public IPage<UmtHistory> page(Integer pageNo, Integer pageSize, Integer user_id) {
        QueryWrapper<UmtHistory> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(String.valueOf(user_id))) {
            wrapper.eq("user_id",user_id);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }


    @Override
    public List<UmtHistory> getListByUserId(int userId) {
        QueryWrapper<UmtHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("active",1);
        return list(wrapper);
    }

    @Override
    public List<UmtHistory> historyListFilter(List<UmtHistory> list) {
        List<UmtHistory> deleteList = new ArrayList<>();
        for (UmtHistory history : list){
            if (history.getActive()==0){
                deleteList.add(history);
            }
        }
        for (UmtHistory history :deleteList){
            list.remove(history);
        }
        return list;

    }

    @Override
    public UmtHistory historyFilter(UmtHistory history) {
        if (history.getActive()==1){
            return history;
        }else {
            return null;
        }
    }

}
