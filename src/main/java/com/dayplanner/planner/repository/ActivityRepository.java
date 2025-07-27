package com.dayplanner.planner.repository;

import com.dayplanner.planner.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
