package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.model.entity.SequenceGenerator;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    public String getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        SequenceGenerator counter = mongoOperations.findAndModify(query, update, options, SequenceGenerator.class);

        long seqNum = (counter != null) ? counter.getSequence() : 1;

        return String.format("TY%03d", seqNum); // Formats as TY001, TY002...
    }

    @Override
    public String getNextCommentSequence(String sequenceName) {

        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        SequenceGenerator counter = mongoOperations.findAndModify(query, update, options, SequenceGenerator.class);

        long seqNum = (counter != null) ? counter.getSequence() : 1;

        return String.format("COM%03d", seqNum); // Formats as COM001, COM002...
    }

    @Override
    public String getNextTicketSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        SequenceGenerator counter = mongoOperations.findAndModify(query, update, options, SequenceGenerator.class);

        long seqNum = (counter != null) ? counter.getSequence() : 1;

        return String.format("TICK%03d", seqNum); // Formats as TICK001, TICK002...
    }
}








