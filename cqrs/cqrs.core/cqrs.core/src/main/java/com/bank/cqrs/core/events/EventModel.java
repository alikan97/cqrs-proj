package com.bank.cqrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
    @Id
    private String id; // Will be converted to Guid in MongoDb
    private Date timestamp;
    private String aggregateId;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseEvent eventData;
}
