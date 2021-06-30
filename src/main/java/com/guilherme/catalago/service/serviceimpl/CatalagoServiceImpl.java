package com.guilherme.catalago.service.serviceimpl;

import java.util.List;

import com.guilherme.catalago.model.Musica;
import com.guilherme.catalago.repository.CatalagoRepository;
import com.guilherme.catalago.service.CatalagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalagoServiceImpl implements CatalagoService{

    @Autowired
    CatalagoRepository catalagoRepository;
    
    @Override
    public List<Musica> findAll() {
        return catalagoRepository.findAll();
    }

    @Override
    public Musica findById(long id) {
        return catalagoRepository.findById(id).get();
    }

    @Override
    public Musica save(Musica musica) {
        return catalagoRepository.save(musica);
    }

    @Override
    public void deleteById(long id) {
        catalagoRepository.deleteById(id);        
    }
    
}
