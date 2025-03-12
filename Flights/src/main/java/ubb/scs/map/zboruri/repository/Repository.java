package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    List<E> findAll();
}


