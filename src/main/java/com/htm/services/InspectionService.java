package com.htm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htm.models.Inspection;
import com.htm.repository.InspectionRepository;

@Service
public class InspectionService {
	@Autowired
    private InspectionRepository inspectionRepository;

	// Save a new inspection
    public Inspection saveInspection(Inspection inspection) {
        return inspectionRepository.save(inspection);
    }

    // Get all inspections
    public List<Inspection> getAllInspections() {
        return inspectionRepository.findAll();
    }
}
