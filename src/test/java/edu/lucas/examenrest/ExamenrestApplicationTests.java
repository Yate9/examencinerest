package edu.lucas.examenrest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import edu.lucas.examenrest.Model.Repositories.IPeliculaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ExamenrestApplicationTests {

	@Test
	void consultaYEliminaPeliculaPorId(@Autowired MockMvc mvc, @Autowired IPeliculaRepository peliculas)
			throws Exception {
		mvc.perform(get("/api/peliculas/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.reparto.length()").value(3));

		mvc.perform(delete("/api/peliculas/1")).andExpect(status().is2xxSuccessful());
		assertFalse(peliculas.existsById(1L));
	}

}
