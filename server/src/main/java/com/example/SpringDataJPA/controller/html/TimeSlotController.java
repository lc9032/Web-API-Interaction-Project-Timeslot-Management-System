package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling requests related to time slots in the application.
 */
@Controller
@RequestMapping("timeSlot")
public class TimeSlotController {
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
     * Handles requests to view a list of time slots.
     *
     * @param page The page number for pagination (default is 0).
     * @param size The number of items per page (default is 10).
     * @return A ModelAndView containing the paginated list of time slots to be displayed in the view.
     */
    @GetMapping("/")
    public ModelAndView timeSlot(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TimeSlotListDTO> timeSlotListDTOs = timeSlotService.getAllTimeSlotLists(pageable);

        ModelAndView modelAndView = new ModelAndView("timeSlot-index");
        modelAndView.addObject("timeSlots", timeSlotListDTOs);
        return modelAndView;
    }

    /**
     * Handles requests to view details of a specific time slot.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @param id The unique identifier of the time slot.
     * @param sourcePage The source page from which the request was made.
     * @param personId The unique identifier of the associated person (if available).
     * @return The name of the HTML template to render, in this case, "timeSlot-detail".
     */
    @GetMapping("/{id}")
    public String timeSlotDetail(Model model,
                                 @PathVariable int id,
                                 @RequestParam(name = "sourcePage", defaultValue = "") String sourcePage,
                                 @RequestParam(name = "personId", required = false) Integer personId) {
        TimeSlotDetailsDTO timeSlotDetailsDTO = timeSlotService.getTimeSlotDetailsById(id);

        model.addAttribute("timeslotDetails", timeSlotDetailsDTO);
        model.addAttribute("sourcePage", sourcePage);
        model.addAttribute("personId", personId);

        return "timeSlot-detail";
    }

    /**
     * Handles requests to show the form for adding a time slot.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @param sourcePage The source page from which the request was made.
     * @param personId The unique identifier of the associated person (if available).
     * @return The name of the HTML template to render, in this case, "timeSlot-create".
     */
    @GetMapping("/create")
    public String showAddTimeslotForm(Model model,
                                      @RequestParam(name = "sourcePage", required = false) String sourcePage,
                                      @RequestParam(name = "personId", required = false) Integer personId) {
        List<PersonDTO> personDTOS = new ArrayList<>();
        List<ProjectDTO> projectDTOS = projectService.getAllProjects();

        if (personId != null) {
            PersonDTO personDTO = personService.getPersonById(personId);
            personDTOS.add(personDTO);
        } else {
            personDTOS.addAll(personService.getAllPersons());
        }

        model.addAttribute("people", personDTOS);
        model.addAttribute("projects", projectDTOS);
        model.addAttribute("sourcePage", sourcePage);
        return "timeSlot-create";
    }

    /**
     * Handles requests to add a new time slot.
     *
     * @param timeSlotCreateDTO The data for the new time slot.
     * @param sourcePage The source page from which the request was made.
     * @param redirectAttributes Used for adding flash attributes for redirection.
     * @return A redirection URL based on the outcome of the operation.
     */
    @PostMapping("/create")
    public String addTimeslot(@ModelAttribute TimeSlotCreateDTO timeSlotCreateDTO,
                              @RequestParam(value = "sourcePage", required = false) String sourcePage,
                              RedirectAttributes redirectAttributes) {

        String toReturn = "";

        Integer personId = timeSlotCreateDTO.getPersonId();

        if (sourcePage != null && sourcePage.equals("person-detail")) {
            toReturn = "redirect:/person/" + personId;
        } else {
            toReturn = "redirect:/timeSlot/";
        }

        try {
            timeSlotService.saveTimeslot(timeSlotCreateDTO);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Project is not assigned to the person");
            toReturn = "redirect:/error";
        }

        return toReturn;
    }

    /**
     * Handles requests to delete a time slot.
     *
     * @param id The unique identifier of the time slot to delete.
     * @return A redirect to the list of time slots.
     */
    @PostMapping("/{id}/delete")
    public String deleteTimeslot(@PathVariable("id") String id) {
        int timeSlotId = Integer.parseInt(id);

        timeSlotService.deleteTimeslot(timeSlotId);

        return "redirect:/timeSlot/";
    }
}
