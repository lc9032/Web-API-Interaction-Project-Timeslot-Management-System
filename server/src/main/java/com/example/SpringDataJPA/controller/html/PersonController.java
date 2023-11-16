package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling requests related to persons in the application.
 */
@Controller
@RequestMapping("person")
public class PersonController {
    /**
     * Service for managing Person entities.
     */
    @Autowired
    private PersonService personService;

    /**
     * Service for managing TimeSlot entities.
     */
    @Autowired
    private TimeSlotService timeSlotService;

    /**
     * Service for managing Project entities.
     */
    @Autowired
    private ProjectService projectService;

    /**
     * Handles requests to view a list of persons.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @return The name of the HTML template to render, in this case, "person-index".
     */
    @GetMapping("/")
    public String person(Model model) {
        List<PersonListDTO> personLists = personService.getAllPersonLists();
        model.addAttribute("personLists", personLists);
        return "person-index";
    }

    /**
     * Handles requests to view details of a specific person.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @param id The unique identifier of the person.
     * @return The name of the HTML template to render, in this case, "person-detail".
     */
    @GetMapping(value = "/{id}")
    public String personDetail(Model model, @PathVariable Integer id) {
        PersonDetailsDTO personDetailsDTO = personService.getPersonDetailsById(id);
        model.addAttribute("personDetails", personDetailsDTO);
        return "person-detail";
    }

    /**
     * Handles requests to create a new person.
     *
     * @return The name of the HTML template to render, in this case, "person-create".
     */
    @GetMapping("/create")
    public String createPerson() {
        return "person-create";
    }

    /**
     * Handles requests to save a newly created person.
     *
     * @param personCreateDTO The data for the new person.
     * @return A redirect to the list of persons.
     */
    @PostMapping("/save")
    public String savePerson(@ModelAttribute PersonCreateDTO personCreateDTO) {
        personService.savePerson(personCreateDTO);
        return "redirect:/person/";
    }

    /**
     * Handles requests to view a form for updating a person.
     *
     * @param id The unique identifier of the person to update.
     * @param model The Spring MVC model for rendering data to the view.
     * @return The name of the HTML template to render, in this case, "person-update".
     */
    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        PersonListDTO personList = personService.getPersonListById(id);
        model.addAttribute("personList", personList);
        return "person-update";
    }

    /**
     * Handles requests to update a person's information.
     *
     * @param updatedPerson The updated data for the person.
     * @return A redirect to view the updated person's details.
     */
    @PostMapping("/{id}/update")
    public String updatePerson(@ModelAttribute PersonUpdateDTO updatedPerson) {
        String id = updatedPerson.getId().toString();
        personService.updatePerson(updatedPerson);
        return "redirect:/person/" + id;
    }

    /**
     * Handles requests to delete a person.
     *
     * @param id The unique identifier of the person to delete.
     * @return A redirect to the list of persons.
     */
    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") String id) {
        int personId = Integer.parseInt(id);
        personService.deletePerson(personId);
        return "redirect:/person/";
    }
}
