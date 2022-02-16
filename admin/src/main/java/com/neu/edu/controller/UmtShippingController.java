package com.neu.edu.controller;

import com.neu.edu.pojo.UmtShipping;
import com.neu.edu.service.IUmtShippingService;
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

@RestController
@RequestMapping("/umt-shipping")
public class UmtShippingController {
    @Resource
    IUmtShippingService iUmtShippingService;

    @GetMapping("/list")
    ResultJson list(int userId) throws InterruptedException {
        return ResultJson.success(iUmtShippingService.shippingListFilter(iUmtShippingService.getListByUserId(userId))) ;
    }
    @PostMapping("/add")
    ResultJson add(UmtShipping umtShipping) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return ResultJson.success(iUmtShippingService.save(umtShipping),"添加地址成功");
    }

    @GetMapping("/getone")
    ResultJson getOne(Long id) {
        System.out.println(id);
            return ResultJson.success(iUmtShippingService.shippingFilter(iUmtShippingService.getById(id)));
    }

    @PostMapping("/update")
    ResultJson update(UmtShipping umtShipping) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        return ResultJson.success(iUmtShippingService.updateById(umtShipping),"修改地址成功");
    }

    @PostMapping("/delete")
    ResultJson delete(UmtShipping umtShipping) {
        String message = umtShipping.getActive() == 0 ? "删除地址成功" : "恢复地址成功";
        return ResultJson.success(iUmtShippingService.updateById(umtShipping),message);
    }
    @PostMapping("/del")
    ResultJson del(UmtShipping umtShipping) {
        int id = umtShipping.getId();
        return ResultJson.success(iUmtShippingService.removeById(id),"删除地址成功");
    }
}
