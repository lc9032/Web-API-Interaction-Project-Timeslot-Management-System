package com.example.SpringDataJPA.dto;

/**
 * A DTO representing the assignment of a project to a person.
 * It encapsulates the unique identifiers of a person and a project.
 */
public class AssignProjectDTO {
    private Integer personId;
    private Integer projectId;

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
}
