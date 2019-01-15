package kz.epam.darling;

import java.sql.SQLException;
import java.util.List;

public abstract class DAO<K, T extends Entity> {
    protected static ConnectionPool connectionPool;


    public DAO() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public abstract boolean create(T entity);
    public abstract List<T> findAll();
    public abstract T findById(K id);
    public abstract T update(T entity);
    public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
}
