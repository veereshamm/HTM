package com.htm.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String location;
    private String area;
    private String username;
    private String notes;
    private String checkedItems;
    private String imagePreview;
    private LocalDate carcurrentdate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCheckedItems() {
		return checkedItems;
	}
	public void setCheckedItems(String checkedItems) {
		this.checkedItems = checkedItems;
	}
	public String getImagePreview() {
		return imagePreview;
	}
	public void setImagePreview(String imagePreview) {
		this.imagePreview = imagePreview;
	}
	public LocalDate getCarcurrentdate() {
		return carcurrentdate;
	}
	public void setCarcurrentdate(String carcurrentdate) {
		this.carcurrentdate = LocalDate.now();
	}
}
