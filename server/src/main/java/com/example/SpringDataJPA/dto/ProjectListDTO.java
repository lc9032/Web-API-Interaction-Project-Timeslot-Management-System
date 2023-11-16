package com.example.SpringDataJPA.dto;

/**
 * A DTO representing a summary view of a project, typically used in list views.
 * This DTO includes essential information about a project, such as its unique identifier (ID), name,
 * description, scheduled effort, assigned hours, and an indicator of whether it's over-assigned.
 */
public class ProjectListDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer scheduledEffort;
    private Double assignedHours;
    private Boolean overAssigned;

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

    public Double getAssignedHours() {
        return assignedHours;
    }

    public void setAssignedHours(Double assignedHours) {
        this.assignedHours = assignedHours;
    }

    public void setOverAssigned(Boolean overAssigned) {
        this.overAssigned = overAssigned;
    }

    public Boolean getOverAssigned() {
        return overAssigned;
    }
}
