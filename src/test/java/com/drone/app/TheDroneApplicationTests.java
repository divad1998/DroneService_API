package com.drone.app;

import com.drone.app.constants.Weight;
import com.drone.app.entity.Drone;
import com.drone.app.entity.Medication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static com.drone.app.constants.State.IDLE;
import static com.drone.app.constants.Weight.LightWeight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //tells spring boot to start app context
@AutoConfigureMockMvc
public class TheDroneApplicationTests {

	@Autowired
	private MockMvc mockmvc; //avoids cost of starter a real server

	private String basicEndpoint = "http://localhost:8080/api/drone";

	@Test
	void contextLoads() {
	}

	//test register Drone endpoint
	//assert ok status, uri in response, json response
	@Test
	public void testRegisterDrone() throws Exception {
		var drone = new Drone();
		drone.setSerialNumber("12345");
		drone.setDroneModel(LightWeight);
		drone.setWeightLimitInGrams(100);
		drone.setBatteryPercentage(50);
		drone.setState(IDLE);

		this.mockmvc.perform(post(basicEndpoint)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(drone))
						.header("Authorization", "Basic dXNlcjp1c2Vy")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(basicEndpoint + "/" + drone.getSerialNumber())));
	}

	@Test
	public void testGetIdleDrones() throws Exception {
		this.mockmvc
				.perform(get(basicEndpoint + "/idle"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(basicEndpoint + "/idle")));
	}

	@Test
	public void testloadDrone() throws Exception {
		var medication = new Medication();
		medication.setCode("hardDrug");
		medication.setName("Meth");
		medication.setWeightInGrams(100);

		this.mockmvc
				.perform(put(basicEndpoint + "/" + "{droneId}" + "/medication", "drone10")
						.content(new ObjectMapper().writeValueAsString(medication))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(basicEndpoint + "/" + "drone10" + "/medication")));
	}

	@Test
	public void testGetDroneBatteryPercentage() throws Exception {
		this.mockmvc
				.perform(get(basicEndpoint + "/" + "{droneId}", "drone10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(basicEndpoint + "/" + "drone10")));
	}


//	@Test
//	public void testGetDroneMedication() throws Exception {
//		this.mockmvc
//				.perform(get(basicEndpoint + "/" + "{droneId}" + "/medication", "drone10"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(content().string(containsString(basicEndpoint + "/" + "drone10" + "/medication")));
//	}

}
