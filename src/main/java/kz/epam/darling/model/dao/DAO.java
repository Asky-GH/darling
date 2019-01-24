package kz.epam.darling.model.dao;

import kz.epam.darling.model.Entity;

import java.sql.SQLException;
import java.util.List;

public interface DAO<K, T extends Entity> {
    void create(T entity) throws SQLException, ClassNotFoundException, InterruptedException;
    List<T> findAll() throws SQLException, ClassNotFoundException, InterruptedException;
    T findById(K id) throws SQLException, ClassNotFoundException, InterruptedException;
    boolean update(T entity);
    boolean delete(K id);
}
