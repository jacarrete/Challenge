package com.challenge.service;

import com.challenge.dao.HorseRepository;
import com.challenge.model.Horse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by jcarretero on 15/03/2018.
 */
@Service("horseService")
public class HorseService {

    @Autowired
    private HorseRepository horseRepository;

    @Transactional
    public List<Horse> getAllHorses() {
        return horseRepository.findAll();
    }

    @Transactional
    public Optional<Horse> findHorseById(long id) {
        return horseRepository.findById(id);
    }

    @Transactional
    public Horse saveHorse(Horse horse) {
        return horseRepository.save(horse);
    }

    @Transactional
    public void deleteHorse(long id) {
        horseRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllHorses() {
        horseRepository.deleteAll();
    }

}