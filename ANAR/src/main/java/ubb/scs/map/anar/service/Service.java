package ubb.scs.map.anar.service;

import ubb.scs.map.anar.domain.City;
import ubb.scs.map.anar.domain.River;
import ubb.scs.map.anar.repository.CityRepository;
import ubb.scs.map.anar.repository.RiverRepository;
import ubb.scs.map.anar.utils.Observable;

import java.util.List;

public class Service extends Observable {
    private RiverRepository riverRepository;
    private CityRepository cityRepository;

    public Service(RiverRepository riverRepository, CityRepository cityRepository) {
        this.riverRepository = riverRepository;
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCitites(){
        return cityRepository.findAll();
    }

    public List<River> getAllRivers(){
        System.out.println(riverRepository.findAll().size());
        return riverRepository.findAll();
    }

    public River findRiverById(Integer id){
        return riverRepository.findOne(id);
    }

    public void updateRiver(River river){
        riverRepository.update(river);
        notifyObservers();
    }
}
