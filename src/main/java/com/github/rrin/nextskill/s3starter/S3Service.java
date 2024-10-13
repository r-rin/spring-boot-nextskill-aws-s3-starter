package com.github.rrin.nextskill.s3starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.nio.file.Paths;

@Service
public class S3Service {

    private final S3Properties s3Properties;
    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Properties s3Properties) {
        this.s3Properties = s3Properties;

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                s3Properties.getAccessKeyId(), s3Properties.getSecretAccessKey()
        );

        this.s3Client = S3Client.builder()
                .region(Region.of(s3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    public void uploadFile(String filePath, String folderPath) {
        File file = new File(filePath);
        String key = folderPath + "/" + file.getName();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(filePath));
            System.out.println("File uploaded to S3 bucket: " + s3Properties.getBucketName());
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public void downloadFile(String key, String downloadPath) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(key)
                    .build();

            File downloadFile = new File(downloadPath);

            s3Client.getObject(getObjectRequest, downloadFile.toPath());
            System.out.println("File downloaded from S3 bucket: " + s3Properties.getBucketName());
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("File deleted from S3 bucket: " + s3Properties.getBucketName());
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
