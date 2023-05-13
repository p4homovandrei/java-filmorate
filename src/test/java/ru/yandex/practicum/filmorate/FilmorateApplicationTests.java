package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class FilmorateApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}
	@Test
	void simpleTest() throws Exception {
		/*mockMvc.perform(get("/films")).andExpect(status().isOk());*/
		LocalDate date = LocalDate.of(12,12,12);
		Film film = new  Film(1,"sd","asd", date,230L);
		mockMvc.perform( MockMvcRequestBuilders
						.post("/films")
						.content(asJsonString(film))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
