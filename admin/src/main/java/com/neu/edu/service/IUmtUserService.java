package com.neu.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.edu.pojo.UmtUser;

import java.util.List;

public interface IUmtUserService extends IService<UmtUser> {
    IPage<UmtUser> page(Integer pageNo, Integer pageSize, String name);
    UmtUser getOne(String username);
    List<UmtUser> getListByUserName(String name);
    String getIcon();
}
