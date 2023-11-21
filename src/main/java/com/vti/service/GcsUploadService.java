package com.vti.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class GcsUploadService {

    @Value("${gcp.bucket.name}")
    private String gcpBucketName;

    @Value("${spring.cloud.gcp.project-id}")
    private String gcpProjectId;

    @Value("${spring.cloud.gcp.credentials.location}")
    private Resource credentialsLocation;

    public String uploadFile(MultipartFile file, String folderName, String fileType) {
        try {
            byte[] fileData = file.getBytes();
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsLocation.getInputStream());

            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .setProjectId(gcpProjectId)
                    .build()
                    .getService();

            String fileName = folderName + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            BlobInfo blobInfo = BlobInfo.newBuilder(gcpBucketName, fileName)
                    .setContentType(fileType)
                    .build();

            storage.create(blobInfo, fileData);

            String fileUrl = "https://storage.googleapis.com/" + gcpBucketName + "/" + fileName;

            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
