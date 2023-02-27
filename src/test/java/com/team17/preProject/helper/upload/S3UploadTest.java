package com.team17.preProject.helper.upload;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3Client;
import com.team17.preProject.exception.businessLogic.BusinessLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.io.InputStream;
import java.net.URL;

@SpringBootTest(classes = {S3UploadImpl.class})
public class S3UploadTest {

    @Autowired private S3Upload s3Upload;
    @MockBean private AmazonS3Client s3Client;
    private URL MOCK_URL = mock(URL.class);

    @ParameterizedTest(name = "file extension : {0}")
    @CsvSource(value = {".jpg", ".png", ".JPG", ".PNG"})
    void imageUploadTest_fileExtensionAllowed(String fileExtension) {
        InputStream inputStream = InputStream.nullInputStream();
        String originalFileName = "name" + fileExtension;
        String fileSize = "10000";
        given(s3Client.getUrl(anyString(), anyString())).willReturn(MOCK_URL);

        assertDoesNotThrow(() -> s3Upload.uploadImage(inputStream, originalFileName, fileSize));
    }

    @ParameterizedTest(name = "file extension : {0}")
    @CsvSource(value = {".io", ".jepg", ".gif", ".exe"})
    void imageUploadTest_fileExtensionNotAllowed(String fileExtension) {
        InputStream inputStream = InputStream.nullInputStream();
        String originalFileName = "name" + fileExtension;
        String fileSize = "10000";

        Exception exception = assertThrows(BusinessLogicException.class,
                () -> s3Upload.uploadImage(inputStream, originalFileName, fileSize));

        assertThat(exception.getMessage()).isEqualTo("이미지 파일이 아닙니다.");
    }

    @BeforeEach
    void mockingURL() {
        when(MOCK_URL.toString()).thenReturn("string");
    }
}
