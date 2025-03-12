package ubb.scs.map.restaurante.repository;

import java.util.List;

public interface Repository<E> {
    List<E> getAll();
}