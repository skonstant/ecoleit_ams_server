package com.ecoleit.ams.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ServerApplicationTests {

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc =
				MockMvcBuilders.standaloneSetup(new PetController())
						.build();
	}

	@Test
	public void getPet() throws Exception {
		final ResultActions result = mockMvc.perform(get("/pet/1"));
		result.andExpect(status().isOk())
				.andExpect(jsonPath("name", is("one")));
	}

	@Test
	public void getPetWithError() throws Exception {
		final ResultActions result = mockMvc.perform(get("/pet/10"));
		result.andExpect(status().isNotFound());
	}

	@Test
	public void getPets() throws Exception {
		final ResultActions result = mockMvc.perform(get("/pets"));
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("one")));
	}

	@Test
	public void createManyPets() throws Exception {
		final ResultActions result = mockMvc.perform(post("/pet/1?petId=1&name=mypet&status=alive"));
		result.andExpect(status().isCreated());

		final ResultActions result2 = mockMvc.perform(get("/pet/1"));
		result2.andExpect(status().isOk())
				.andExpect(jsonPath("name", is("mypet")));

	}


	@Test
	public void createPetWithError() throws Exception {
		final ResultActions result = mockMvc.perform(post("/pet/1?petId=1&name=oneeeeeeeeeeeeee&status=alive"));
		result.andExpect(status().isMethodNotAllowed());

		final ResultActions result2 = mockMvc.perform(get("/pet/1"));
		result2.andExpect(status().isNotFound());

	}

	@Test
	public void deletePet() throws Exception {
		final ResultActions result = mockMvc.perform(post("/pet/1?petId=1&name=mypet&status=alive"));
		result.andExpect(status().isCreated());

		final ResultActions result2 = mockMvc.perform(get("/pet/1"));
		result2.andExpect(status().isOk())
				.andExpect(jsonPath("name", is("mypet")));
		// 1. delete
		final ResultActions result3 = mockMvc.perform(delete("/pet?petId=1"));
		result3.andExpect(status().isOk());

		// 2. check not found
		final ResultActions result4 = mockMvc.perform(get("/pet/1"));
		result4.andExpect(status().isNotFound());



	}

}
