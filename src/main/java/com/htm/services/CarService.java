package com.htm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htm.models.Car;
import com.htm.repository.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car saveCar(Car job) {
        return carRepository.save(job);
    }
}