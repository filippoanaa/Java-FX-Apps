package ubb.scs.map.faptebune.service;

import ubb.scs.map.faptebune.domain.City;
import ubb.scs.map.faptebune.domain.Need;
import ubb.scs.map.faptebune.domain.Person;
import ubb.scs.map.faptebune.repository.NeedRepository;
import ubb.scs.map.faptebune.repository.PersonRepository;
import ubb.scs.map.faptebune.utils.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Service extends Observable {
    NeedRepository needRepository;
    PersonRepository personRepository;

    public Service(NeedRepository needRepository, PersonRepository personRepository) {
        this.needRepository = needRepository;
        this.personRepository = personRepository;
    }

    public void addPerson(Person person) {
        personRepository.add(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Need> getAllNeeds() {
        return needRepository.findAll();
    }

    public void updateNeed(Need selectedNeed) {
        needRepository.update(selectedNeed);
    }

    public City getCityById(Long id) {
        return personRepository.findById(id).getCity();
    }

    public void addNeed(Need need) {
        needRepository.add(need);
        notifyObservers();

    }
}
