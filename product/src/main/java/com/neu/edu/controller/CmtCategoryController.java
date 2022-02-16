package com.neu.edu.controller;


import com.neu.edu.pojo.CmtCategory;
import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.service.ICmtCategoryService;
import com.neu.edu.service.IPmtProductService;
import com.neu.edu.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@RestController
@RequestMapping("/category")
public class CmtCategoryController {

    @Resource
    ICmtCategoryService iCmtCategoryService;

    @Resource
    IPmtProductService iPmtProductService;

    /**
     * 获取所有节点列表
     * @return
     */
    @GetMapping("/list")
    ResultJson list() {
        return ResultJson.success(iCmtCategoryService.catogeryListFilter(iCmtCategoryService.list()));
    }

//    /**
//     * 添加类别
//     * @param cmtCategory
//     * @return
//     */
//    @PostMapping("/add")
//    ResultJson add(CmtCategory cmtCategory) {
//        //        将创建时间赋值
//        LocalDateTime time = LocalDateTime.now();
//        cmtCategory.setCreateTime(time);
//        cmtCategory.setUpdateTime(time);
//        return ResultJson.success(iCmtCategoryService.save(cmtCategory),"添加分类成功");
//    }

    /**
     * 根据所给节点id返回下一级节点列表
     * @param id
     * @return
     */
    @GetMapping("/select")
    ResultJson select(int id) {
        CmtCategory cmtCategory = iCmtCategoryService.getById(id);
        if (cmtCategory.getLevel()==2){
            return ResultJson.success(iPmtProductService.productListFilter(iPmtProductService.getListByCategory(id)));
        }else {
            return ResultJson.success(iCmtCategoryService.catogeryListFilter(iCmtCategoryService.getListByParentId(id)));
        }

    }

//    /**
//     * 更新节点
//     * @param cmtCategory
//     * @return
//     */
//    @PostMapping("/update")
//    ResultJson update(CmtCategory cmtCategory) {
//        //        将创建时间赋值
//        LocalDateTime time = LocalDateTime.now();
//        cmtCategory.setUpdateTime(time);
//
//        return ResultJson.success(iCmtCategoryService.updateById(cmtCategory),"修改分类成功");
//    }
//
//    /**
//     * 删除某节点
//     * @param id
//     * @return
//     */
//    @PostMapping("/delete")
//    ResultJson del(int id) {
//        CmtCategory cmtCategory = iCmtCategoryService.getById(id);
//        String message = cmtCategory.getActive() == 1 ? "删除分类成功" : "恢复分类成功";
//        LocalDateTime time = LocalDateTime.now();
//        cmtCategory.setUpdateTime(time);
////        修改active值
//        if (cmtCategory.getActive()==0){
////            若该节点已经被删除，恢复该节点及其所有子节点
//            cmtCategory.setActive(1);
//            iCmtCategoryService.updateById(cmtCategory);
//            List<CmtCategory> list = iCmtCategoryService.getListByParentId(id);
//            boolean flag = true;
//            for (CmtCategory category : list) {
//                category.setActive(1);
//                flag = flag & iCmtCategoryService.updateById(category);
//            }
//            return ResultJson.success(flag,message);
//        }else {
////            若该节点未被删除，删除该节点及其子节点
//            cmtCategory.setActive(0);
//            iCmtCategoryService.updateById(cmtCategory);
//            List<CmtCategory> list = iCmtCategoryService.getListByParentId(id);
//            boolean flag = true;
//            for (CmtCategory category : list) {
//                category.setActive(0);
//                flag = flag & iCmtCategoryService.updateById(category);
//            }
//            return ResultJson.success(flag,message);
//        }
//    }
}
