package kz.epam.darling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;
    private static final String PROPERTIES_FILE = "database";
    private static final int POOL_SIZE = 8;


    private ConnectionPool(String url, String user, String password, int poolSize) throws SQLException {
        connections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connections.offer(DriverManager.getConnection(url, user, password));
        }
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE);
            String url = resourceBundle.getString("db.url");
            String user = resourceBundle.getString("db.user");
            String password = resourceBundle.getString("db.password");
            instance = new ConnectionPool(url, user, password, POOL_SIZE);
        }
        return instance;
    }

    public Connection takeConnection() throws InterruptedException {
        return connections.take();
    }

    public void releaseConnection(Connection connection) throws SQLException {
        if (!connection.isClosed()) {
            if (!connections.offer(connection)) {
                System.out.println("Connection not added. Possible leakage of connections");
            }
        } else {
            System.out.println("Trying to release closed connection. Possible leakage of connections");
        }
    }
}
