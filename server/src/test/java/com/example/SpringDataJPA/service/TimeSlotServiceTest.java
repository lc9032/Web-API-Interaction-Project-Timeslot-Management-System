package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.TimeSlotCreateDTO;
import com.example.SpringDataJPA.dto.TimeSlotDetailsDTO;
import com.example.SpringDataJPA.dto.TimeSlotListDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TimeSlotServiceTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeSlotRepository timeslotRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @BeforeEach
    public void clearDataBase(){
        timeslotRepository.deleteAll();
        personRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void test_getAllTimeSlotLists(){
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

        Page<TimeSlotListDTO> timeSlotListDTOSPage  = timeSlotService.getAllTimeSlotLists(PageRequest.of(0, 3));
        List<TimeSlotListDTO> timeSlotListDTOS = timeSlotListDTOSPage.getContent();

        Assertions.assertEquals(3, timeSlotListDTOS.size());

        Assertions.assertEquals(LocalDate.of(2023, 4,30), timeSlotListDTOS.get(0).getDate());
        Assertions.assertEquals(LocalTime.of(9, 30), timeSlotListDTOS.get(0).getStartTime());
        Assertions.assertEquals(LocalTime.of(10, 45), timeSlotListDTOS.get(0).getEndTime());
        Assertions.assertEquals("PS2", timeSlotListDTOS.get(0).getDescription());

        Assertions.assertEquals(LocalDate.of(2023, 4,30), timeSlotListDTOS.get(1).getDate());
        Assertions.assertEquals(LocalTime.of(11, 0), timeSlotListDTOS.get(1).getStartTime());
        Assertions.assertEquals(LocalTime.of(12, 15), timeSlotListDTOS.get(1).getEndTime());
        Assertions.assertEquals("PS2", timeSlotListDTOS.get(1).getDescription());

        Assertions.assertEquals(LocalDate.of(2023, 5,2), timeSlotListDTOS.get(2).getDate());
        Assertions.assertEquals(LocalTime.of(11, 0), timeSlotListDTOS.get(2).getStartTime());
        Assertions.assertEquals(LocalTime.of(12, 15), timeSlotListDTOS.get(2).getEndTime());
        Assertions.assertEquals("APF", timeSlotListDTOS.get(2).getDescription());

        timeSlotListDTOSPage  = timeSlotService.getAllTimeSlotLists(PageRequest.of(1, 3));
        timeSlotListDTOS = timeSlotListDTOSPage.getContent();

        Assertions.assertEquals(1, timeSlotListDTOS.size());

        Assertions.assertEquals(LocalDate.of(2023, 5,30), timeSlotListDTOS.get(0).getDate());
        Assertions.assertEquals(LocalTime.of(9, 40), timeSlotListDTOS.get(0).getStartTime());
        Assertions.assertEquals(LocalTime.of(10, 55), timeSlotListDTOS.get(0).getEndTime());
        Assertions.assertEquals("PS3", timeSlotListDTOS.get(0).getDescription());
    }

    @Test
    public void test_getTimeSlotDetailsById(){
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

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(2)
                , project);
        persons.get(2).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);
        personRepository.saveAll(persons);
        timeslotRepository.saveAll(timeSlots);

        TimeSlotDetailsDTO timeSlotListDTO = timeSlotService.getTimeSlotDetailsById(timeslot.getId());

        Assertions.assertEquals(LocalDate.of(2023, 4,30), timeSlotListDTO.getDate());
        Assertions.assertEquals(LocalTime.of(9, 30), timeSlotListDTO.getStartTime());
        Assertions.assertEquals(LocalTime.of(10, 45), timeSlotListDTO.getEndTime());
        Assertions.assertEquals("PS2", timeSlotListDTO.getDescription());
    }

    @Test
    public void test_saveTimeslot(){
        Person person = new Person("Max", "Meier");
        Project project = new Project("Project 1", "Project Description 1", 1);

        person.addProject(project);

        projectRepository.save(project);
        personRepository.save(person);

        TimeSlotCreateDTO timeSlotCreateDTO = new TimeSlotCreateDTO();
        timeSlotCreateDTO.setPersonId(person.getId());
        timeSlotCreateDTO.setProjectId(project.getId());
        timeSlotCreateDTO.setDate(LocalDate.of(2023, 4,30));
        timeSlotCreateDTO.setStartTime(LocalTime.of(9, 30));
        timeSlotCreateDTO.setEndTime(LocalTime.of(10, 45));
        timeSlotCreateDTO.setDescription("PS2");

        timeSlotService.saveTimeslot(timeSlotCreateDTO);

        TimeSlot timeSlot = timeslotRepository.findAll().get(0);

        Assertions.assertEquals(LocalDate.of(2023, 4,30), timeSlot.getDate());
        Assertions.assertEquals(LocalTime.of(9, 30), timeSlot.getStartTime());
        Assertions.assertEquals(LocalTime.of(10, 45), timeSlot.getEndTime());
        Assertions.assertEquals("PS2", timeSlot.getDescription());
    }

    @Test
    public void test_deleteTimeslot_OneTimeSlot(){
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

        List<TimeSlot> allTimeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(1, allTimeSlots.size());

        timeSlotService.deleteTimeslot(timeslot.getId());

        allTimeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(0, allTimeSlots.size());
    }


    @Test
    public void test_deleteTimeslot_MultipleTimeSlot(){
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

        timeslot = new TimeSlot(LocalDate.of(2023, 4,30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , persons.get(2)
                , project);
        persons.get(2).addTimeslot(timeslot);
        project.addTimeslot(timeslot);
        timeSlots.add(timeslot);

        projectRepository.save(project);
        personRepository.saveAll(persons);
        timeslotRepository.saveAll(timeSlots);

        List<TimeSlot> allTimeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(4, allTimeSlots.size());

        timeSlotService.deleteTimeslot(timeslot.getId());

        allTimeSlots = timeslotRepository.findAll();
        Assertions.assertEquals(3, allTimeSlots.size());
    }
}
