package com.dayplanner.planner.service;

import com.dayplanner.planner.model.Activity;
import com.dayplanner.planner.repository.ActivityRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PlannerService {
    @Autowired
    private ActivityRepository activityRepository;

    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // Bulk insert method
    public List<Activity> addActivities(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    public List<Activity> getTimetable(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public String exportTimetableToExcel(Long userId) throws IOException {
        List<Activity> activities = getTimetable(userId);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Timetable");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Activity");
        header.createCell(1).setCellValue("Start Time");
        header.createCell(2).setCellValue("End Time");

        int rowNum = 1;
        for (Activity activity : activities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(activity.getActivityType());
            row.createCell(1).setCellValue(activity.getStartTime().toString());
            row.createCell(2).setCellValue(activity.getEndTime().toString());
        }

        String filePath = "timetable_user_" + userId + ".xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
        return filePath;
    }

    // Delete single activity by ID
    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    // Delete all activities for a specific user
    public void deleteActivitiesByUser(Long userId) {
        activityRepository.deleteByUserId(userId);
    }
}
