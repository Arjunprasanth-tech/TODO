package com.example.proj10.task;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TasksRepository
        extends JpaRepository<TasksDetails, Long> {

    List<TasksDetails> findByDueDate(LocalDate dueDate);
    long countByStatusNameIgnoreCase(String statusName);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.transaction.annotation.Transactional
    @org.springframework.data.jpa.repository.Query("""
           UPDATE TasksDetails t 
           SET t.status = :overdueStatus 
           WHERE (LOWER(t.status.name) = 'pending') 
             AND t.dueDate < :today
           """)
    void autoUpdateOverdueTasks(
            @org.springframework.data.repository.query.Param("overdueStatus") com.example.proj10.status.StatusDetails overdueStatus, 
            @org.springframework.data.repository.query.Param("today") LocalDate today);
}
