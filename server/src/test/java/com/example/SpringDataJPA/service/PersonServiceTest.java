package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonServiceTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeSlotRepository timeslotRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonService personService;

    @BeforeEach
    public void clearDataBase(){
        timeslotRepository.deleteAll();
        personRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void test_getAllPersons(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        projectRepository.save(project);
        personRepository.saveAll(persons);

        List<PersonDTO> personDTOS = personService.getAllPersons();

        Assertions.assertEquals(3, personDTOS.size());

        Assertions.assertEquals("Max", personDTOS.get(0).getFirstName());
        Assertions.assertEquals("Meier", personDTOS.get(0).getLastName());
        Assertions.assertEquals(1, personDTOS.get(0).getProjects().size());
        Assertions.assertEquals(0, personDTOS.get(0).getTimeslots().size());

        Assertions.assertEquals("Mimi", personDTOS.get(1).getFirstName());
        Assertions.assertEquals("Meier", personDTOS.get(1).getLastName());
        Assertions.assertEquals(1, personDTOS.get(1).getProjects().size());
        Assertions.assertEquals(0, personDTOS.get(1).getTimeslots().size());

        Assertions.assertEquals("Hugo", personDTOS.get(2).getFirstName());
        Assertions.assertEquals("Hugoson", personDTOS.get(2).getLastName());
        Assertions.assertEquals(1, personDTOS.get(2).getProjects().size());
        Assertions.assertEquals(0, personDTOS.get(2).getTimeslots().size());
    }

    @Test
    public void test_getPersonById(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        projectRepository.save(project);
        personRepository.saveAll(persons);

        Integer personId = persons.get(0).getId();
        PersonDTO personDTO = personService.getPersonById(personId);
        Assertions.assertEquals("Max", personDTO.getFirstName());
        Assertions.assertEquals("Meier", personDTO.getLastName());
        Assertions.assertEquals(1, personDTO.getProjects().size());
        Assertions.assertEquals(0, personDTO.getTimeslots().size());

        personId = persons.get(1).getId();
        personDTO = personService.getPersonById(personId);
        Assertions.assertEquals("Mimi", personDTO.getFirstName());
        Assertions.assertEquals("Meier", personDTO.getLastName());
        Assertions.assertEquals(1, personDTO.getProjects().size());
        Assertions.assertEquals(0, personDTO.getTimeslots().size());

        personId = persons.get(2).getId();
        personDTO = personService.getPersonById(personId);
        Assertions.assertEquals("Hugo", personDTO.getFirstName());
        Assertions.assertEquals("Hugoson", personDTO.getLastName());
        Assertions.assertEquals(1, personDTO.getProjects().size());
        Assertions.assertEquals(0, personDTO.getTimeslots().size());
    }

    @Test
    public void test_getPersonDetailsById(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }
        projectRepository.save(project);
        personRepository.saveAll(persons);

        Integer personId = persons.get(0).getId();

        PersonDetailsDTO personDetailsDTO = personService.getPersonDetailsById(personId);
        Assertions.assertEquals("Max", personDetailsDTO.getFirstName());
        Assertions.assertEquals("Meier", personDetailsDTO.getLastName());

        List<PersonProjectDTO> personProjectList = new ArrayList<>(personDetailsDTO.getPersonProjectDTOS());
        Assertions.assertEquals(1, personProjectList.size());
        Assertions.assertEquals(project.getId(), personProjectList.get(0).getId());
        Assertions.assertEquals(project.getName(), personProjectList.get(0).getName());
        Assertions.assertEquals(project.getDescription(), personProjectList.get(0).getDescription());
        Assertions.assertEquals(project.getScheduledEffort(), personProjectList.get(0).getScheduledEffort());
        Assertions.assertEquals(0.0, personProjectList.get(0).getHours());
        Assertions.assertEquals("NaN", personProjectList.get(0).getPercentage());

        Assertions.assertEquals(0, personDetailsDTO.getTimeslots().size());
    }

    @Test
    public void test_getAllPersonLists(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        projectRepository.save(project);
        personRepository.saveAll(persons);

        List<PersonListDTO> personListDTOS = personService.getAllPersonLists();

        Assertions.assertEquals(3, personListDTOS.size());

        Assertions.assertEquals("Max", personListDTOS.get(0).getFirstName());
        Assertions.assertEquals("Meier", personListDTOS.get(0).getLastName());

        Assertions.assertEquals("Mimi", personListDTOS.get(1).getFirstName());
        Assertions.assertEquals("Meier", personListDTOS.get(1).getLastName());

        Assertions.assertEquals("Hugo", personListDTOS.get(2).getFirstName());
        Assertions.assertEquals("Hugoson", personListDTOS.get(2).getLastName());
    }

    @Test
    public void test_getPersonListById(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        projectRepository.save(project);
        personRepository.saveAll(persons);

        Integer personId = persons.get(0).getId();
        PersonListDTO personListDTO = personService.getPersonListById(personId);
        Assertions.assertEquals("Max", personListDTO.getFirstName());
        Assertions.assertEquals("Meier", personListDTO.getLastName());

        personId = persons.get(1).getId();
        personListDTO = personService.getPersonListById(personId);
        Assertions.assertEquals("Mimi", personListDTO.getFirstName());
        Assertions.assertEquals("Meier", personListDTO.getLastName());

        personId = persons.get(2).getId();
        personListDTO = personService.getPersonListById(personId);
        Assertions.assertEquals("Hugo", personListDTO.getFirstName());
        Assertions.assertEquals("Hugoson", personListDTO.getLastName());
    }

    @Test
    public void test_savePerson(){
        PersonCreateDTO personCreateDTO = new PersonCreateDTO();
        personCreateDTO.setFirstName("Max");
        personCreateDTO.setLastName("Meier");

        personService.savePerson(personCreateDTO);

        List<Person> persons = personRepository.findAll();
        Assertions.assertEquals(1, persons.size());
        Assertions.assertEquals("Max", persons.get(0).getFirstName());
        Assertions.assertEquals("Meier", persons.get(0).getLastName());
    }

    @Test
    public void test_updatePerson(){
        Person person = new Person("Max", "Meier");
        personRepository.save(person);

        PersonUpdateDTO personUpdateDTO = new PersonUpdateDTO();
        personUpdateDTO.setId(personRepository.findAll().get(0).getId());
        personUpdateDTO.setFirstName("Hugo");
        personUpdateDTO.setLastName("Hugoson");

        personService.updatePerson(personUpdateDTO);

        List<Person> persons = personRepository.findAll();
        Assertions.assertEquals(1, persons.size());
        Assertions.assertEquals("Hugo", persons.get(0).getFirstName());
        Assertions.assertEquals("Hugoson", persons.get(0).getLastName());

    }

    @Test
    public void test_deletePerson_OnePerson(){
        Person person = new Person("Max", "Meier");
        personRepository.save(person);

        List<Person> people = personRepository.findAll();
        Assertions.assertEquals(1, people.size());

        personService.deletePerson(person.getId());

        people = personRepository.findAll();
        Assertions.assertEquals(0, people.size());
    }

    @Test
    public void test_deletePerson_OnePersonWithTimeSlot(){
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

        List<Person> people = personRepository.findAll();
        List<TimeSlot> timeSlots = timeslotRepository.findAll();

        Assertions.assertEquals(1, people.size());
        Assertions.assertEquals(1, timeSlots.size());

        personService.deletePerson(person.getId());

        people = personRepository.findAll();
        timeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(0, people.size());
        Assertions.assertEquals(0, timeSlots.size());
    }

    @Test
    public void test_deletePerson_MultiplePeople(){
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            persons.add(person);
        }
        personRepository.saveAll(persons);

        List<Person> people = personRepository.findAll();
        Assertions.assertEquals(3, people.size());

        personService.deletePerson(persons.get(0).getId());

        people = personRepository.findAll();
        Assertions.assertEquals(2, people.size());
    }


}
