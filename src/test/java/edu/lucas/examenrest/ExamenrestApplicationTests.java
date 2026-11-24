package edu.lucas.examenrest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
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

		mvc.perform(delete("/api/peliculas/1")).andExpect(status().isNoContent());
		assertFalse(peliculas.existsById(1L));
	}

	@Test
	void altaListadoYActualizacion(@Autowired MockMvc mvc) throws Exception {
		String ruta = "/api/peliculas";
		String datos = """
				{"titulo":"Estreno","director":"Ana","anio":1888,"duracion":1}
				""";
		String location = mvc.perform(post(ruta).contentType(MediaType.APPLICATION_JSON).content(datos))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.titulo").value("Estreno"))
				.andReturn().getResponse().getHeader("Location");

		mvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.titulo").value("Estreno"));
		mvc.perform(get(ruta)).andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.titulo == 'Estreno')]").isNotEmpty());
		mvc.perform(post(ruta).contentType(MediaType.APPLICATION_JSON).content(datos))
				.andExpect(status().isConflict());
		mvc.perform(put(location).contentType(MediaType.APPLICATION_JSON).content(datos.replace("Ana", "Eva")))
				.andExpect(status().isOk()).andExpect(jsonPath("$.director").value("Eva"));
		mvc.perform(put(location).contentType(MediaType.APPLICATION_JSON)
				.content(datos.replace("Estreno", "Ciudad de Cristal"))).andExpect(status().isConflict());
		mvc.perform(get(location)).andExpect(jsonPath("$.titulo").value("Estreno"))
				.andExpect(jsonPath("$.director").value("Eva"));
	}

	@Test
	void datosInvalidosEIdInexistente(@Autowired MockMvc mvc) throws Exception {
		String ruta = "/api/peliculas";
		String datos = """
				{"titulo":"Prueba","director":"Ana","anio":1888,"duracion":1}
				""";
		for (String invalido : new String[] { datos.replace("Prueba", " "), datos.replace("\"Ana\"", "null"),
				datos.replace("1888", "1887"), datos.replace(":1}", ":0}") }) {
			mvc.perform(post(ruta).contentType(MediaType.APPLICATION_JSON).content(invalido))
					.andExpect(status().isBadRequest());
			mvc.perform(put(ruta + "/2").contentType(MediaType.APPLICATION_JSON).content(invalido))
					.andExpect(status().isBadRequest());
		}
		String ausente = ruta + "/999999";
		mvc.perform(get(ausente)).andExpect(status().isNotFound());
		mvc.perform(put(ausente).contentType(MediaType.APPLICATION_JSON).content(datos))
				.andExpect(status().isNotFound());
		mvc.perform(delete(ausente)).andExpect(status().isNotFound());
	}

}
