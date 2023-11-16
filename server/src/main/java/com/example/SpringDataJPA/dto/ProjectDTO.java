package com.example.SpringDataJPA.dto;

/**
 * A DTO representing basic information about a project.
 * This DTO is typically used to transfer project data between different layers of an application.
 * It includes fields such as the project's unique identifier (ID), name, description, and scheduled effort.
 */
public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer scheduledEffort;

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScheduledEffort() {
        return scheduledEffort;
    }

    public void setScheduledEffort(Integer scheduledEffort) {
        this.scheduledEffort = scheduledEffort;
    }

}
