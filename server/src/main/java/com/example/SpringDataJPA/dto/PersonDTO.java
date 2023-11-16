package com.example.SpringDataJPA.dto;

import java.util.Set;

/**
 * A DTO representing a person, including their ID, first name, last name, associated projects,
 * and time slots.
 */
public class PersonDTO {
    private Integer  id;
    private String firstName;
    private String lastName;
    private Set<ProjectDTO> projectDTOs;
    private Set<TimeSlotDTO> timeslots;

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProjectDTO> getProjects() {
        return projectDTOs;
    }

    public void setProjects(Set<ProjectDTO> projectDTOs) {
        this.projectDTOs = projectDTOs;
    }

    public Set<TimeSlotDTO> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(Set<TimeSlotDTO> timeslots) {
        this.timeslots = timeslots;
    }


}
