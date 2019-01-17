package kz.epam.darling.model.dao;

import kz.epam.darling.model.Entity;

import java.util.List;

public interface DAO<K, T extends Entity> {
    boolean create(T entity);
    List<T> findAll();
    T findById(K id);
    boolean update(T entity);
    boolean delete(K id);
}
