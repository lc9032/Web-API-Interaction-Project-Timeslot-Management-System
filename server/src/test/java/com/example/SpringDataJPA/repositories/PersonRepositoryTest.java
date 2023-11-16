package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeSlotRepository timeslotRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    public void clearDataBase(){
        timeslotRepository.deleteAll();
        personRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void test_retrieveAllAndCalculateHoursThreePersonsZeroThreeOneTimeslot() {
        // Create test data
        // Create persons
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        //Create timeslots
        List<TimeSlot> timeSlots = new LinkedList<>();
        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 5,2)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "APF"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(2)
                , project);
        persons.get(2).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);

        personRepository.saveAll(persons);

        System.out.println(persons);

        timeslotRepository.saveAll(timeSlots);

        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();

        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();

        Assertions.assertEquals(2, resultRetrieveAll.size());

        Assertions.assertEquals("Mimi", resultRetrieveAll.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultRetrieveAll.get(0).getLastName());
        Assertions.assertEquals(3, resultRetrieveAll.get(0).getTimeSlots().size());

        Assertions.assertEquals("Hugo", resultRetrieveAll.get(1).getFirstName());
        Assertions.assertEquals("Hugoson", resultRetrieveAll.get(1).getLastName());
        Assertions.assertEquals(1, resultRetrieveAll.get(1).getTimeSlots().size());

        Assertions.assertEquals(3, resultCalculateHours.size());

        Assertions.assertEquals("Max", resultCalculateHours.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultCalculateHours.get(0).getLastName());
        Assertions.assertEquals(0, resultCalculateHours.get(0).getSlotCount());

        Assertions.assertEquals("Mimi", resultCalculateHours.get(1).getFirstName());
        Assertions.assertEquals("Meier", resultCalculateHours.get(1).getLastName());
        Assertions.assertEquals(3, resultCalculateHours.get(1).getSlotCount());

        Assertions.assertEquals("Hugo", resultCalculateHours.get(2).getFirstName());
        Assertions.assertEquals("Hugoson", resultCalculateHours.get(2).getLastName());
        Assertions.assertEquals(1, resultCalculateHours.get(2).getSlotCount());
    }

    @Test
    public void test_retrieveAll_ZeroEntries(){
        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
        Assertions.assertEquals(0, resultRetrieveAll.size());
    }

    @Test
    public void test_retrieveAll_OneEntry(){
        Person person = new Person("Max", "Meier");
        Project project = new Project("Project 1", "Project Description 1", 1);

        TimeSlot timeSlot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , person
                , project);

        person.addProject(project);
        person.addTimeslot(timeSlot);

        projectRepository.save(project);
        personRepository.save(person);
        timeslotRepository.save(timeSlot);

        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
        Assertions.assertEquals(1, resultRetrieveAll.size());

        Assertions.assertEquals("Max", resultRetrieveAll.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultRetrieveAll.get(0).getLastName());
        Assertions.assertEquals(1, resultRetrieveAll.get(0).getTimeSlots().size());
    }

    @Test
    public void test_retrieveAll_MultipleEntries(){
        // Create persons
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }



        //Create timeslots
        List<TimeSlot> timeSlots = new LinkedList<>();
        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "PS2"
                , persons.get(1), project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 5,2)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "APF"
                , persons.get(0)
                , project);
        persons.get(0).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);
        personRepository.saveAll(persons);
        timeslotRepository.saveAll(timeSlots);

        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();

        Assertions.assertEquals(2, resultRetrieveAll.size());

        Assertions.assertEquals("Max", resultRetrieveAll.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultRetrieveAll.get(0).getLastName());
        Assertions.assertEquals(1, resultRetrieveAll.get(0).getTimeSlots().size());

        Assertions.assertEquals("Mimi", resultRetrieveAll.get(1).getFirstName());
        Assertions.assertEquals("Meier", resultRetrieveAll.get(1).getLastName());
        Assertions.assertEquals(2, resultRetrieveAll.get(1).getTimeSlots().size());
    }

    @Test
    public void test_calculateHours_ZeroEntries(){
        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();
        Assertions.assertEquals(0, resultCalculateHours.size());
    }

    @Test
    public void test_calculateHours_OneEntry(){
        Person person = new Person("Max", "Meier");
        Project project = new Project("Project 1", "Project Description 1", 1);

        TimeSlot timeSlot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , person
                , project);

        person.addProject(project);
        person.addTimeslot(timeSlot);

        projectRepository.save(project);
        personRepository.save(person);
        timeslotRepository.save(timeSlot);

        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();
        Assertions.assertEquals(1, resultCalculateHours.size());

        Assertions.assertEquals("Max", resultCalculateHours.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultCalculateHours.get(0).getLastName());
        Assertions.assertEquals(1, resultCalculateHours.get(0).getSlotCount());
    }

    @Test
    public void test_calculateHours_MultipleEntries(){
        // Create persons
        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
        ArrayList<Person> persons = new ArrayList<>(names.length);
        Project project = new Project("Project 1", "Project Description 1", 1);
        for (String[] name : names) {
            Person person = new Person(name[0], name[1]);
            person.addProject(project);
            persons.add(person);
        }

        //Create timeslots
        List<TimeSlot> timeSlots = new LinkedList<>();
        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "PS2"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 5,2)
                , LocalTime.of(11, 0)
                , LocalTime.of(12, 15)
                , "APF"
                , persons.get(1)
                , project);
        persons.get(1).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(2)
                , project);
        persons.get(2).addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);
        personRepository.saveAll(persons);
        timeslotRepository.saveAll(timeSlots);

        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();
        Assertions.assertEquals(3, resultCalculateHours.size());

        Assertions.assertEquals("Max", resultCalculateHours.get(0).getFirstName());
        Assertions.assertEquals("Meier", resultCalculateHours.get(0).getLastName());
        Assertions.assertEquals(0, resultCalculateHours.get(0).getSlotCount());

        Assertions.assertEquals("Mimi", resultCalculateHours.get(1).getFirstName());
        Assertions.assertEquals("Meier", resultCalculateHours.get(1).getLastName());
        Assertions.assertEquals(3, resultCalculateHours.get(1).getSlotCount());

        Assertions.assertEquals("Hugo", resultCalculateHours.get(2).getFirstName());
        Assertions.assertEquals("Hugoson", resultCalculateHours.get(2).getLastName());
        Assertions.assertEquals(1, resultCalculateHours.get(2).getSlotCount());
    }
}
