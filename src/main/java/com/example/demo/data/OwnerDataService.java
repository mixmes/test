package com.example.demo.data;

import com.example.demo.model.Owner;
import com.example.demo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerDataService {
    @Autowired
    OwnerRepository repository;
    public void save(Owner owner){
        repository.save(owner);
    }
    public Optional<Owner> getById(long id){
        return repository.findById(id);
    }
    public List<Owner> findAll(){
        return repository.findAll();
    }
    public void update(Owner owner){
        repository.save(owner);
    }
    public void deleteById(long id){
        repository.deleteById(id);
    }
}
