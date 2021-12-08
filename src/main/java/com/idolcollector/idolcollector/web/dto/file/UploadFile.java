package com.idolcollector.idolcollector.web.dto.file;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
