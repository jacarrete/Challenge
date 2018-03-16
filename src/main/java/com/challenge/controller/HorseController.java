package com.challenge.controller;

import com.challenge.dto.PartialHorse;
import com.challenge.model.Horse;
import com.challenge.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by jcarretero on 15/03/2018.
 */
@RestController
public class HorseController {

    private static final Logger log = LoggerFactory.getLogger(HorseController.class);

    @Autowired
    private HorseService horseService;

    @GetMapping("/getHorses")
    public List<Horse> getAllHorses() {
        return horseService.getAllHorses();
    }

    @GetMapping("/getHorse/{id}")
    public ResponseEntity<Object> getHorse(@PathVariable long id) {
        Optional<Horse> horse = horseService.findHorseById(id);

        if (!horse.isPresent()) {
            log.info("getHorse with id {} not found.", id);
            return new ResponseEntity<>("Horse with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(horse.get(), HttpStatus.OK);
    }

    @PostMapping("/createHorse")
    public ResponseEntity<Object> createHorse(@RequestBody Horse horse) {
        Horse savedHorse = horseService.saveHorse(horse);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedHorse.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/updateHorse/{id}")
    public ResponseEntity<Object> updateHorse(@RequestBody Horse horse, @PathVariable long id) {
        Optional<Horse> horseOptional = horseService.findHorseById(id);

        if (!horseOptional.isPresent()) {
            log.info("updateHorse with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        horse.setId(id);
        horseService.saveHorse(horse);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/patchHorse/{id}")
    public ResponseEntity<Object> patchHorse(@RequestBody PartialHorse partialHorse, @PathVariable long id) {
        Optional<Horse> horseOptional = horseService.findHorseById(id);

        if (!horseOptional.isPresent()) {
            log.info("updateHorse with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        Horse horse = horseOptional.get();
        horse.setId(id);
        horse.setName(partialHorse.getName());
        horseService.saveHorse(horse);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteHorse/{id}")
    public ResponseEntity<Object> deleteHorse(@PathVariable long id) {
        Optional<Horse> horse = horseService.findHorseById(id);

        if (!horse.isPresent()) {
            log.info("deleteHorse with id {} not found.", id);
            return new ResponseEntity<>("Horse with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        horseService.deleteHorse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

