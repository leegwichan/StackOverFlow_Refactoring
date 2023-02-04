package com.team17.preProject.helper.upload;

import java.io.InputStream;

public interface S3Upload {

    String upload(InputStream inputStream, String originFileName, String fileSize);
}
