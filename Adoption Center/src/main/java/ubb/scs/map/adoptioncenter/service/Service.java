package ubb.scs.map.adoptioncenter.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ubb.scs.map.adoptioncenter.controller.AdoptionCenterController;
import ubb.scs.map.adoptioncenter.domain.AdoptionCenter;
import ubb.scs.map.adoptioncenter.domain.Animal;
import ubb.scs.map.adoptioncenter.repository.AdoptionCenterRepository;
import ubb.scs.map.adoptioncenter.repository.AnimalRepository;
import ubb.scs.map.adoptioncenter.utils.Observable;
import ubb.scs.map.adoptioncenter.utils.Observer;

import java.util.List;

public class Service extends Observable {
    private AnimalRepository animalRepository;
    private AdoptionCenterRepository adoptionCenterRepository;
    public Service(AnimalRepository animalRepository, AdoptionCenterRepository adoptionCenterRepository) {
        this.animalRepository = animalRepository;
        this.adoptionCenterRepository = adoptionCenterRepository;
    }

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }
    public List<AdoptionCenter> getAdoptionCenters() {
        return adoptionCenterRepository.findAll();
    }
    public Animal getAnimalById(int id) {
        return animalRepository.findOne(id);
    }
    public AdoptionCenter getAdoptionCenterById(int id) {
        return adoptionCenterRepository.findOne(id);
    }
    public void updateAnimal(Animal animal) {
        animalRepository.update(animal);
        notifyObservers();
    }

    public List<Animal> getAnimalsByCenterId(Integer id) {
        return animalRepository.findByCenterId(id);
    }

    public List<Animal> filterAnimals(Integer centerId, Animal.AnimalType animalType) {
        return animalRepository.findByCenterIdAndType(centerId, animalType);
    }


    public List<AdoptionCenter> getCentersInSameLocation(String location) {
        return adoptionCenterRepository.findByLocation(location);
    }
}
