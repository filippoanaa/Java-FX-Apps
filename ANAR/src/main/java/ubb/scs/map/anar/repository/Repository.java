package ubb.scs.map.anar.repository;

import ubb.scs.map.anar.domain.Entity;

import java.util.List;

public interface Repository<ID,E extends Entity<ID>> {
    List<E> findAll();
    E findOne(ID id);

}
