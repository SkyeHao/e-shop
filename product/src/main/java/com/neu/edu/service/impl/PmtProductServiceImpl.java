package com.neu.edu.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.edu.mapper.PmtProductMapper;
import com.neu.edu.pojo.Img;
import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.service.IImgService;
import com.neu.edu.service.IPmtProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@Service
public class PmtProductServiceImpl extends ServiceImpl<PmtProductMapper, PmtProduct> implements IPmtProductService {

    @Resource
    IPmtProductService iPmtProductService;
    @Resource
    IImgService iImgService;

    @Override
    public List<PmtProduct> getListByName(String name) {
        QueryWrapper<PmtProduct> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {
            wrapper.like("name",name);
        }
        List<PmtProduct> list = this.list(wrapper);
        for (PmtProduct pi : list){
            pi.setMainImage(iImgService.getById(Integer.parseInt(pi.getMainImage())).getUrl());
        }
        return list;
    }

    @Override
    public PmtProduct getMainProductsById(int id) {
        PmtProduct pmtProduct = this.getById(id);
        pmtProduct.setMainImage(iImgService.getById(Integer.parseInt(pmtProduct.getMainImage())).getUrl());
        return pmtProduct;
    }

    @Override
    public PmtProduct getDetailProductsById(int id) {
        PmtProduct pmtProduct = this.getById(id);
        pmtProduct.setMainImage(iImgService.getById(Integer.parseInt(pmtProduct.getMainImage())).getUrl());

//        设置轮播图url，用 , 分割
        String[] subImagesList = pmtProduct.getSubImages().split(",");
        String subImg = "";
        for (String img : subImagesList) {
            if ("".equals(subImg)){
                subImg += iImgService.getById(Integer.parseInt(img)).getUrl();
            }else {
                subImg += ",";
                subImg += iImgService.getById(Integer.parseInt(img)).getUrl();
            }
        }
        pmtProduct.setSubImages(subImg);

        //        设置详情图url，用 , 分割
        String[] detailList = pmtProduct.getDetail().split(",");
        String detailImg = "";
        for (String img : detailList) {
            if ("".equals(detailImg)){
                detailImg += iImgService.getById(Integer.parseInt(img)).getUrl();
            }else {
                detailImg += ",";
                detailImg += iImgService.getById(Integer.parseInt(img)).getUrl();
            }
        }
        pmtProduct.setDetail(detailImg);
        return pmtProduct;
    }


//    按2级节点获取商品数据
    @Override
    public List<PmtProduct> getListByCategory(int categoryId) {
        QueryWrapper<PmtProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        List<PmtProduct> list = iPmtProductService.list(wrapper);
        for (PmtProduct pi : list){
            pi.setMainImage(iImgService.getById(Integer.parseInt(pi.getMainImage())).getUrl());
        }
        return list;
    }

    @Override
    public List<PmtProduct> productListFilter(List<PmtProduct> list) {
        List<PmtProduct> deleteList = new ArrayList<>();
        for (PmtProduct pmtProduct : list){
            if (pmtProduct.getActive()==0){
                deleteList.add(pmtProduct);
            }
        }
        for (PmtProduct product :deleteList){
            list.remove(product);
        }
        return list;

    }

    @Override
    public PmtProduct productFilter(PmtProduct product) {
        if (product.getActive()==1){
            return product;
        }else {
            return null;
        }
    }

    @Override
    public List<PmtProduct> getDefaultProductList() {
        List<PmtProduct> list = new ArrayList<>();
        int[] idList = new int[]{2076,2199,2228,2280,2254,2375};
        for (Integer id: idList){
            list.add(getMainProductsById(id));
        }
        return list;
    }

}
