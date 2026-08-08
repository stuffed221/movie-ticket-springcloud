package org.example;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.example.Config.AliOssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;

@Component
public class AliOssUtil {

    @Autowired
    private AliOssConfig aliOssConfig;

    private static AliOssConfig config;

    @PostConstruct
    public void init() {
        config = aliOssConfig;
    }

    public static String upload(byte[] bytes, String fileName) {
        String endpoint = "https://" + config.getEndpoint();
        OSS ossClient = new OSSClientBuilder().build(
            endpoint,
            config.getAccessKeyId(),
            config.getAccessKeySecret()
        );
        try {
            ossClient.putObject(config.getBucketName(), fileName, new ByteArrayInputStream(bytes));
            return "https://" + config.getBucketName() + "." + config.getEndpoint() + "/" + fileName;
        } finally {
            ossClient.shutdown();
        }
    }
}
