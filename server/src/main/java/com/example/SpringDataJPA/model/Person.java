package com.example.SpringDataJPA.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a person in the system. This class is used to store information about individuals,
 * including their first name, last name, associated time slots, and assigned projects.
 */
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    Integer id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    Set<TimeSlot> timeSlots;

    @ManyToMany
    @JoinTable(name = "person_project", joinColumns = @JoinColumn(name = "p_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    Set<Project> projects;

    /**
     * Default constructor for the Person class.
     */
    protected Person() {
        // Default constructor
    }

    /**
     * Creates a new Person object with the specified first name and last name.
     *
     * @param first_name The first name of the person.
     * @param last_name  The last name of the person.
     */
    public Person(String first_name, String last_name){
        this.firstName = first_name;
        this.lastName = last_name;
        this.timeSlots = new HashSet<>();
        this.projects = new HashSet<>();
    }

    /**
     * Get the unique identifier (ID) of the person.
     *
     * @return The ID of the person.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier (ID) of the person.
     *
     * @param id The ID to set for the person.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the first name of the person.
     *
     * @return The first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstName The first name to set for the person.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the person.
     *
     * @return The last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     *
     * @param lastName The last name to set for the person.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the full name of the person by combining their first name and last name.
     *
     * @return The full name of the person.
     */
    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * Get the set of time slots associated with this person.
     *
     * @return A set of time slots.
     */
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Add a time slot to the set of time slots associated with this person.
     *
     * @param timeslot The time slot to add.
     */
    public void addTimeslot(TimeSlot timeslot) {
        timeSlots.add(timeslot);
    }

    /**
     * Get the set of projects assigned to this person.
     *
     * @return A set of projects.
     */
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * Add a project to the set of projects assigned to this person.
     * This method also updates the reverse association in the project entity.
     *
     * @param project The project to add.
     */
    public void addProject(Project project) {
        projects.add(project);
        project.getPersons().add(this);
    }

    /**
     * Remove a project from the set of projects assigned to this person.
     * This method also updates the reverse association in the project entity.
     *
     * @param project The project to remove.
     */
    public void removeProject(Project project) {
        projects.remove(project);
        project.getPersons().remove(this);
    }
}
