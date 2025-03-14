package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.service.FileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations gridFsOperations;


    public String addFiles(String title, MultipartFile file) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("type", file.getContentType());
        metadata.put("title", title);

        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metadata);
        return id.toString();
    }


    public byte[] getFilesById(String fileId) throws IOException {

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
        return StreamUtils.copyToByteArray(gridFsOperations.getResource(file).getInputStream());
    }
}
