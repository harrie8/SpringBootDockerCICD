package com.sppart.admin.objectstorage.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageServiceImpl implements ObjectStorageService {

    private final AmazonS3 amazonS3Client;

    @Value("${naver.objectStorage.bucketName}")
    private String bucket;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String uploadFile(MultipartFile file) {
        String fileName = createUUIDFileName(file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드 에러 : " + e.getMessage());
        }

        log.info("저장한 파일의 url = {}", amazonS3Client.getUrl(bucket, fileName).toString());

        return fileName;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> uploadFiles(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            fileNames.add(uploadFile(file));
        }

        return fileNames;
    }

    // filePathAndName: 01278b9a-0d06-427f-9693-ae5a5d18a8d8.jpg 또는 하위 폴더가 존재하는 경우 image/01278b9a-0d06-427f-9693-ae5a5d18a8d8.jpg 처럼 전달해주시면 됩니다.
    @Override
    public void delete(String filePathAndName) {
        try {
            boolean doesObjectExist = amazonS3Client.doesObjectExist(bucket, filePathAndName);
            if (doesObjectExist) {
                amazonS3Client.deleteObject(bucket, filePathAndName);
            }
            if (!doesObjectExist) {
                log.info("버킷에서 해당 파일을 찾지 못했습니다. : {}", filePathAndName);
            }
        } catch (SdkClientException e) {
            log.error("파일 삭제 오류, ", e);
        }
    }

    @Override
    public void delete(List<String> urls) {
        urls.forEach(this::delete);
    }

    private String createUUIDFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        List<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName.toLowerCase(Locale.ENGLISH))) {
            throw new IllegalArgumentException("이미지 파일만 올려주세요");
        }
        return idxFileName;
    }
}
