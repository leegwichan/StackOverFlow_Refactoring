package com.team17.preProject.helper.upload;

import java.io.InputStream;

public interface S3Upload {

    String uploadImage(InputStream inputStream, String originFileName, String fileSize);
}
