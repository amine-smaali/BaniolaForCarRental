package com.projet.inscription_connexion.buisness.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projet.inscription_connexion.doa.Entity.Car;
import com.projet.inscription_connexion.doa.Repository.CarRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Car findCar(Long id){
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // public List<Car> getAvailableCars() {
    //     return carRepository.findByAvailableTrue();
    // }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car existingCar = carRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found"));
        
        existingCar.setMarque(updatedCar.getMarque());
        existingCar.setModel(updatedCar.getModel());
        existingCar.setAnnee_fab(updatedCar.getAnnee_fab());
        existingCar.setPrix_par_jour(updatedCar.getPrix_par_jour());
        existingCar.setEtat(updatedCar.getEtat());
        
        return carRepository.save(existingCar);
    }

    public Car updateCarAvailabilityTrue(Long id) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found"));
        car.setEtat("available");
        return carRepository.save(car);
    }

    public Car updateCarAvailabilityFalse(Long id) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found"));
        car.setEtat("loué");
        return carRepository.save(car);
    }
}