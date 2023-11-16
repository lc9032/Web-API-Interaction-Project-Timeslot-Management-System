package com.example.SpringDataJPA.dto;

/**
 * A DTO representing a simplified view of a person, including their ID, first name, and last name.
 * This DTO is typically used for listing persons in a concise manner.
 */
public class PersonListDTO {
    private Integer id;
    private String firstName;
    private String lastName;

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
}
