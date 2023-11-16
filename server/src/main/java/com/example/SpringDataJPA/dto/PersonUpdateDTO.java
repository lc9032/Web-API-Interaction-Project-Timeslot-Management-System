package com.example.SpringDataJPA.dto;

/**
 * A DTO representing the information required to update a person's details.
 * This DTO is typically used when updating a person's first name or last name.
 */
public class PersonUpdateDTO {
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
