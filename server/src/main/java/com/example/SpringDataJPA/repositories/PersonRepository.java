package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Person entities.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Retrieves all persons with at least one timeSlot. Together with their timeSlots to avoid n + 1 problem.
     * @return all person with timeSlots
     */
    @Query("""
            SELECT DISTINCT p
            FROM Person p
            JOIN FETCH p.timeSlots t
            """)
    List<Person> retrieveAllActive();


    /**
     * Uses a native query to count the timeSlots for every person and combines the result with the first and last
     * name of the person.
     * @return all persons with first and last name and the count of timeSlots
     */
    @Query(value = """
            SELECT p.first_name AS firstName, p.last_name AS lastName, COUNT(t.t_id) AS slotCount
            FROM person p
            LEFT JOIN timeslot t ON p.p_id = t.p_id
            GROUP BY p.p_id
            """, nativeQuery = true)
    List<PersonWithHours> calculateHours();


    /**
     * Interface for combining the count of timeSlots a person has booked together with the first and last name of
     * the person.
     */
    interface PersonWithHours{
        String getFirstName();
        String getLastName();
        Integer getSlotCount();
    }
}
