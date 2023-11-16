package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for managing TimeSlot entities as JSON.
 */
@RestController
@RequestMapping("timeSlot")
public class TimeSlotJSONController {

    /**
     * Service for managing TimeSlot entities.
     */
    @Autowired
    private TimeSlotService timeSlotService;

    /**
     * Retrieves a list of TimeSlot entities in JSON format.
     *
     * @return A list of TimeSlotListDTO objects representing TimeSlot entities.
     */
    @GetMapping(value = "/.json", produces = "application/json")
    @ResponseBody
    public Page<TimeSlotListDTO> getAllTimeSlotList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return timeSlotService.getAllTimeSlotLists(pageable);
    }

    /**
     * Retrieves detailed information about a specific TimeSlot entity in JSON format.
     *
     * @param id The ID of the TimeSlot entity to retrieve.
     * @return A TimeSlotDetailsDTO object representing detailed information about the TimeSlot entity.
     */
    @GetMapping(value = "/{id}.json", produces = "application/json")
    @ResponseBody
    public TimeSlotDetailsDTO timeSlotDetail(@PathVariable("id") Integer id){
        return timeSlotService.getTimeSlotDetailsById(id);
    }

    /**
     * Creates a new TimeSlot entity based on the provided JSON data.
     *
     * @param timeSlotCreateDTO The JSON data representing the new TimeSlot entity.
     */
    @PostMapping(value = "/create.json", consumes = "application/json")
    public void createTimeSlot(@RequestBody TimeSlotCreateDTO timeSlotCreateDTO) {
        timeSlotService.saveTimeslot(timeSlotCreateDTO);
    }

    /**
     * Deletes a specific TimeSlot entity by its ID.
     *
     * @param id The ID of the TimeSlot entity to delete.
     */
    @DeleteMapping(value = "/delete/{id}.json")
    public void deleteTimeSlot(@PathVariable("id") Integer id) {
        timeSlotService.deleteTimeslot(id);
    }
}
