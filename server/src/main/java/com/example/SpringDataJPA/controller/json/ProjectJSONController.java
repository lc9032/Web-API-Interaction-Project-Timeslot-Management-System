package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for managing Project entities as JSON.
 */
@RestController
@RequestMapping("project")
public class ProjectJSONController {
    /**
     * Service for managing Project entities.
     */
    @Autowired
    private ProjectService projectService;

    /**
     * Retrieves a list of Project entities in JSON format.
     *
     * @return A list of ProjectListDTO objects representing Project entities.
     */
    @GetMapping(value = "/.json", produces = "application/json")
    @ResponseBody
    public List<ProjectListDTO> getAllProjectList() {
        return projectService.getAllProjectLists();
    }

    /**
     * Retrieves detailed information about a specific Project entity in JSON format.
     *
     * @param id The ID of the Project entity to retrieve.
     * @return A ProjectDetailsDTO object representing detailed information about the Project entity.
     */
    @GetMapping(value = "/{id}.json", produces = "application/json")
    @ResponseBody
    public ProjectDetailsDTO projectDetail(@PathVariable("id") Integer id,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page, size);
        return projectService.getProjectDetails(id, pageable);
    }

    /**
     * Creates a new Project entity based on the provided JSON data.
     *
     * @param projectCreateDTO The JSON data representing the new Project entity.
     */
    @PostMapping(value = "/create.json", consumes = "application/json")
    public void createProject(@RequestBody ProjectCreateDTO projectCreateDTO) {
        projectService.saveProject(projectCreateDTO);
    }

    /**
     * Assigns a Project to a Person based on the provided JSON data.
     *
     * @param assignProjectDTO The JSON data representing the assignment of a Project to a Person.
     */
    @PostMapping(value = "/assign.json", consumes = "application/json")
    public void assignProjectToPerson(@RequestBody AssignProjectDTO assignProjectDTO) {
        projectService.assignProjectToPerson(assignProjectDTO);
    }

    /**
     * Deletes a specific Project entity by its ID.
     *
     * @param id The ID of the Project entity to delete.
     */
    @DeleteMapping(value = "/delete/{id}.json")
    public void deleteProject(@PathVariable("id") Integer id) {
        projectService.deleteProject(id);
    }

}
