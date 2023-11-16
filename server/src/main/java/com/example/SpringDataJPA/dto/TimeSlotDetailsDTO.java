package com.example.SpringDataJPA.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A DTO representing detailed information about a time slot.
 * This DTO includes details such as the time slot's unique identifier (ID), associated
 * person's unique identifier (ID) and full name, associated project's unique identifier (ID)
 * and name, date, start time, end time, duration, and description of the time slot.
 */
public class TimeSlotDetailsDTO {
    private Integer timeSlotId;
    private Integer personId;
    private String personFullName;
    private Integer projectId;
    private String projectName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double duration;
    private String description;

    //getters and setters
    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String personFullName) {
        this.personFullName = personFullName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
