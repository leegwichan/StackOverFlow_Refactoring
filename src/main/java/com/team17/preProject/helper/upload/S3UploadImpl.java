package com.team17.preProject.helper.upload;

import com.amazonaws.services.s3.AmazonS3;
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
public class S3UploadImpl implements S3Upload{

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    // private String[] imageFileExtension = new String[]{".jpg", ".png"};

    private final AmazonS3Client s3Client;


    @Override
    public String upload(InputStream inputStream, String originFileName, String fileSize) {

        if(!originFileName.substring(originFileName.length()-4).equals(".jpg") &&
                !originFileName.substring(originFileName.length()-4).equals(".png")){
            throw new BusinessLogicException(ExceptionCode.NOT_IMAGE_EXTENSION);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
        String s3FileName = dir + "/" +LocalDateTime.now().format(format)+ "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(Long.parseLong(fileSize));

        s3Client.putObject(bucket, s3FileName, inputStream, objMeta);

        return s3Client.getUrl(bucket, s3FileName).toString();
    }
}