package com.htm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htm.models.Inspection;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
}
