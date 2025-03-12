package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    List<E> findAll();
    E findById(ID id);
    void add(E entity);


}
