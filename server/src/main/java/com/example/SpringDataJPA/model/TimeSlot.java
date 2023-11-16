package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a time slot entity in the application.
 */
@Entity
@Table(name = "timeslot")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "t_id")
    Integer id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "start_time")
    LocalTime startTime;

    @Column(name = "end_time")
    LocalTime endTime;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "p_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    /**
     * Default constructor for the TimeSlot class.
     */
    protected TimeSlot() {
        // Default constructor
    }

    public TimeSlot(LocalDate date,LocalTime startTime,LocalTime endTime,String description, Person person, Project project){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.person = person;
        this.project = project;
    }

    /**
     * Gets the unique identifier (ID) of the time slot.
     *
     * @return The ID of the time slot.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the date of the time slot.
     *
     * @return The date of the time slot.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the start time of the time slot.
     *
     * @return The start time of the time slot.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the time slot.
     *
     * @return The end time of the time slot.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the description of the time slot.
     *
     * @return The description of the time slot.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the ID of the associated person.
     *
     * @return The ID of the associated person.
     */
    public Integer getPersonId() {
        return person.getId();
    }

    /**
     * Gets the associated person for this time slot.
     *
     * @return The associated person for this time slot.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the date of the time slot.
     *
     * @param date The date to set for the time slot.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the start time of the time slot.
     *
     * @param startTime The start time to set for the time slot.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the time slot.
     *
     * @param endTime The end time to set for the time slot.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the description of the time slot.
     *
     * @param description The description to set for the time slot.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the associated person for this time slot.
     *
     * @param person The person to associate with this time slot.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gets the associated project for this time slot.
     *
     * @return The associated project for this time slot.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the associated project for this time slot.
     *
     * @param project The project to associate with this time slot.
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
