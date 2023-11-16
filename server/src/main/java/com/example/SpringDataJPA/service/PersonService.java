package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The PersonService class provides services related to managing persons
 */
@Service
public class PersonService {
    /**
     * The repository for managing Person entities.
     */
    private final PersonRepository personRepository;

    /**
     * The repository for managing TimeSlot entities.
     */
    private final TimeSlotRepository timeSlotRepository;

    /**
     * The repository for managing Project entities.
     */
    private final ProjectRepository projectRepository;


    /**
     * Constructs a new PersonService with the specified repositories.
     *
     * @param personRepository   The repository for managing Person entities.
     * @param timeSlotRepository The repository for managing TimeSlot entities.
     * @param projectRepository  The repository for managing Project entities.
     */
    @Autowired
    public PersonService(PersonRepository personRepository, TimeSlotRepository timeSlotRepository,
                         ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Retrieves a list of all persons in the system.
     *
     * @return A list of PersonDTO objects representing all persons.
     */
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a person by their unique identifier.
     *
     * @param id The unique identifier of the person to retrieve.
     * @return A PersonDTO object representing the retrieved person.
     */
    public PersonDTO getPersonById(int id) {
        Person person = personRepository.getReferenceById(id);
        return convertEntityToDTO(person);
    }

    private PersonDTO convertEntityToDTO(Person person){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());

        Set<TimeSlotDTO> timeSlotDTOS = person.getTimeSlots().stream()
                .map(this::convertToTimeSlotDTO)
                .collect(Collectors.toSet());

        personDTO.setTimeslots(timeSlotDTOS);

        Set<ProjectDTO> projectDTOS = person.getProjects().stream()
                .map(this::convertToProjectDTO)
                .collect(Collectors.toSet());

        personDTO.setProjects(projectDTOS);

        return personDTO;
    }

    /**
     * Retrieves detailed information about a person by their unique identifier.
     *
     * @param id The unique identifier of the person to retrieve details for.
     * @return A PersonDetailsDTO object containing detailed information about the person.
     */
    public PersonDetailsDTO getPersonDetailsById(Integer id){
        Person person = personRepository.getReferenceById(id);

        return convertEntityToPersonDetailsDTO(person);
    }

    private PersonDetailsDTO convertEntityToPersonDetailsDTO(Person person){

        Integer personId = person.getId();

        PersonDetailsDTO personDetailsDTO = new PersonDetailsDTO();
        personDetailsDTO.setId(personId);
        personDetailsDTO.setFirstName(person.getFirstName());
        personDetailsDTO.setLastName(person.getLastName());

        Set<TimeSlotDTO> timeSlotDTOS = person.getTimeSlots().stream()
                .map(this::convertToTimeSlotDTO)
                .collect(Collectors.toSet());

        personDetailsDTO.setTimeslots(timeSlotDTOS);


        Set<PersonProjectDTO> projectWorkDTOs = person.getProjects().stream()
                .map(project -> convertToPersonProjectDTO(project, personId))
                .collect(Collectors.toSet());

        personDetailsDTO.setPersonProjectDTOS(projectWorkDTOs);

        return personDetailsDTO;
    }

    private PersonProjectDTO convertToPersonProjectDTO(Project project, Integer personId) {
        PersonProjectDTO personProjectDTO = new PersonProjectDTO();
        personProjectDTO.setId(project.getId());
        personProjectDTO.setName(project.getName());
        personProjectDTO.setDescription(project.getDescription());
        personProjectDTO.setScheduledEffort(project.getScheduledEffort());

        Double hours = calculateHoursForPersonAndProject(personId, project.getId());
        Double percentage = (hours / calculateTotalHours(personId)) * 100;

        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format to two decimal places
        String formattedPercentage = decimalFormat.format(percentage);

        personProjectDTO.setHours(hours);
        personProjectDTO.setPercentage(formattedPercentage);

        return personProjectDTO;
    }

    /**
     * Retrieves a list of simplified person representations, suitable for displaying in lists.
     *
     * @return A list of PersonListDTO objects representing persons for list display.
     */
    public List<PersonListDTO> getAllPersonLists() {
        return personRepository.findAll()
                .stream()
                .map(this::convertEntityToPersonListDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a simplified representation of a person by their unique identifier.
     *
     * @param id The unique identifier of the person to retrieve.
     * @return A PersonListDTO object representing the person for list display.
     */
    public PersonListDTO getPersonListById(int id) {
        Person person = personRepository.getReferenceById(id);

        return convertEntityToPersonListDTO(person);
    }

    private PersonListDTO convertEntityToPersonListDTO(Person person){
        PersonListDTO personListDTO = new PersonListDTO();
        personListDTO.setId(person.getId());
        personListDTO.setFirstName(person.getFirstName());
        personListDTO.setLastName(person.getLastName());

        return personListDTO;
    }

    private TimeSlotDTO convertToTimeSlotDTO(TimeSlot timeSlot) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setId(timeSlot.getId());
        timeSlotDTO.setDate(timeSlot.getDate());
        timeSlotDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDTO.setEndTime(timeSlot.getEndTime());
        timeSlotDTO.setDescription(timeSlot.getDescription());

        return timeSlotDTO;
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setScheduledEffort(project.getScheduledEffort());

        return projectDTO;
    }

    /**
     * Saves a new person based on the provided PersonCreateDTO.
     *
     * @param personCreateDTO The DTO containing information to create the person.
     */
    public void savePerson(PersonCreateDTO personCreateDTO) {
        Person person = new Person(personCreateDTO.getFirstName(), personCreateDTO.getLastName());
        personRepository.save(person);
    }

    /**
     * Updates an existing person's information based on the provided PersonUpdateDTO.
     *
     * @param personUpdateDTO The DTO containing updated information for the person.
     */
    public void updatePerson(PersonUpdateDTO personUpdateDTO) {
        Person person = personRepository.getReferenceById(personUpdateDTO.getId());

        person.setFirstName(personUpdateDTO.getFirstName());
        person.setLastName(personUpdateDTO.getLastName());

        personRepository.save(person);
    }

    /**
     * Deletes a person and removes their associations with projects.
     *
     * @param id The unique identifier of the person to delete.
     */
    public void deletePerson(int id) {
        Person person = personRepository.getReferenceById(id);

        for (Project project : person.getProjects()) {
            project.getPersons().remove(person);
            projectRepository.save(project);
        }
        person.getProjects().clear();

        personRepository.delete(person);
    }

    private Double calculateTotalHours(int personId){
        double totalHours = 0.0;
        long totalMinutes = 0;

        Person person = personRepository.getReferenceById(personId);

        for (TimeSlot timeSlot : person.getTimeSlots()) {
            totalMinutes += Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toMinutes();
        }
        totalHours = (double) totalMinutes / 60.0;

        return totalHours;
    }

    private Double calculateHoursForPersonAndProject(int personId, int projectId){
        double totalHours = 0.0;
        long totalMinutes = 0;

        Person person = personRepository.getReferenceById(personId);

        for (TimeSlot timeSlot : person.getTimeSlots()) {
            if (timeSlot.getProject().getId().equals(projectId)) {
                totalMinutes += Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toMinutes();
            }
        }
        totalHours = (double) totalMinutes / 60.0;

        return totalHours;
    }
}
