package com.drone.app.entity;

import com.drone.app.constants.State;
import com.drone.app.constants.Weight;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DRONE")
@Data
public class Drone {

    @Id
    @Column(name = "SERIALNUMBER", unique = true, nullable = false)
    @Size(max = 100) //right
    private String serialNumber; //shouldn't be over 100 chars long

    @Column(name = "DRONEMODEL", nullable = false)
    @Enumerated(EnumType.STRING)
    private Weight droneModel;

    @Column(name = "WEIGHTLIMITINGRAMS", nullable = false)
    @Max(500)
    private int weightLimitInGrams; //shouldn't be above 500g

    @Column(name = "BATTERYPERCENTAGE", nullable = false)
    @Min(0)
    @Max(100)
    private int batteryPercentage; //I define this for each drone

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state; //when battery percentage is set at <25%, this should not be LOADING, LOADED

    @OneToOne(fetch = FetchType.LAZY)
    private Medication medication;
}
