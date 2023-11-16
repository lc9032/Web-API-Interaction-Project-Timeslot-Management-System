package com.example.SpringDataJPA.dto;

/**
 * A DTO representing a person's participation in a project, including project details and
 * additional information such as hours worked and percentage of completion.
 * This DTO is typically used to provide summarized project information for a specific person.
 */
public class PersonProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer scheduledEffort;
    private Double hours;
    private String percentage;

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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
