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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The ProjectService class provides services related to managing projects.
 */
@Service
public class ProjectService {
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
     * Constructs a new ProjectService with the provided repositories.
     *
     * @param personRepository   The repository for managing person-related data.
     * @param timeSlotRepository The repository for managing time slot-related data.
     * @param projectRepository  The repository for managing project-related data.
     */
    @Autowired
    public ProjectService(PersonRepository personRepository, TimeSlotRepository timeSlotRepository,
                          ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Retrieves a list of all projects as Data Transfer Objects (DTOs).
     *
     * @return A list of ProjectDTOs representing all projects.
     */
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a project by its unique identifier and returns it as a Data Transfer Object (DTO).
     *
     * @param id The unique identifier of the project to retrieve.
     * @return A ProjectDTO representing the project with the specified ID, or null if not found.
     */
    public ProjectDTO getProjectById(int id) {
        Project project = projectRepository.getReferenceById(id);
        return convertEntityToDTO(project);
    }

    private ProjectDTO convertEntityToDTO(Project project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setScheduledEffort(project.getScheduledEffort());
        return projectDTO;
    }

    /**
     * Retrieves a list of all projects as Project List Data Transfer Objects (DTOs).
     *
     * @return A list of ProjectListDTOs representing all projects.
     */
    public List<ProjectListDTO> getAllProjectLists() {
        return projectRepository.findAll()
                .stream()
                .map(this::convertEntityToProjectListDTO)
                .collect(Collectors.toList());
    }

    private ProjectListDTO convertEntityToProjectListDTO(Project project){
        ProjectListDTO projectListDTO = new ProjectListDTO();
        projectListDTO.setId(project.getId());
        projectListDTO.setName(project.getName());
        projectListDTO.setDescription(project.getDescription());
        projectListDTO.setScheduledEffort(project.getScheduledEffort());
        projectListDTO.setAssignedHours(calculateAssignedHours(project.getId()));
        projectListDTO.setOverAssigned(projectListDTO.getScheduledEffort() < projectListDTO.getAssignedHours());
        return projectListDTO;
    }

    /**
     * Retrieves project details for the specified project ID.
     *
     * @param projectId The unique identifier of the project.
     * @return A ProjectDetailsDTO containing information about the project and its associated persons.
     */
    public ProjectDetailsDTO getProjectDetails(int projectId, Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        Project project = projectRepository.getReferenceById(projectId);
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        projectDetailsDTO.setProjectId(projectId);
        projectDetailsDTO.setProjectName(project.getName());
        projectDetailsDTO.setProjectDescription(project.getDescription());
        projectDetailsDTO.setScheduledEffort(project.getScheduledEffort());

        List<ProjectPersonDTO> projectPersonDTOS = getProjectPersonPage(projectId, page ,size);
        projectDetailsDTO.setProjectPersonDTOS(projectPersonDTOS);
        projectDetailsDTO.setCurrentPage(page);
        projectDetailsDTO.setPageSize(size);
        projectDetailsDTO.setTotalPages((int) Math.ceil((double) project.getPersons().size() / size));

        return projectDetailsDTO;
    }

    public List<ProjectPersonDTO> getProjectPersonPage(int projectId, int page, int size) {
        Project project = projectRepository.getReferenceById(projectId);
        Pageable pageable = PageRequest.of(page, size);

        List<ProjectPersonDTO> projectPersonDTOS = new ArrayList<>(project.getPersons()
                .stream()
                .map(person -> convertPersonToProjectPersonDTO(project, person))
                .toList());

        projectPersonDTOS.sort(Comparator.comparing(ProjectPersonDTO::getPersonId));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), projectPersonDTOS.size());

