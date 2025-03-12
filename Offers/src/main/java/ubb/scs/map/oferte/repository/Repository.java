package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    List<E> getAll();
}
