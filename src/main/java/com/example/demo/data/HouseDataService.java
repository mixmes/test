package com.example.demo.data;

import com.example.demo.model.House;
import com.example.demo.model.Person;
import com.example.demo.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HouseDataService {
    @Autowired
    HouseRepository houseRepository;
    public Optional<House> getHouseById(long id){
        return houseRepository.findById(id);
    }
    public List<House> getAll(){
        return houseRepository.findAll();
    }
    public void update(House house){
        houseRepository.save(house);
    }
    public Optional<House> getHouseByAddress(String address){
        return houseRepository.findByAddress(address);
    }
    public void delete(long id){
         houseRepository.deleteById(id);
    }
    public void updateByTenants(long id,List<Person> tenants) throws Exception {
        Optional<House> house = houseRepository.findById(id);
        if(!house.isPresent()){
            throw new Exception("No such house");
        }
        else{
            house.get().setTenants(tenants);
            houseRepository.save(house.get());
        }
    }

}