        return projectPersonDTOS.subList(start, end);
    }

    private ProjectPersonDTO convertPersonToProjectPersonDTO(Project project, Person person) {
        Integer projectId = project.getId();

        double totalHours = 0.0;
        for (Person p : project.getPersons()) {
            double hoursForPerson = calculateHoursForPersonAndProject(p.getId(), projectId);
            totalHours += hoursForPerson;
        }

        double hoursForPerson = calculateHoursForPersonAndProject(person.getId(), projectId);
        double percentage = (hoursForPerson / totalHours) * 100;
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format to two decimal places
        String formattedPercentage = decimalFormat.format(percentage);

        ProjectPersonDTO projectPersonDTO = new ProjectPersonDTO();
        projectPersonDTO.setPersonId(person.getId());
        projectPersonDTO.setPersonFullName(person.getFullName());
        projectPersonDTO.setHours(hoursForPerson);
        projectPersonDTO.setPercentage(formattedPercentage);

        List<TimeSlotListDTO> timeSlotListDTOS = person.getTimeSlots().stream()
                    .filter(timeSlot -> Objects.equals(timeSlot.getProject().getId(), projectId))
                    .map(this::convertEntityToTimeSlotListDTO)
                    .collect(Collectors.toList());
        projectPersonDTO.setTimeSlotLists(timeSlotListDTOS);

        return projectPersonDTO;
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
     * Assigns a project to a person based on the provided AssignProjectDTO.
     *
     * @param assignProjectDTO An AssignProjectDTO containing the person and project IDs.
     */
    public void assignProjectToPerson(AssignProjectDTO assignProjectDTO) {
        Integer personId = assignProjectDTO.getPersonId();
        Integer projectId = assignProjectDTO.getProjectId();

        Person person = personRepository.getReferenceById(personId);
        Project project = projectRepository.getReferenceById(projectId);

        person.addProject(project);
        personRepository.save(person);
    }

    /**
     * Checks if a project is assigned to a specific person.
     *
     * @param personId The unique identifier of the person.
     * @param projectId The unique identifier of the project.
     * @return True if the project is assigned to the person, false otherwise.
     */
    public boolean isProjectAssignedToPerson(int personId, int projectId){
        Person person = personRepository.getReferenceById(personId);

        return person.getProjects().stream().anyMatch(project -> project.getId() == projectId);
    }

    private Double calculateAssignedHours(int projectId){
        Project project = projectRepository.getReferenceById(projectId);
        double totalHours = 0.0;
        long totalMinutes = 0;

        for (TimeSlot timeSlot : project.getTimeSlots()) {
            totalMinutes += Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toMinutes();
        }
        totalHours = (double) totalMinutes / 60.0;

        return totalHours;
    }

    private Double calculateHoursForPersonAndProject(int personId, int projectId){
        double totalHours = 0.0;
        long totalMinutes = 0;

        Person person = personRepository.getReferenceById(personId);
        Project project = projectRepository.getReferenceById(projectId);

        for (TimeSlot timeSlot : project.getTimeSlots()) {
            if (timeSlot.getPersonId().equals(person.getId())) {
                totalMinutes += Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toMinutes();
            }
        }
        totalHours = (double) totalMinutes / 60.0;

        return totalHours;
    }

    /**
     * Saves a new project based on the provided ProjectCreateDTO.
     *
     * @param projectCreateDTO A ProjectCreateDTO containing project details.
     */
    public void saveProject(ProjectCreateDTO projectCreateDTO) {
        Project project = new Project(projectCreateDTO.getName(),
                                      projectCreateDTO.getDescription(),
                                      projectCreateDTO.getScheduledEffort());
        projectRepository.save(project);
    }

    /**
     * Deletes a project and removes its associations with persons.
     *
     * @param id The unique identifier of the project to be deleted.
     */
    @Transactional
    public void deleteProject(int id) {
        Project project = projectRepository.getReferenceById(id);

        for (Person person : project.getPersons()) {
           person.removeProject(project);
           personRepository.save(person);
        }
        project.getPersons().clear();

        projectRepository.delete(project);
    }
}
