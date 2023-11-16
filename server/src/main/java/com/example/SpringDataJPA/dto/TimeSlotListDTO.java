package com.example.SpringDataJPA.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A DTO representing a list of time slots.
 * This DTO includes essential details for each time slot, such as its unique identifier (ID),
 * date, start time, end time, and a brief description. It is typically used for displaying
 * a list of time slots to users.
 */
public class TimeSlotListDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
