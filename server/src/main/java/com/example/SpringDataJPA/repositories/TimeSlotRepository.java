package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing TimeSlot entities.
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}