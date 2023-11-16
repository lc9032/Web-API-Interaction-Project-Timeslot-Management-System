package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Person entities.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
