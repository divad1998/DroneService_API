package com.drone.app.controller;

import com.drone.app.entity.Drone;
import com.drone.app.entity.Medication;
import com.drone.app.repository.DroneRepository;
import com.drone.app.service.DroneService;
import com.drone.app.utility.Response;
import com.drone.app.utility.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.drone.app.utility.Utils.generateCreateUri;
import static com.drone.app.utility.Utils.generateUri;

@RestController
@RequestMapping("/api/drone")
public class DispatchController {

    DroneService droneService;

    public DispatchController(DroneService droneService) {
        this.droneService = droneService;
    }

    //register drone
    @PostMapping
    public ResponseEntity<?> registerDrone(@Valid @RequestBody Drone drone) {
        Drone registeredDrone = droneService.register(drone);
        return ResponseEntity.ok(new Response<String>(null, generateCreateUri(registeredDrone.getSerialNumber())));
    }

    //test data.sql
    @GetMapping
    public ResponseEntity<?> getDrones() {

        return ResponseEntity.ok(droneService.getAll());
    }

    //check for idle drones
    @GetMapping("/idle")
    public ResponseEntity<?> getIdleDrones() {
        return ResponseEntity.ok(new Response<List<Drone>>(droneService.getIdle(), generateUri()));
    }

    //load a drone with medication
    @PutMapping("/{droneId}/medication")
    public ResponseEntity<?> loadDrone(@PathVariable("droneId") String droneSN,
                                       @Valid @RequestBody Medication medication)
    {
        return ResponseEntity.ok(droneService.loadMedication(droneSN, medication));
    }

//    checking loaded medication for a given drone
//    @GetMapping("/{droneId}/medication")
//    public ResponseEntity<?> getDroneMedication(@PathVariable("droneId") String droneSN) {
//
//        return ResponseEntity.ok(droneService.getMedication(droneSN));
//
//    }

    //checking battery percentage of a drone
    @GetMapping("/{droneId}")
    public ResponseEntity<?> getDroneBatteryPercentage(@PathVariable("droneId") String droneSN) {
        return ResponseEntity.ok(droneService.getBatteryPercentage(droneSN));
    }


}
