package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing time slots, including creation, deletion, and retrieval.
 * It handles interactions between persons, projects, and time slots.
 */
@Service
public class TimeSlotService {
    /**
     * The repository for managing Person entities.
     */
    private final PersonRepository personRepository;

    /**
     * The repository for managing Project entities.
     */
    private final ProjectRepository projectRepository;

    /**
     * The repository for managing TimeSlot entities.
     */
    private final TimeSlotRepository timeSlotRepository;

    /**
     * Service for managing persons, used for interactions with person data.
     */
    private final PersonService personService;

    /**
     * Service for managing projects, used for interactions with project data.
     */
    private final ProjectService projectService;

    /**
     * Constructs a new TimeSlotService instance with the provided repositories and services.
     *
     * @param personRepository   The repository for accessing and managing person-related data.
     * @param projectRepository  The repository for accessing and managing project-related data.
     * @param timeSlotRepository The repository for accessing and managing time slot-related data.
     * @param personService      The service for managing persons, used for interactions with person data.
     * @param projectService     The service for managing projects, used for interactions with project data.
     */
    @Autowired
    public TimeSlotService(PersonRepository personRepository, ProjectRepository projectRepository, TimeSlotRepository timeSlotRepository, PersonService personService, ProjectService projectService) {
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.personService = personService;
        this.projectService = projectService;
    }

    /**
     * Retrieves a list of TimeSlotListDTO objects, which represent all time slots in the system.
     *
     * @return A list of TimeSlotListDTO objects, each containing details of a time slot.
     */
    public Page<TimeSlotListDTO> getAllTimeSlotLists(Pageable pageable) {
        Page<TimeSlot> timeSlotsPage = timeSlotRepository.findAll(pageable);
        return timeSlotsPage.map(this::convertEntityToTimeSlotListDTO);
    }

    private TimeSlotListDTO convertEntityToTimeSlotListDTO(TimeSlot timeSlot){
        TimeSlotListDTO timeSlotListDTO = new TimeSlotListDTO();
        timeSlotListDTO.setId(timeSlot.getId());
        timeSlotListDTO.setDate(timeSlot.getDate());
        timeSlotListDTO.setStartTime(timeSlot.getStartTime());
        timeSlotListDTO.setEndTime(timeSlot.getEndTime());
        timeSlotListDTO.setDescription(timeSlot.getDescription());

        return timeSlotListDTO;
    }

    /**
     * Retrieves the details of a specific TimeSlot identified by its unique ID.
     *
     * @param id The unique ID of the TimeSlot to retrieve details for.
     * @return A TimeSlotDetailsDTO object containing details of the specified TimeSlot.
     */
    public TimeSlotDetailsDTO getTimeSlotDetailsById(int id) {
        TimeSlot timeSlot = timeSlotRepository.getReferenceById(id);
        return convertEntityToTimeSlotDetailsDTO(timeSlot);
    }

    private TimeSlotDetailsDTO convertEntityToTimeSlotDetailsDTO(TimeSlot timeSlot){
        TimeSlotDetailsDTO timeSlotDetailsDTO = new TimeSlotDetailsDTO();

        timeSlotDetailsDTO.setTimeSlotId(timeSlot.getId());
        timeSlotDetailsDTO.setPersonId(timeSlot.getPersonId());
        timeSlotDetailsDTO.setPersonFullName(timeSlot.getPerson().getFullName());
        timeSlotDetailsDTO.setProjectId(timeSlot.getProject().getId());
        timeSlotDetailsDTO.setProjectName(timeSlot.getProject().getName());
        timeSlotDetailsDTO.setDate(timeSlot.getDate());
        timeSlotDetailsDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDetailsDTO.setEndTime(timeSlot.getEndTime());
        timeSlotDetailsDTO.setDescription(timeSlot.getDescription());
        timeSlotDetailsDTO.setDuration(calculateDurationInHours(timeSlot.getId()));

        return timeSlotDetailsDTO;
    }

    private Double calculateDurationInHours(int id){
        TimeSlot timeSlot = timeSlotRepository.getReferenceById(id);
        long durationMinutes = Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toMinutes();

        return (double) durationMinutes / 60.0;
    }

    /**
     * Saves a new TimeSlot based on the provided TimeSlotCreateDTO.
     *
     * @param timeSlotDTO The TimeSlotCreateDTO containing the details of the TimeSlot to be created.
     * @throws IllegalArgumentException If the specified project is not assigned to the person.
     */
    public void saveTimeslot(TimeSlotCreateDTO timeSlotDTO){
        Integer personId = timeSlotDTO.getPersonId();
        Integer projectId = timeSlotDTO.getProjectId();
        if(projectService.isProjectAssignedToPerson(personId,projectId)) {
            PersonDTO personDTO = personService.getPersonById(personId);//timeSlotDTO.getPerson();
            Person person = new Person(personDTO.getFirstName(), personDTO.getLastName());
            person.setId(personDTO.getId());

            ProjectDTO projectDTO = projectService.getProjectById(projectId);//timeSlotDTO.getProject();
            Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getScheduledEffort());
            project.setId(projectDTO.getId());

            TimeSlot timeSlot = new TimeSlot(timeSlotDTO.getDate(),
                    timeSlotDTO.getStartTime(),
                    timeSlotDTO.getEndTime(),
                    timeSlotDTO.getDescription(),
                    person,
                    project
            );
            timeSlotRepository.save(timeSlot);
        }
        else{
            throw new IllegalArgumentException("Project is not assigned to the person");
        }
    }

    /**
     * Deletes a TimeSlot with the specified ID and removes it from associated Person and Project entities.
     *
     * @param id The ID of the TimeSlot to be deleted.
     */
    public void deleteTimeslot(int id){
        TimeSlot timeSlot = timeSlotRepository.getReferenceById(id);

        Person person = timeSlot.getPerson();
        Project project = timeSlot.getProject();

        person.getTimeSlots().remove(timeSlot);
        personRepository.save(person);

        project.getTimeSlots().remove(timeSlot);
        projectRepository.save(project);

        timeSlotRepository.delete(timeSlot);
    }
}
