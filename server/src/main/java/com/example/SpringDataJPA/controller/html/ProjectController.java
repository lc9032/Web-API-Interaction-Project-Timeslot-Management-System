package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling requests related to projects in the application.
 */
@Controller
@RequestMapping("project")
public class ProjectController {
    /**
     * Service for managing Project entities.
     */
    @Autowired
    private ProjectService projectService;

    /**
     * Service for managing Person entities.
     */
    @Autowired
    private PersonService personService;

    /**
     * Handles requests to view a list of projects.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @return The name of the HTML template to render, in this case, "project-index".
     */
    @GetMapping("/")
    public String project(Model model) {
        List<ProjectListDTO> projectLists = projectService.getAllProjectLists();
        model.addAttribute("projectLists", projectLists);
        return "project-index";
    }

    /**
     * Handles requests to view details of a specific project.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @param id The unique identifier of the project.
     * @param sourcePage The source page from which the request was made.
     * @param personId The unique identifier of the associated person (if available).
     * @return The name of the HTML template to render, in this case, "project-detail".
     */
    @GetMapping(value = "/{id}")
    public String projectDetail(Model model,
                                @PathVariable Integer id,
                                @RequestParam(name = "sourcePage", defaultValue = "") String sourcePage,
                                @RequestParam(name = "personId", required = false) Integer personId,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ProjectDetailsDTO projectDetailsDTO = projectService.getProjectDetails(id, pageable);

        model.addAttribute("projectDetails", projectDetailsDTO);
        model.addAttribute("sourcePage", sourcePage);
        model.addAttribute("personId", personId);

        return "project-detail";
    }

    /**
     * Handles requests to create a new project.
     *
     * @return The name of the HTML template to render, in this case, "project-create".
     */
    @GetMapping("/create")
    public String createProject() {
        return "project-create";
    }

    /**
     * Handles requests to save a newly created project.
     *
     * @param projectCreateDTO The data for the new project.
     * @return A redirect to the list of projects.
     */
    @PostMapping("/save")
    public String saveProject(@ModelAttribute ProjectCreateDTO projectCreateDTO) {
        projectService.saveProject(projectCreateDTO);
        return "redirect:/project/";
    }

    /**
     * Handles requests to show the form for assigning a project to a person.
     *
     * @param personId The unique identifier of the associated person (if available).
     * @param projectId The unique identifier of the associated project (if available).
     * @param sourcePage The source page from which the request was made.
     * @param model The Spring MVC model for rendering data to the view.
     * @return The name of the HTML template to render, in this case, "project-assign".
     */
    @GetMapping("/assign")
    public String showAssignProjectForm(
        @RequestParam(name = "personId", required = false) Integer personId,
        @RequestParam(name = "projectId", required = false) Integer projectId,
        @RequestParam(name = "sourcePage", required = false) String sourcePage,
        Model model) {

        List<PersonDTO> personDTOS = new ArrayList<>();
        List<ProjectDTO> projectDTOS = new ArrayList<>();

        if (personId != null) {
            PersonDTO personDTO = personService.getPersonById(personId);
            personDTOS.add(personDTO);
        } else {
            personDTOS.addAll(personService.getAllPersons());
        }

        if (projectId != null) {
            ProjectDTO projectDTO = projectService.getProjectById(projectId);
            projectDTOS.add(projectDTO);
        } else {
            projectDTOS.addAll(projectService.getAllProjects());
        }

        model.addAttribute("people", personDTOS);
        model.addAttribute("projects", projectDTOS);
        model.addAttribute("sourcePage", sourcePage);

        return "project-assign";
    }

    /**
     * Handles requests to assign a project to a person.
     *
     * @param assignProjectDTO The data for the project assignment.
     * @param sourcePage The source page from which the request was made.
     * @return A redirect based on the source page.
     */
    @PostMapping("/assign")
    public String assignProjectToPerson(@ModelAttribute AssignProjectDTO assignProjectDTO,
                                        @RequestParam(value = "sourcePage", required = false) String sourcePage) {
        String toReturn = "";
        Integer personId = assignProjectDTO.getPersonId();
        Integer projectId = assignProjectDTO.getProjectId();

        projectService.assignProjectToPerson(assignProjectDTO);

        if (sourcePage != null && sourcePage.equals("person-detail")) {
            toReturn = "redirect:/person/" + personId;
        } else {
            toReturn = "redirect:/project/" + projectId;
        }

        return toReturn;
    }

    /**
     * Handles requests to delete a project.
     *
     * @param id The unique identifier of the project to delete.
     * @return A redirect to the list of projects.
     */
    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable("id") String id) {
        int projectId = Integer.parseInt(id);

        projectService.deleteProject(projectId);

        return "redirect:/project/";
    }

}
