package com.example.SpringDataJPA.dto;

/**
 * A DTO representing the information required to create a new project.
 * This DTO is typically used when creating a new project, providing its name, description, and scheduled effort.
 */
public class ProjectCreateDTO {
    private String name;
    private String description;
    private Integer scheduledEffort;

    //getters and setters
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
