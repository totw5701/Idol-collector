package com.idolcollector.idolcollector.file;

import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class FileDtoStoreTest {

    @Autowired
    private FileStore fileStore;


    @Test
    void fileStore() throws IOException {

        // Given
        File img = new File("/Users/totheworld/Desktop/idol-collector/test.png");
        MultipartFile mf = new MockMultipartFile("image","test.png", "img",new FileInputStream(img));

        // When
        UploadFile uploadFile = fileStore.storeFile(mf);

        // Then
        assertThat(uploadFile.getUploadFileName()).isEqualTo("test.png");
        assertThat(uploadFile.getStoreFileName()).isNotNull();

        File file = new File("/Users/totheworld/Desktop/idol-collector/img/" + uploadFile.getStoreFileName());
        assertThat(file.length()).isEqualTo(img.length());

    }

}