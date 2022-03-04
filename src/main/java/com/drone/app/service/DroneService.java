package com.drone.app.service;

import com.drone.app.constants.State;
import com.drone.app.entity.Drone;
import com.drone.app.entity.Medication;
import com.drone.app.repository.DroneRepository;
import com.drone.app.repository.MedicationRepository;
import com.drone.app.utility.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.drone.app.constants.State.*;
import static com.drone.app.utility.Utils.generateUri;

@Service
public class DroneService {

    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;

    public DroneService(DroneRepository droneRepository,
                        MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    //service for registering drone
    public Drone register(Drone drone) {
        if (drone.getState().compareTo(IDLE) == 0) {
            return droneRepository.save(drone);
        }
        else {
            throw new RuntimeException("Drone has to be idle.");
        }

    }

    //service for getting all drones
    public List<Drone> getAll() {
        return (List<Drone>) droneRepository.findAll();
    }

    //check for idle drones
    public List<Drone> getIdle() {
        return droneRepository.findByState(IDLE);
    }

    //service for loading drone
    public Response<String> loadMedication(String droneSN, Medication medication) {

        //is drone present?
        Optional<Drone> droneOptional = droneRepository.findById(droneSN);
        if (droneOptional.isPresent()) {
            var drone = droneOptional.get();
            var bool = drone.getState() == IDLE && drone.getBatteryPercentage() >= 25 && drone.getWeightLimitInGrams() >= medication.getWeightInGrams();

            if (bool) {
                medicationRepository.save(medication);
                drone.setState(LOADING);
                drone.setMedication(medication);
                drone.setState(LOADED);
                droneRepository.save(drone);
                return new Response<>(null, generateUri());
            }
            else {
                throw new RuntimeException("Can't load Drone!");
            }
        }
        else {
            throw new RuntimeException("No such Drone.");
        }
    }

    //service for getting battery percentage
    public Response<Integer> getBatteryPercentage(String droneSN) {
        Optional<Drone> droneOptional = droneRepository.findById(droneSN);
        if (droneOptional.isPresent()) {
            var drone = droneOptional.get();
            return new Response<>(drone.getBatteryPercentage(), generateUri());
        }
        else {
            throw new RuntimeException("No such Drone!");
        }
    }

//    service for getting medication of a drone
//    public Medication getMedication(String droneSN) {
//
//        //is drone present
//        return droneRepository.getBySerialNumber(droneSN).getMedication();
////        Optional<Drone> droneOptional = droneRepository.findById(droneSN);
////        if (droneOptional.isPresent()) {
////            //var drone = droneOptional.get();
////            return droneOptional.get().getMedication();
////            //return new Response<>(drone.getMedication(), generateUri());
////        }
////        else {
////            throw new RuntimeException("No such Drone!");
////        }
//    }
}
