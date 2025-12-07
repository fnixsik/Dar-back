package com.example.demo.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName = "dar-bucket";

    @PostConstruct
    public void createBucketIfNotExists() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании bucket: " + e.getMessage(), e);
        }
    }

    public String uploadFile(MultipartFile file) {
        return uploadFile(file, "");
    }

    public String uploadFile(MultipartFile file, String folder) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String objectName = folder.isEmpty() ? fileName : folder + "/" + fileName;

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке файла в MinIO: " + e.getMessage(), e);
        }

        return "https://s3.dar-team.kz/" + bucketName + "/" + objectName;
    }
}


