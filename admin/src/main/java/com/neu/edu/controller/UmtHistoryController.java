package com.neu.edu.controller;

import com.neu.edu.pojo.UmtHistory;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/umt-history")
public class UmtHistoryController {
    @Resource
    IUmtHistoryService umtHistoryService;
    @GetMapping("/list")
    ResultJson list(int userId) throws InterruptedException {
        System.out.println(userId);
        return ResultJson.success(umtHistoryService.historyListFilter(umtHistoryService.getListByUserId(userId))) ;
    }

    @PostMapping("/del")
    ResultJson del(UmtHistory umtHistory) {
        String message = umtHistory.getActive() == 0 ? "删除足迹成功" : "恢复足迹成功";
        return ResultJson.success(umtHistoryService.updateById(umtHistory),message);
    }
}
