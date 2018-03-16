package com.challenge;

import com.challenge.dto.PartialHorse;
import com.challenge.model.Horse;
import com.challenge.service.HorseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by jcarretero on 15/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
public class HorseControllerTest {

    @Autowired
    private HorseService horseService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<Horse> horseList = new ArrayList<>();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        horseService.deleteAllHorses();

        horseList.add(horseService.saveHorse(new Horse("Horse1", "Jockey1", "Trainer1")));
        horseList.add(horseService.saveHorse(new Horse("Horse2", "Jockey2", "Trainer2")));
    }

    @Test
    public void horseNotFound() throws Exception {
        mockMvc.perform(post("/getHorse")
                .content(this.json(new Horse(null, null, null)))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSingleHorse() throws Exception {
        mockMvc.perform(get("/getHorse/" + this.horseList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.horseList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is("Horse1")))
                .andExpect(jsonPath("$.jockey", is("Jockey1")))
                .andExpect(jsonPath("$.trainer", is("Trainer1")));
    }

    @Test
    public void getAllHorses() throws Exception {
        mockMvc.perform(get("/getHorses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.horseList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is("Horse1")))
                .andExpect(jsonPath("$[0].jockey", is("Jockey1")))
                .andExpect(jsonPath("$[0].trainer", is("Trainer1")))
                .andExpect(jsonPath("$[1].id", is(this.horseList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is("Horse2")))
                .andExpect(jsonPath("$[1].jockey", is("Jockey2")))
                .andExpect(jsonPath("$[1].trainer", is("Trainer2")));
    }

    @Test
    public void createHorse() throws Exception {
        String horseJson = json(new Horse("Horse3", "Jockey3", "Trainer3"));

        mockMvc.perform(post("/createHorse")
                .contentType(contentType)
                .content(horseJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateHorse() throws Exception {
        Horse horse = new Horse("Horse3", "Jockey3", "Trainer3");
        String horseJson = json(new Horse( "Horse4", "Jockey4", "Trainer4"));
        horseService.saveHorse(horse);

        mockMvc.perform(put("/updateHorse/" + horse.getId())
                .contentType(contentType)
                .content(horseJson))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/getHorse/" + horse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(horse.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Horse4")))
                .andExpect(jsonPath("$.jockey", is("Jockey4")))
                .andExpect(jsonPath("$.trainer", is("Trainer4")));
    }

    @Test
    public void updateHorseNotFound() throws Exception {
        String horseJson = json(new Horse( "Horse4", "Jockey4", "Trainer4"));

        mockMvc.perform(put("/updateHorse/" + 6)
                .contentType(contentType)
                .content(horseJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void patchHorse() throws Exception {
        Horse horse = new Horse("Horse3", "Jockey3", "Trainer3");
        String partialHorseJson = json(new PartialHorse( "Horse4"));
        horseService.saveHorse(horse);

        mockMvc.perform(patch("/patchHorse/" + horse.getId())
                .contentType(contentType)
                .content(partialHorseJson))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/getHorse/" + horse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(horse.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Horse4")))
                .andExpect(jsonPath("$.jockey", is("Jockey3")))
                .andExpect(jsonPath("$.trainer", is("Trainer3")));
    }

    @Test
    public void patchHorseNotFound() throws Exception {
        String horseJson = json(new PartialHorse( "Horse4"));

        mockMvc.perform(patch("/patchHorse/" + 6)
                .contentType(contentType)
                .content(horseJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteHorse() throws Exception {
        Horse horse = new Horse("Horse3", "Jockey3", "Trainer3");
        horseService.saveHorse(horse);

        mockMvc.perform(delete("/deleteHorse/" + horse.getId())
                .contentType(contentType)
                .accept(contentType))
                .andExpect(status().isOk());

        mockMvc.perform(get("/getHorse/" + horse.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteHorseNotFound() throws Exception {
        mockMvc.perform(delete("/deleteHorse/" + 6)
                .contentType(contentType)
                .accept(contentType))
                .andExpect(status().isNotFound());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
