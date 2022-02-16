package com.neu.edu.controller;


import com.neu.edu.pojo.PmtCart;
import com.neu.edu.pojo.PmtProduct;
import com.neu.edu.service.IPmtCartService;
import com.neu.edu.util.ResultJson;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author hsk
 * @since 2021-07-09
 */
@RestController
@RequestMapping("/cart")
public class  PmtCartController {


    @Resource
    IPmtCartService iPmtCartService;
//    @Resource
//    IImgService imgService;

    /**
     * 获取全部购物车信息
     * /cart/list
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/list")
    ResultJson list(int id) throws InterruptedException {
        return ResultJson.success(iPmtCartService.cartListFilter(iPmtCartService.getCartListByUserId(id))) ;
    }

    /**
     * 添加购物车
     * /cart/add
     * @param pmtCart
     */

    @PostMapping("/add")
    ResultJson add(PmtCart pmtCart){
        PmtCart p = iPmtCartService.isExists(pmtCart);
        LocalDateTime time = LocalDateTime.now();
        if (p==null){
//        将创建时间赋值
            pmtCart.setCreateTime(time);
            pmtCart.setUpdateTime(time);
            return ResultJson.success(iPmtCartService.save(pmtCart),"添加购物车成功");
        }else {
            p.setQuantity(p.getQuantity()+pmtCart.getQuantity());
            p.setUpdateTime(time);
            return ResultJson.success(iPmtCartService.updateById(p),"添加购物车成功");
        }
    }

    /**
     * 按id获取某个购物车
     * /cart/select
     * @param id
     * @return
     */

    @GetMapping("/select")
    ResultJson select(Long id) {
        return ResultJson.success(iPmtCartService.cartFilter(iPmtCartService.getById(id)));
    }

    /**
     * 修改购物车信息
     * /cart/update
     * @param pmtCart
     */

    @PostMapping("/update")
    ResultJson update(PmtCart pmtCart) {
        //        将创建时间赋值
        LocalDateTime time = LocalDateTime.now();
        pmtCart.setUpdateTime(time);
        pmtCart.setActive(1);
        return ResultJson.success(iPmtCartService.updateById(pmtCart),"修改购物车成功");
    }

    /**
     * 按id删除购物车信息
     * /cart/delete
     * @param id
     * @return
     */

    @PostMapping("/delete")
    ResultJson del(Long id) {
        PmtCart pmtCart = iPmtCartService.getById(id);
        String message = pmtCart.getActive() == 1 ? "删除购物车成功" : "恢复购物车成功";
        //        将创建时间赋值
        LocalDateTime time = LocalDateTime.now();
        pmtCart.setUpdateTime(time);
//        修改active值
        if (pmtCart.getActive()==0){
            pmtCart.setActive(1);
        }else {
            pmtCart.setActive(0);
        }
        return ResultJson.success(iPmtCartService.updateById(pmtCart),message);
    }

}
