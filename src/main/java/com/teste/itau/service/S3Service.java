package com.teste.itau.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public void uploadFile(String fileName, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        amazonS3.putObject(bucketName, fileName, inputStream, metadata);
    }
}
