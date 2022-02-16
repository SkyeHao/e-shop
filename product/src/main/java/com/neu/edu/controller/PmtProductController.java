package com.neu.edu.controller;


import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.pojo.UmtHistory;
import com.neu.edu.service.IImgService;
import com.neu.edu.service.IPmtProductService;
import com.neu.edu.service.IUmtHistoryService;
import com.neu.edu.util.ResultJson;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@RestController
@RequestMapping("/product")
public class PmtProductController {

    @Resource
    IUmtHistoryService iUmtHistoryService;

    @Resource
    IPmtProductService iPmtProductService;
    @Resource
    IImgService imgService;

    /**
     * 按name获取全部商品信息
     * /product/list
     * @param name
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/list")
    ResultJson list(String name) throws InterruptedException {
        return ResultJson.success(iPmtProductService.productListFilter(iPmtProductService.getListByName(name)));
    }

//    /**
//     * 按姓名获取某个商品
//     * /product/selectByName
//     * @param name
//     * @return
//     * @throws InterruptedException
//     */
//
//    @GetMapping("/selectByName")
//    ResultJson selectByname(String name) throws InterruptedException {
//        return ResultJson.success(iPmtProductService.getProducts(name));
//    }


    /**
     * 按id获取某个商品
     * /product/selectById
     * @param id
     * @return
     */

    @GetMapping("/selectById")
    ResultJson selectDetailById(int id,int userId) {
        UmtHistory umtHistory = new UmtHistory();
        umtHistory.setUserId(userId);
        umtHistory.setProductId(id);
        LocalDateTime time = LocalDateTime.now();
        umtHistory.setCreateTime(time);
        umtHistory.setActive(1);
        iUmtHistoryService.save(umtHistory);
        return ResultJson.success(iPmtProductService.productFilter(iPmtProductService.getDetailProductsById(id)));
    }

    /**
     * 按id获取某个商品精简信息
     * /product/selectMainById
     * @param id
     * @return
     */

    @GetMapping("/selectMainById")
    ResultJson selectMainById(int id) {
        return ResultJson.success(iPmtProductService.productFilter(iPmtProductService.getMainProductsById(id)));
    }

    /**
     * 按id获取某个商品精简信息
     * /product/selectMainById
     * @return
     */

    @GetMapping("/selectDefaultProductList")
    ResultJson getDefaultProductList(int id) {
        System.out.println("selectDefaultProductList执行了");
        return ResultJson.success(iPmtProductService.productListFilter(iPmtProductService.getDefaultProductList()));
    }

//    /**
//     * 添加商品
//     * /product/add
//     * @param pmtProduct
//     * @param file
//     * @return
//     * @throws IOException
//     * @throws InvalidKeyException
//     * @throws InvalidResponseException
//     * @throws InsufficientDataException
//     * @throws NoSuchAlgorithmException
//     * @throws ServerException
//     * @throws InternalException
//     * @throws XmlParserException
//     * @throws InvalidBucketNameException
//     * @throws ErrorResponseException
//     */
//
//    @PostMapping("/add")
//    ResultJson add(PmtProduct pmtProduct, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
////        pmtProduct.setIcon(imgService.upload(file));
//
////        将创建时间赋值
//        LocalDateTime time = LocalDateTime.now();
//        pmtProduct.setCreateTime(time);
//        pmtProduct.setUpdateTime(time);
//
//        return ResultJson.success(iPmtProductService.save(pmtProduct),"添加商品成功");
//    }


//    /**
//     * 修改商品信息
//     * /product/update
//     * @param pmtProduct
//     * @param file
//     * @return
//     * @throws IOException
//     * @throws InvalidKeyException
//     * @throws InvalidResponseException
//     * @throws InsufficientDataException
//     * @throws NoSuchAlgorithmException
//     * @throws ServerException
//     * @throws InternalException
//     * @throws XmlParserException
//     * @throws InvalidBucketNameException
//     * @throws ErrorResponseException
//     */
//
//    @PostMapping("/update")
//    ResultJson update(PmtProduct pmtProduct, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
////        if(file != null && file.getSize() > 0) {
////            umsUser.setIcon(imgService.upload(file));
////        }
//
//        //        将创建时间赋值
//        LocalDateTime time = LocalDateTime.now();
//        pmtProduct.setUpdateTime(time);
//        return ResultJson.success(iPmtProductService.updateById(pmtProduct),"修改商品成功");
//    }

//    /**
//     * 删除商品信息
//     * /product/delete
//     * @param id
//     * @return
//     */
//
//    @PostMapping("/delete")
//    ResultJson del(Long id) {
//        PmtProduct pmtProduct = iPmtProductService.getById(id);
//        String message = pmtProduct.getActive() == 1 ? "删除商品成功" : "恢复商品成功";
//        //        将创建时间赋值
//        LocalDateTime time = LocalDateTime.now();
//        pmtProduct.setUpdateTime(time);
////        修改active值
//        if (pmtProduct.getActive()==0){
//            pmtProduct.setActive(1);
//        }else {
//            pmtProduct.setActive(0);
//        }
//        return ResultJson.success(iPmtProductService.updateById(pmtProduct),message);
//    }

}
