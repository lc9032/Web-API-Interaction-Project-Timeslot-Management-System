package com.example.SpringDataJPA.dto;

import java.util.Set;

/**
 * A DTO representing the details of a person, including their ID, first name, last name,
 * associated projects, and time slots.
 */
public class PersonDetailsDTO {
    private Integer  id;
    private String firstName;
    private String lastName;
    private Set<PersonProjectDTO> personProjectDTOS;
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

    public Set<PersonProjectDTO> getPersonProjectDTOS() {
        return personProjectDTOS;
    }

    public void setPersonProjectDTOS(Set<PersonProjectDTO> personProjectDTOS) {
        this.personProjectDTOS = personProjectDTOS;
    }

    public Set<TimeSlotDTO> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(Set<TimeSlotDTO> timeslots) {
        this.timeslots = timeslots;
    }
}
