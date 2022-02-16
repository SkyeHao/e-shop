package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.UmtHistory;

import java.util.List;

public interface IUmtHistoryService extends IService<UmtHistory> {
    IPage<UmtHistory> page(Integer pageNo, Integer pageSize, Integer user_id);
    List<UmtHistory> getListByUserId(int userId);
    List<UmtHistory> historyListFilter(List<UmtHistory> list);
    UmtHistory historyFilter(UmtHistory history);
}
