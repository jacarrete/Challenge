package com.challenge;

import com.challenge.model.Horse;
import com.challenge.service.HorseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by jcarretero on 15/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
public class HorseRepositoryTest {

    @Autowired
    private HorseService horseService;

    private List<Horse> horseList;

    @Before
    public void setup() throws Exception {
        horseService.deleteAllHorses();

        horseService.saveHorse(new Horse("Horse1", "Jockey1", "Trainer1"));
        horseService.saveHorse(new Horse("Horse2", "Jockey2", "Trainer2"));
        horseService.saveHorse(new Horse("Horse3", "Jockey3", "Trainer3"));
    }

    @Test
    public void createAndGetHorse() {
        Horse horse = new Horse("Horse4", "Jockey4", "Trainer4");
        horseService.saveHorse(horse);
        Optional<Horse> horseOptional = horseService.findHorseById(horse.getId());
        Horse dbHorse = horseOptional.get();
        assertEquals( "Horse4", dbHorse.getName());
        assertEquals("Jockey4", dbHorse.getJockey());
        assertEquals("Trainer4", dbHorse.getTrainer());
        assertEquals("Horse{name='Horse4', jockey='Jockey4', trainer='Trainer4'}", dbHorse.toString());
    }

    @Test
    public void getAllHorses() {
        List<Horse> list = horseService.getAllHorses();
        assert list.size() == 3;
        assertEquals("Horse1", list.get(0).getName());
        assertEquals("Jockey1", list.get(0).getJockey());
        assertEquals("Trainer1", list.get(0).getTrainer());
        assertEquals("Horse2", list.get(1).getName());
        assertEquals("Jockey2", list.get(1).getJockey());
        assertEquals("Trainer2", list.get(1).getTrainer());
        assertEquals("Horse3", list.get(2).getName());
        assertEquals("Jockey3", list.get(2).getJockey());
        assertEquals("Trainer3", list.get(2).getTrainer());
    }

    @Test
    public void deleteHorse() {
        Horse horse = new Horse("Horse4", "Jockey4", "Trainer4");
        horseService.saveHorse(horse);
        List<Horse> list = horseService.getAllHorses();
        assert list.size() == 4;

        Optional<Horse> horseOptional = horseService.findHorseById(horse.getId());
        Horse dbHorse = horseOptional.get();
        horseService.deleteHorse(dbHorse.getId());
        list = horseService.getAllHorses();
        assert list.size() == 3;
    }

    @Test
    public void deleteAllHorses() {
        horseService.deleteAllHorses();
        List<Horse> list = horseService.getAllHorses();
        assert list.size() == 0;
    }
}
