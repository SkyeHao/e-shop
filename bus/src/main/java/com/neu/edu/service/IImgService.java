package com.neu.edu.service;

import com.neu.edu.pojo.Img;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.*;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hsk
 * @since 2021-07-19
 */
public interface IImgService extends IService<Img> {
//    上传图片
    String upload(MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, io.minio.errors.ServerException, io.minio.errors.InternalException;



}
