package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for managing Person entities as JSON.
 */
@RestController
@RequestMapping("person")
public class PersonJSONController {
    /**
     * Service for managing Person entities.
     */
    @Autowired
    private PersonService personService;

    /**
     * Retrieves a list of Person entities in JSON format.
     *
     * @return A list of PersonListDTO objects representing Person entities.
     */
    @GetMapping(value = "/.json", produces = "application/json")
    @ResponseBody
    public List<PersonListDTO> getAllPersonsList() {
        return personService.getAllPersonLists();
    }

    /**
     * Retrieves detailed information about a specific Person entity in JSON format.
     *
     * @param id The ID of the Person entity to retrieve.
     * @return A PersonDetailsDTO object representing detailed information about the Person entity.
     */
    @GetMapping(value = "/{id}.json", produces = "application/json")
    @ResponseBody
    public PersonDetailsDTO personDetail(@PathVariable("id") Integer id){
        return personService.getPersonDetailsById(id);
    }

    /**
     * Creates a new Person entity based on the provided JSON data.
     *
     * @param personCreateDTO The JSON data representing the new Person entity.
     */
    @PostMapping(value = "/create.json", consumes = "application/json")
    public void createPerson(@RequestBody PersonCreateDTO personCreateDTO) {
        personService.savePerson(personCreateDTO);
    }

    /**
     * Updates an existing Person entity based on the provided JSON data.
     *
     * @param personUpdateDTO The JSON data representing the updated Person entity.
     */
    @PutMapping(value = "/update/{id}.json", consumes = "application/json")
    public void updatePerson(@RequestBody PersonUpdateDTO personUpdateDTO) {
        personService.updatePerson(personUpdateDTO);
    }

    /**
     * Deletes a specific Person entity by its ID.
     *
     * @param id The ID of the Person entity to delete.
     */
    @DeleteMapping(value = "/delete/{id}.json")
    public void deletePerson(@PathVariable("id") Integer id) {
        personService.deletePerson(id);
    }

}
