package com.drone.app.repository;

import com.drone.app.constants.State;
import com.drone.app.entity.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends CrudRepository<Drone, String> {

    public List<Drone> findByState(State state);
    //public Drone getBySerialNumber(String serialNumber);
}
