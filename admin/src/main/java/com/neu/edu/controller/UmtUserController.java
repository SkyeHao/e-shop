package com.neu.edu.controller;


import com.neu.edu.pojo.UmtUser;
import com.neu.edu.service.IUmtUserService;
import com.neu.edu.util.ResultJson;
import io.minio.errors.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/umt-user")
public class UmtUserController {
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    IUmtUserService umsUserService;
    @GetMapping("/list")
    ResultJson list(String name) throws InterruptedException {
        return ResultJson.success(umsUserService.getListByUserName(name)) ;
    }
    @PostMapping("/add")
    ResultJson add(UmtUser umtUser, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        System.out.println(umtUser.getPassword());
        System.out.println(umtUser.getRawPassword());
        umtUser.setActive(1);
        umtUser.setPassword(passwordEncoder.encode(umtUser.getRawPassword()));
        return ResultJson.success(umsUserService.save(umtUser),"添加用户成功");
    }
    @GetMapping("/getone")
    ResultJson getOne(Long id) {
        return ResultJson.success(umsUserService.getById(id));
    }
    @PostMapping("/update")
    ResultJson update(UmtUser umsUser, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        if(file != null && file.getSize() > 0) {
        }
        return ResultJson.success(umsUserService.updateById(umsUser),"修改用户成功");
    }
    @PostMapping("/login")
    ResultJson login(String username,String rawPassword){
        UmtUser umtUser = umsUserService.getOne(username);
        String password = umtUser.getPassword();
        password = umtUser.getPassword();
        System.out.println(passwordEncoder.matches(rawPassword,password));
        if(passwordEncoder.matches(rawPassword,password)){
            return ResultJson.success(umtUser.getId(),"登陆成功");
        }
        return ResultJson.error("用户名或密码错误");
    }

    @PostMapping("/del")
    ResultJson del(UmtUser umtUser) {
        String message = umtUser.getActive() == 0 ? "删除用户成功" : "恢复用户成功";
        return ResultJson.success(umsUserService.updateById(umtUser),message);
    }
    @GetMapping("/index")
    String index() {
        return "这里是admin中的index方法";
    }


    @GetMapping("/icon")
    ResultJson del(int id) {
        return ResultJson.success(umsUserService.getIcon(),"success");
    }
}
