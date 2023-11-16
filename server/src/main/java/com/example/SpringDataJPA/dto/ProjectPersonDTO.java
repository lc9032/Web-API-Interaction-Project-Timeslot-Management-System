package com.example.SpringDataJPA.dto;

import java.util.List;

/**
 * A DTO representing information about a person's involvement in a project.
 * This DTO includes details such as the person's unique identifier (ID), full name, allocated hours,
 * percentage of allocation, and a list of time slot information related to the project.
 */
public class ProjectPersonDTO {
    private Integer personId;
    private String personFullName;
    private double hours;
    private String percentage;
    private List<TimeSlotListDTO> timeSlotLists;

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

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public List<TimeSlotListDTO> getTimeSlotLists() {
        return timeSlotLists;
    }

    public void setTimeSlotLists(List<TimeSlotListDTO> timeSlotLists) {
        this.timeSlotLists = timeSlotLists;
    }
}
