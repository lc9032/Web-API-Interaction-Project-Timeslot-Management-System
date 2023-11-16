package com.example.SpringDataJPA.dto;

/**
 * A DTO representing the data needed to create a new person.
 * It contains the first name and last name of the person.
 */
public class PersonCreateDTO {
    private String firstName;
    private String lastName;

    //getters and setters
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
}
