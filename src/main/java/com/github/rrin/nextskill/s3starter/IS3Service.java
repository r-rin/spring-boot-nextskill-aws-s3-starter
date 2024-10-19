package com.github.rrin.nextskill.s3starter;

public interface IS3Service {
    void uploadFile(String filePath, String folderPath);
    void downloadFile(String key, String downloadPath);
    void deleteFile(String key);
}
