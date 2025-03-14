package com.fireflink.jiraservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

     String addFiles(String title, MultipartFile file) throws IOException;

     byte[] getFilesById(String fileId) throws IOException;

}
