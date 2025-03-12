package ubb.scs.map.adoptioncenter.repository;

import ubb.scs.map.adoptioncenter.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    List<E> findAll();
    E findOne(ID id);
    void update(E entity);
}


