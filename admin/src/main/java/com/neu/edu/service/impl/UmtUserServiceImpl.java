package com.neu.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.UmtUserMapper;
import com.neu.edu.pojo.Img;
import com.neu.edu.pojo.UmtUser;
import com.neu.edu.service.IImgService;
import com.neu.edu.service.IUmtUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UmtUserServiceImpl extends ServiceImpl<UmtUserMapper, UmtUser> implements IUmtUserService {

    @Resource
    IImgService iImgService;

    @Override
    public IPage<UmtUser> page(Integer pageNo, Integer pageSize, String name) {
        QueryWrapper<UmtUser> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {
            wrapper.like("nicky_name",name);
        }
        return this.page(new Page<>(pageNo,pageSize),wrapper);
    }

    @Override
    public UmtUser getOne(String username){
        QueryWrapper<UmtUser> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            wrapper.eq("username",username);
        }
        return this.getOne(wrapper);
    }

    @Override
    public List<UmtUser> getListByUserName(String name) {
        QueryWrapper<UmtUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",name).eq("active",1);
        return list(wrapper);
    }

    @Override
    public String getIcon() {
        QueryWrapper<Img> wrapper = new QueryWrapper<>();
        wrapper.eq("id",19402);
        return iImgService.getOne(wrapper).getUrl();
    }


}
