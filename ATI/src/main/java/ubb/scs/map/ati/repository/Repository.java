package ubb.scs.map.ati.repository;

import ubb.scs.map.ati.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    List<E> findAll();
    E findById(ID id);
}
