package com.guilherme.catalago.service;

import java.util.List;

import com.guilherme.catalago.model.Musica;

public interface CatalagoService {
    List<Musica> findAll();
    Musica findById(long id);
    Musica save (Musica musica);
    void deleteById(long id);
}
