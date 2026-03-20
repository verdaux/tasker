package com.taskapi.tasker.audit;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "audit_logs") 

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder 

public class AuditLog { 

    @Id private String id; 

    private String action;      // CREATE / UPDATE / DELETE 

    private String entityType; 

    private String entityId; 

    private String actorId; 

    private Instant timestamp; 

} 