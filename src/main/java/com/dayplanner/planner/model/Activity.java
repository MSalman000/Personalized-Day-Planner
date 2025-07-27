package com.dayplanner.planner.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String activityType; // e.g., "wakeup", "exercise", etc.
    private LocalTime startTime;
    private LocalTime endTime;

    public Activity() {}

    public Activity(Long userId, String activityType, LocalTime startTime, LocalTime endTime) {
        this.userId = userId;
        this.activityType = activityType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
