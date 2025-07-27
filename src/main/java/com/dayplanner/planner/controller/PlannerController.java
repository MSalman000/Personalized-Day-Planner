package com.dayplanner.planner.controller;

import com.dayplanner.planner.model.Activity;
import com.dayplanner.planner.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/planner")
public class PlannerController {
    @Autowired
    private PlannerService plannerService;

    // Single activity insert
    @PostMapping("/activity")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(plannerService.addActivity(activity));
    }

    // Bulk activity insert
    @PostMapping("/activities")
    public ResponseEntity<List<Activity>> addActivities(@RequestBody List<Activity> activities) {
        List<Activity> savedActivities = plannerService.addActivities(activities);
        return ResponseEntity.ok(savedActivities);
    }

    // Delete activity by ID
    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long activityId) {
        plannerService.deleteActivity(activityId);
        return ResponseEntity.noContent().build();
    }

    // Delete all activities for a user
    @DeleteMapping("/activities/{userId}")
    public ResponseEntity<Void> deleteActivitiesByUser(@PathVariable Long userId) {
        plannerService.deleteActivitiesByUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/timetable/{userId}")
    public ResponseEntity<List<Activity>> getTimetable(@PathVariable Long userId) {
        return ResponseEntity.ok(plannerService.getTimetable(userId));
    }

    @GetMapping("/export/{userId}")
    public ResponseEntity<byte[]> exportTimetable(@PathVariable Long userId) throws IOException {
        String filePath = plannerService.exportTimetableToExcel(userId);
        File file = new File(filePath);
        byte[] contents = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
        headers.setContentLength(contents.length);

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}
