package com.drone.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.awt.image.BufferedImage;

@Entity
@Table(name = "MEDICATION")
@Data
public class Medication {

    @Id
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String code;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    @Column(nullable = false)
    private int weightInGrams;

    //private BufferedImage image;
}
