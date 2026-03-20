package com.taskapi.tasker.audit;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 

@Service 

@RequiredArgsConstructor 

public class AuditService { 

 

    private final AuditRepository auditRepo; 

 

    /** 

     * Persist an audit entry to MongoDB. 

     * 

     * @param action     e.g. "CREATE", "UPDATE", "DELETE" 

     * @param entityType e.g. "task" 

     * @param entityId   UUID of the affected entity 

     * @param actorId    UUID of the user who performed the action 

     */ 

    public void log(String action, String entityType, String entityId, String actorId) { 

        try { 

            var entry = AuditLog.builder() 

                    .action(action) 

                    .entityType(entityType) 

                    .entityId(entityId) 

                    .actorId(actorId) 

                    .timestamp(Instant.now()) 

                    .build(); 

            auditRepo.save(entry); 

        } catch (Exception ex) { 

            // Audit failure must never break the main transaction 

            log.error("Failed to persist audit log: action={} entityType={} entityId={}", 

                    action, entityType, entityId, ex); 

        } 

    } 

 

    public java.util.List<AuditLog> getLogsForEntity(String entityType, String entityId) { 

        return auditRepo.findByEntityTypeAndEntityIdOrderByTimestampDesc(entityType, entityId); 

    } 

 

    public java.util.List<AuditLog> getLogsForActor(String actorId) { 

        return auditRepo.findByActorIdOrderByTimestampDesc(actorId); 

    } 

} 