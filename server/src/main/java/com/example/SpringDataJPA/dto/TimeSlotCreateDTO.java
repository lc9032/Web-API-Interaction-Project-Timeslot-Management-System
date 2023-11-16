package com.example.SpringDataJPA.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A DTO representing information required to create a new time slot.
 * This DTO includes details such as the person's unique identifier (ID), project's unique
 * identifier (ID), date, start time, end time, and a description of the time slot.
 */
public class TimeSlotCreateDTO {
    private Integer personId;
    private Integer projectId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    //getters and setters
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

}
