package com.taskapi.tasker.audit;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuditRepository extends MongoRepository<AuditLog, String> { 

   List<AuditLog> findByEntityTypeAndEntityIdOrderByTimestampDesc( 

           String entityType, String entityId); 

   List<AuditLog> findByActorIdOrderByTimestampDesc(String actorId); 

} 


