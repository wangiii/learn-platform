package com.angiii.learnplatform.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;

@Configuration
@PropertySource("classpath:aliyun.oss.properties")
@Slf4j
public class AliyunOssUtil {

    @Value("${endpoint}")
    private String endpoint;

    @Value("${accessKeyId}")
    private String accessKeyId;

    @Value("${accessKeySecret}")
    private String accessKeySecret;

    @Value("${bucketName}")
    private String bucketName;

    private OSS ossClient;

    private OSS getInstance() {
        if(ossClient==null){
            synchronized(AliyunOssUtil.class){
                if(ossClient==null){
                    ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return ossClient;
    }

    /**
     * 上传
     */
    public void upload(InputStream file, String fileName){
        getInstance().putObject(bucketName, fileName, file);
    }

    /**
     * 删除
     */
    public void delete(String fileName) {
        getInstance().deleteObject(bucketName, fileName);
    }
}
