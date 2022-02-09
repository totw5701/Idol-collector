package com.idolcollector.idolcollector.file;

import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    @Value("${profile.dir}")
    private String profileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String getProfileFullPath(String fileName) {
        return profileDir + fileName;
    }

    private String createStoreFileName(String fileName) {

        int idx = fileName.lastIndexOf(".");
        String ext = fileName.substring(idx + 1);

        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();

        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }


    public UploadFile storeProFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();

        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getProfileFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }

    public void deleteFile(String storeFileName) {

        File file = new File(getFullPath(storeFileName));
        if(file.exists()) file.delete();
    }

    public void deleteProFile(String uri) {

        String storeFileName = getProfileStoreFileName(uri);

        System.out.println("storeFileName = " + storeFileName);

        File file = new File(getProfileFullPath(storeFileName));
        if(file.exists()) file.delete();
    }

    public String getProfileStoreFileName(String uri) {

        int idx = uri.lastIndexOf('/');
        return uri.substring(idx + 1, uri.length());
    }
}
