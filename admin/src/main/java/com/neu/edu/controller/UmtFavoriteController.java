package com.neu.edu.controller;

import com.neu.edu.pojo.UmtFavorite;
import com.neu.edu.service.IUmtFavoriteService;
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
@RequestMapping("/umt-favorite")
public class UmtFavoriteController {
    @Resource
    IUmtFavoriteService umtFavoriteService;
    @GetMapping("/list")
    ResultJson list(int userId) throws InterruptedException {
        return ResultJson.success(umtFavoriteService.getListByUserId(userId));
    }

    @PostMapping("/add")
    ResultJson add(UmtFavorite umtFavorite) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        umtFavorite.setActive(1);
        return ResultJson.success(umtFavoriteService.save(umtFavorite),"添加收藏成功");
    }
    @PostMapping("/del")
    ResultJson del(UmtFavorite umtFavorite) {
        umtFavorite.setActive(0);
        return ResultJson.success(umtFavoriteService.updateById(umtFavorite),"删除收藏成功");
    }
}
