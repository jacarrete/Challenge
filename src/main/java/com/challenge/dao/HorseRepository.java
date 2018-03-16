package com.challenge.dao;

import com.challenge.model.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jcarretero on 15/03/2018.
 */
@Repository
public interface HorseRepository extends JpaRepository<Horse, Long> {
}
