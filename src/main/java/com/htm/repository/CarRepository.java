package com.htm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htm.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
