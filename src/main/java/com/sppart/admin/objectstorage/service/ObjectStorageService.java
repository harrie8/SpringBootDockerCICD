package com.sppart.admin.objectstorage.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {

    String uploadFile(MultipartFile file);

    List<String> uploadFiles(List<MultipartFile> files);

    void delete(String url);

    void delete(List<String> urls);
}
