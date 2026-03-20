package com.taskapi.tasker.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false) 

    private String title; 

 

    private String description; 

 

    @Enumerated(EnumType.STRING) 

    @Column(nullable = false) 

    private TaskStatus status = TaskStatus.TODO; 

 

    @Enumerated(EnumType.STRING) 

    @Column(nullable = false) 

    private TaskPriority priority = TaskPriority.MEDIUM; 

 

    private LocalDate dueDate; 

 

    @ManyToOne(fetch = FetchType.LAZY) 

    @JoinColumn(name = "user_id", nullable = false) 

    private User owner; 

 

    @CreationTimestamp 

    private LocalDateTime createdAt; 

 

    @UpdateTimestamp 

    private LocalDateTime updatedAt; 


}
