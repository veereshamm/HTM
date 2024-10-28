package com.htm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htm.models.Inspection;
import com.htm.services.InspectionService;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;
 
    @PostMapping
    public ResponseEntity<Inspection> createInspection(@RequestBody Inspection inspection) {
        Inspection savedInspection = inspectionService.saveInspection(inspection);
        return ResponseEntity.ok(savedInspection);
    }

    // Endpoint to get all inspections
    @GetMapping
    public ResponseEntity<List<Inspection>> getAllInspections() {
        List<Inspection> inspections = inspectionService.getAllInspections();
        return ResponseEntity.ok(inspections);
    }
}
