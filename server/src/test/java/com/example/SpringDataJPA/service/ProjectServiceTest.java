package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeSlotRepository timeslotRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @BeforeEach
    public void clearDataBase(){
        timeslotRepository.deleteAll();
        personRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void test_getAllProjects(){
        Project project1 = new Project("Project 1", "Project Description 1", 1);
        Project project2 = new Project("Project 2", "Project Description 2", 2);

        projectRepository.save(project1);
        projectRepository.save(project2);

        List<ProjectDTO> projectDTOList = projectService.getAllProjects();

        Assertions.assertEquals(2, projectDTOList.size());

        Assertions.assertEquals("Project 1", projectDTOList.get(0).getName());
        Assertions.assertEquals("Project Description 1", projectDTOList.get(0).getDescription());
        Assertions.assertEquals(1, projectDTOList.get(0).getScheduledEffort());

        Assertions.assertEquals("Project 2", projectDTOList.get(1).getName());
        Assertions.assertEquals("Project Description 2", projectDTOList.get(1).getDescription());
        Assertions.assertEquals(2, projectDTOList.get(1).getScheduledEffort());
    }

    @Test
    public void test_getProjectById(){
        Project project1 = new Project("Project 1", "Project Description 1", 1);
        Project project2 = new Project("Project 2", "Project Description 2", 2);

        projectRepository.save(project1);
        projectRepository.save(project2);

        ProjectDTO projectDTO = projectService.getProjectById(project1.getId());
        Assertions.assertEquals("Project 1", projectDTO.getName());
        Assertions.assertEquals("Project Description 1", projectDTO.getDescription());
        Assertions.assertEquals(1, projectDTO.getScheduledEffort());

        projectDTO = projectService.getProjectById(project2.getId());
        Assertions.assertEquals("Project 2", projectDTO.getName());
        Assertions.assertEquals("Project Description 2", projectDTO.getDescription());
        Assertions.assertEquals(2, projectDTO.getScheduledEffort());
    }

    @Test
    public void test_getAllProjectLists(){
        Person person = new Person("Max", "Meier");

        Project project1 = new Project("Project 1", "Project Description 1", 1);
        Project project2 = new Project("Project 2", "Project Description 2", 2);

        person.addProject(project2);

        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(12, 30)
                , "PS2"
                , person
                , project2);
        person.addTimeslot(timeslot);
        project2.addTimeslot(timeslot);

        projectRepository.save(project1);
        projectRepository.save(project2);
        personRepository.save(person);
        timeslotRepository.save(timeslot);

        List<ProjectListDTO> projectListDTOS = projectService.getAllProjectLists();

        Assertions.assertEquals(2, projectListDTOS.size());

        Assertions.assertEquals("Project 1", projectListDTOS.get(0).getName());
        Assertions.assertEquals("Project Description 1", projectListDTOS.get(0).getDescription());
        Assertions.assertEquals(1, projectListDTOS.get(0).getScheduledEffort());
        Assertions.assertEquals(0.0, projectListDTOS.get(0).getAssignedHours());
        Assertions.assertEquals(Boolean.FALSE, projectListDTOS.get(0).getOverAssigned());

        Assertions.assertEquals("Project 2", projectListDTOS.get(1).getName());
        Assertions.assertEquals("Project Description 2", projectListDTOS.get(1).getDescription());
        Assertions.assertEquals(2, projectListDTOS.get(1).getScheduledEffort());
        Assertions.assertEquals(3.0, projectListDTOS.get(1).getAssignedHours());
        Assertions.assertEquals(Boolean.TRUE, projectListDTOS.get(1).getOverAssigned());
    }

    @Test
    public void test_getProjectDetails(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        List<TimeSlot> timeSlots = new LinkedList<>();
        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 5,2)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "APF"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 5,30)
                , LocalTime.of(9, 40)
                , LocalTime.of(10, 55)
                , "PS3"
                , persons.get(2)
                , project);
        persons.get(2).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);
        personRepository.saveAll(persons);
        timeslotRepository.saveAll(timeSlots);

        ProjectDetailsDTO projectDetailsDTO = projectService.getProjectDetails(project.getId(), PageRequest.of(0, 2));

        Assertions.assertEquals("Project 1", projectDetailsDTO.getProjectName());
        Assertions.assertEquals("Project Description 1", projectDetailsDTO.getProjectDescription());
        Assertions.assertEquals(1, projectDetailsDTO.getScheduledEffort());

        Assertions.assertEquals(2, projectDetailsDTO.getProjectPersonDTOS().size());

        ProjectPersonDTO projectPersonDTO = projectDetailsDTO.getProjectPersonDTOS().get(0);
        Assertions.assertEquals(persons.get(0).getId(), projectPersonDTO.getPersonId());
        Assertions.assertEquals(persons.get(0).getFullName(), projectPersonDTO.getPersonFullName());
        Assertions.assertEquals(0.0, projectPersonDTO.getHours());
        Assertions.assertEquals("0", projectPersonDTO.getPercentage());
        Assertions.assertEquals(0, projectPersonDTO.getTimeSlotLists().size());

        projectPersonDTO = projectDetailsDTO.getProjectPersonDTOS().get(1);
        Assertions.assertEquals(persons.get(1).getId(), projectPersonDTO.getPersonId());
        Assertions.assertEquals(persons.get(1).getFullName(), projectPersonDTO.getPersonFullName());
        Assertions.assertEquals(3.75, projectPersonDTO.getHours());
        Assertions.assertEquals("75", projectPersonDTO.getPercentage());
        Assertions.assertEquals(3, projectPersonDTO.getTimeSlotLists().size());

        projectDetailsDTO = projectService.getProjectDetails(project.getId(), PageRequest.of(1, 2));
        Assertions.assertEquals(1, projectDetailsDTO.getProjectPersonDTOS().size());
        projectPersonDTO = projectDetailsDTO.getProjectPersonDTOS().get(0);
        Assertions.assertEquals(persons.get(2).getId(), projectPersonDTO.getPersonId());
        Assertions.assertEquals(persons.get(2).getFullName(), projectPersonDTO.getPersonFullName());
        Assertions.assertEquals(1.25, projectPersonDTO.getHours());
        Assertions.assertEquals("25", projectPersonDTO.getPercentage());
        Assertions.assertEquals(1, projectPersonDTO.getTimeSlotLists().size());
    }

    @Test
    public void test_saveProject(){
        ProjectCreateDTO projectCreateDTO = new ProjectCreateDTO();
        projectCreateDTO.setName("Project 1");
        projectCreateDTO.setDescription("Project Description 1");
        projectCreateDTO.setScheduledEffort(1);

        projectService.saveProject(projectCreateDTO);

        Project project = projectRepository.findAll().get(0);

        Assertions.assertEquals("Project 1", project.getName());
        Assertions.assertEquals("Project Description 1", project.getDescription());
        Assertions.assertEquals(1, project.getScheduledEffort());
    }

     @Test
     public void test_assignProjectToPerson_OnePersonOneProject(){
         Person person = new Person("Max", "Meier");
         Project project = new Project("Project 1", "Project Description 1", 1);
         personRepository.save(person);
         projectRepository.save(project);

         AssignProjectDTO assignProjectDTO = new AssignProjectDTO();

         assignProjectDTO.setPersonId(person.getId());
         assignProjectDTO.setProjectId(project.getId());

         projectService.assignProjectToPerson(assignProjectDTO);

         Person updatedPerson = personRepository.getReferenceById(person.getId());
         Project updatedProject = projectRepository.getReferenceById(project.getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), person.getId())));
     }

     @Test
     public void test_assignProjectToPerson_MultiplePeopleOneProject(){

         String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
         ArrayList<Person> persons = new ArrayList<>(names.length);
         Project project = new Project("Project 1", "Project Description 1", 1);
         for (String[] name : names) {
             Person person = new Person(name[0], name[1]);
             persons.add(person);
         }

         personRepository.saveAll(persons);
         projectRepository.save(project);

         AssignProjectDTO assignProjectDTO = new AssignProjectDTO();

         assignProjectDTO.setPersonId(persons.get(0).getId());
         assignProjectDTO.setProjectId(project.getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         assignProjectDTO.setPersonId(persons.get(1).getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         assignProjectDTO.setPersonId(persons.get(2).getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         Person updatedPerson = personRepository.getReferenceById(persons.get(0).getId());
         Project updatedProject = projectRepository.getReferenceById(project.getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), persons.get(0).getId())));

         updatedPerson = personRepository.getReferenceById(persons.get(1).getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), persons.get(1).getId())));

         updatedPerson = personRepository.getReferenceById(persons.get(2).getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), persons.get(2).getId())));
     }

     @Test
     public void test_assignProjectToPerson_OnePersonMultipleProjects(){
         Person person = new Person("Max", "Meier");
         Project project1 = new Project("Project 1", "Project Description 1", 1);
         Project project2 = new Project("Project 2", "Project Description 2", 1);
         Project project3 = new Project("Project 3", "Project Description 3", 1);

         personRepository.save(person);
         projectRepository.save(project1);
         projectRepository.save(project2);
         projectRepository.save(project3);

         AssignProjectDTO assignProjectDTO = new AssignProjectDTO();

         assignProjectDTO.setPersonId(person.getId());
         assignProjectDTO.setProjectId(project1.getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         assignProjectDTO.setProjectId(project2.getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         assignProjectDTO.setProjectId(project3.getId());
         projectService.assignProjectToPerson(assignProjectDTO);

         Person updatedPerson = personRepository.getReferenceById(person.getId());
         Project updatedProject = projectRepository.getReferenceById(project1.getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project1.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), person.getId())));

         updatedProject = projectRepository.getReferenceById(project2.getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project2.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), person.getId())));

         updatedProject = projectRepository.getReferenceById(project3.getId());

         Assertions.assertTrue(updatedPerson.getProjects().stream().anyMatch(
                 matchProject -> Objects.equals(matchProject.getId(), project3.getId())));

         Assertions.assertTrue(updatedProject.getPersons().stream().anyMatch(
                 matchPerson -> Objects.equals(matchPerson.getId(), person.getId())));
     }

     @Test
     public void test_isProjectAssignedToPerson_False(){
         Person person = new Person("Max", "Meier");
         Project project = new Project("Project 1", "Project Description 1", 1);
         personRepository.save(person);
         projectRepository.save(project);

         Assertions.assertFalse(projectService.isProjectAssignedToPerson(person.getId(), project.getId()));
     }


     @Test
     public void test_isProjectAssignedToPerson_True(){
         Person person = new Person("Max", "Meier");
         Project project = new Project("Project 1", "Project Description 1", 1);
         personRepository.save(person);
         projectRepository.save(project);

         AssignProjectDTO assignProjectDTO = new AssignProjectDTO();

         assignProjectDTO.setPersonId(person.getId());
         assignProjectDTO.setProjectId(project.getId());

         projectService.assignProjectToPerson(assignProjectDTO);

         Assertions.assertTrue(projectService.isProjectAssignedToPerson(person.getId(), project.getId()));
     }

    @Test
    public void test_deleteProject_OneProject(){
        Project project1 = new Project("Project 1", "Project Description 1", 1);

        projectRepository.save(project1);

        List<Project> projects = projectRepository.findAll();
        Assertions.assertEquals(1, projects.size());

        projectService.deleteProject(project1.getId());

        projects = projectRepository.findAll();
        Assertions.assertEquals(0, projects.size());
    }

    @Test
    public void test_deleteProject_OneProjectWithTimeSlot(){
        Person person = new Person("Max", "Meier");
        Project project = new Project("Project 1", "Project Description 1", 1);

        person.addProject(project);

        projectRepository.save(project);
        personRepository.save(person);

        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , person
                , project);
        timeslotRepository.save(timeslot);
        person.addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        projectRepository.save(project);
        personRepository.save(person);


        List<Project> projects = projectRepository.findAll();
        List<TimeSlot> timeSlots = timeslotRepository.findAll();

        Assertions.assertEquals(1, projects.size());
        Assertions.assertEquals(1, timeSlots.size());

        projectService.deleteProject(project.getId());

        projects = projectRepository.findAll();
        timeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(0, projects.size());
        Assertions.assertEquals(0, timeSlots.size());
    }

    @Test
    public void test_deleteProject_MultipleProject(){
        Project project1 = new Project("Project 1", "Project Description 1", 1);
        Project project2 = new Project("Project 2", "Project Description 2", 2);
        Project project3 = new Project("Project 3", "Project Description 3", 3);

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        List<Project> projects = projectRepository.findAll();
        Assertions.assertEquals(3, projects.size());

        projectService.deleteProject(project1.getId());

        projects = projectRepository.findAll();
        Assertions.assertEquals(2, projects.size());
    }

}
