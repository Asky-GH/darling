package kz.epam.darling.model.dao;

import kz.epam.darling.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements DAO<Integer, Message> {
    private static final String FIND_BY_PARTICIPANTS_QUERY = "SELECT * FROM messages WHERE id IN (SELECT message_id " +
                                                                "FROM meassages_users WHERE meassages_users.user_id " +
                                                                "IN (?, ?)) ORDER BY created_at";
    private static final String INSERT_QUERY = "INSERT INTO messages(text, user_id) VALUES (?, ?)";
    private static final String RELATE_QUERY = "INSERT INTO meassages_users(message_id, user_id) VALUES (?, ?)";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM messages WHERE user_id = ?";


    public List<Message> findByParticipants(int user_id, int match_id) throws SQLException, ClassNotFoundException,
                                                                                InterruptedException {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PARTICIPANTS_QUERY)) {
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, match_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = retrieveMessage(resultSet);
                    messages.add(message);
                }
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return messages;
    }

    private Message retrieveMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getInt("id"));
        message.setText(resultSet.getString("text"));
        message.setCreated_at(resultSet.getTimestamp("created_at"));
        message.setUser_id(resultSet.getInt("user_id"));
        return message;
    }

    public void relate(Message message, int match_id) throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELATE_QUERY)) {
            preparedStatement.setInt(1, message.getId());
            preparedStatement.setInt(2, match_id);
            preparedStatement.executeUpdate();
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    public Message findByUserId(int user_id) throws SQLException, ClassNotFoundException, InterruptedException {
        Message message = null;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    message = retrieveMessage(resultSet);
                }
            }
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return message;
    }

    @Override
    public void create(Message message) throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, message.getText());
            preparedStatement.setInt(2, message.getUser_id());
            preparedStatement.executeUpdate();
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public List<Message> findAll() throws SQLException, ClassNotFoundException, InterruptedException {
        return null;
    }

    @Override
    public Message findById(Integer id) throws SQLException, ClassNotFoundException, InterruptedException {
        return null;
    }

    @Override
    public boolean update(Message entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
