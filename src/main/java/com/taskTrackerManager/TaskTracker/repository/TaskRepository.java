package com.taskTrackerManager.TaskTracker.repository;
import lombok.*;
import jakarta.persistence.*;
import com.taskTrackerManager.TaskTracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
