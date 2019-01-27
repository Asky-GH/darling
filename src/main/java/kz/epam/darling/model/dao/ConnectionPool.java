package kz.epam.darling.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getName());
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;


    private ConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String databaseURL = resourceBundle.getString("db.url");
        String databaseUser = resourceBundle.getString("db.user");
        String databasePassword = resourceBundle.getString("db.password");
        String databaseDriver = resourceBundle.getString("db.driver");
        int databaseConnectionPoolSize = Integer.parseInt(resourceBundle.getString("db.connectionPoolSize"));
        connections = new ArrayBlockingQueue<>(databaseConnectionPoolSize);
        try {
            Class.forName(databaseDriver);
            for (int i = 0; i < databaseConnectionPoolSize; i++) {
                connections.offer(DriverManager.getConnection(databaseURL, databaseUser, databasePassword));
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
        }
    }

    public static void init() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
    }

    static ConnectionPool getInstance() {
        return instance;
    }

    Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    void releaseConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                if (!connections.offer(connection)) {
                    LOGGER.warn("Connection not added. Possible leakage of connections");
                }
            } else {
                LOGGER.warn("Trying to release closed connection. Possible leakage of connections");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    public static void dispose() {
        if (instance != null) {
            instance.clearConnectionQueue();
            instance = null;
        }
    }

    private void clearConnectionQueue() {
        Connection connection;
        while ((connection = connections.poll()) != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e);
            }
        }
    }
}
