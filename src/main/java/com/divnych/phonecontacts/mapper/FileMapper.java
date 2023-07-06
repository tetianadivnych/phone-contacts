package com.divnych.phonecontacts.mapper;

import com.divnych.phonecontacts.exception.ContactPhotoNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


public class FileMapper {

    public static String mapFiletoString(MultipartFile file) {
        byte[] fileContent;
        try {
            fileContent = file.getBytes();
        } catch (IOException e) {
            throw new ContactPhotoNotFoundException("File path is not valid");
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static byte[] mapStringToBytes(String string) {
        return Base64.getDecoder().decode(string);
    }

}
