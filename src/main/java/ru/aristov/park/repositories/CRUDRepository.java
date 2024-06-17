package ru.aristov.park.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T> {
    public List<T> getAll();

    public Optional<T> getById(Integer id);

    public T create(T entity);

    public T update(T entity);

    boolean deleteById(Integer id);
}
