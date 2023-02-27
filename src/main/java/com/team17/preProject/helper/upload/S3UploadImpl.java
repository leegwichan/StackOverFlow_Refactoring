package com.team17.preProject.helper.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import com.team17.preProject.exception.businessLogic.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class S3UploadImpl implements S3Upload {

    private static final String FILE_EXTENSION_DELIMITER = ".";
    private static final String[] ALLOWED_IMAGE_EXTENSION = new String[]{".jpg", ".png", ".JPG", ".PNG"};
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
    private static final String CONTENT_TYPE = "multipart/formed-data";

    @Value("${cloud.aws.s3.bucket}") private String bucket;
    @Value("${cloud.aws.s3.dir}") private String dir;
    private final AmazonS3Client s3Client;
    
    @Override
    public String uploadImage(InputStream inputStream, String originFileName, String fileSize) {
        if(!isAllowedExtension(originFileName)){
            throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
        }

        String s3FileName = dir + "/" + LocalDateTime.now().format(DATE_FORMAT) + "-" + originFileName;

        s3Client.putObject(bucket, s3FileName, inputStream, makeObjectMetadata(fileSize));
        return s3Client.getUrl(bucket, s3FileName).toString();
    }

    private boolean isAllowedExtension(String fileName) {
        for (String extension : ALLOWED_IMAGE_EXTENSION) {
            if (isMatchedExtension(fileName, extension)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchedExtension(String filename, String extension) {
        return filename.substring(filename.lastIndexOf(FILE_EXTENSION_DELIMITER)).equals(extension);
    }

    private ObjectMetadata makeObjectMetadata(String fileSize) {
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(Long.parseLong(fileSize));
        objMeta.setContentType(CONTENT_TYPE);
        return objMeta;
    }
}
