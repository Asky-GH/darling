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
    private static final String PROPERTIES_FILE = "database";
    private static final int POOL_SIZE = 8;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getName());
    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(POOL_SIZE);


    private ConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String driver = resourceBundle.getString("db.driver");
        try {
            Class.forName(driver);
            for (int i = 0; i < POOL_SIZE; i++) {
                connections.offer(DriverManager.getConnection(url, user, password));
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
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
}