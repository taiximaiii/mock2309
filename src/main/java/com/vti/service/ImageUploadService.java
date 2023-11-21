package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    @Autowired
    private GcsUploadService gcsUploadService;

    public String uploadImage(MultipartFile file, String folderName) {
        return gcsUploadService.uploadFile(file, folderName,"image/jpeg");
    }
}