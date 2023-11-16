package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a project entity in the application.
 */
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "scheduled_effort")
    Integer scheduledEffort;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    Set<TimeSlot> timeSlots;

    @ManyToMany(mappedBy = "projects")
    private Set<Person> persons;

    /**
     * Default constructor for the Project class.
     */
    protected Project(){
        // Default constructor
    }

    public Project(String name, String description, Integer scheduledEffort){
        this.name = name;
        this.description = description;
        this.scheduledEffort = scheduledEffort;
        this.timeSlots = new HashSet<>();
        this.persons = new HashSet<>();
    }

    /**
     * Gets the unique identifier (ID) of the project.
     *
     * @return The ID of the project.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier (ID) of the project.
     *
     * @param id The ID to set for the project.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the project.
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name The name to set for the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the project.
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description The description to set for the project.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the scheduled effort (e.g., hours) for the project.
     *
     * @return The scheduled effort for the project.
     */
    public Integer getScheduledEffort() {
        return scheduledEffort;
    }

    /**
     * Sets the scheduled effort (e.g., hours) for the project.
     *
     * @param scheduledEffort The scheduled effort to set for the project.
     */
    public void setScheduledEffort(Integer scheduledEffort) {
        this.scheduledEffort = scheduledEffort;
    }

    /**
     * Gets the set of persons associated with this project.
     *
     * @return The set of persons associated with this project.
     */
    public Set<Person> getPersons() {
        return persons;
    }

    /**
     * Sets the set of persons associated with this project.
     *
     * @param persons The set of persons to associate with this project.
     */
    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    /**
     * Gets the set of time slots associated with this project.
     *
     * @return The set of time slots associated with this project.
     */
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Sets the set of time slots associated with this project.
     *
     * @param timeSlots The set of time slots to associate with this project.
     */
    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    /**
     * Adds a time slot to the set of time slots associated with this project.
     *
     * @param timeslot The time slot to add.
     */
    public void addTimeslot(TimeSlot timeslot) {
        timeSlots.add(timeslot);
    }

}
