package com.guilherme.catalago.repository;

import com.guilherme.catalago.model.Musica;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalagoRepository  extends JpaRepository<Musica, Long> {
    
}
