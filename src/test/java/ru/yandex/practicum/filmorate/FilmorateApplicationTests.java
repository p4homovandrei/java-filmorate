package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.attribute.Genre;
import ru.yandex.practicum.filmorate.model.attribute.Mpa;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class FilmorateApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;



/*
    @Test
    void simpleTest() throws Exception {
        LocalDate date = LocalDate.of(2012, 12, 12);
        Film film = new Film(1, "sd", "asd", date, 230L,
                new LinkedList<Genre>(), new Mpa(1), 1, new LinkedList<Integer>());
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .content(asJsonString(film)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }*/

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content("{\n" +
                                "\"login\": \"dolore\",\n" +
                                "\"name\": \"Nick Name\",\n" +
                                "\"email\": \"mail@mail.ru\",\n" +
                                "\"birthday\": \"1946-08-20\"\n" +
                                "}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateUser() throws Exception {
        testCreateUser();
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content("{\n" +
                                "\"id\": \"1\",\n" +
                                "\"login\": \"doloreUpdate\",\n" +
                                "\"name\": \"est adipisicing\",\n" +
                                "\"email\": \"mails@yandexs.ru\",\n" +
                                "\"birthday\": \"1976-09-20\"\n" +
                                "}").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.name").isString());
    }

    @Test
    void testCreateFilm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .content("{\n" +
                                "\"name\": \"nisi eiusmod\",\n" +
                                "\"description\": \"adipisicing\",\n" +
                                "\"releaseDate\": \"1967-03-25\",\n" +
                                "\"duration\": 100,\n" +
                                "\"mpa\": { \"id\": 1}\n" +
                                "}").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testUpdateFilm() throws Exception {
        testCreateFilm();
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
                        .content("{\n" +
                                "\"id\": \"1\",\n" +
                                "\"name\": \"Film Updated\",\n" +
                                "\"description\": \"New film update decription\",\n" +
                                "\"releaseDate\": \"1989-04-17\",\n" +
                                "\"duration\": 190,\n" +
                                "\"mpa\": { \"id\": 2}\n" +
                                "}").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testGetFilm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/films/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString());
    }

   /* public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
