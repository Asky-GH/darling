package kz.epam.darling.dao;

import kz.epam.darling.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private static final Logger LOGGER = LogManager.getLogger(MessageDAO.class.getName());


    public static List<Message> findByParticipants(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM messages WHERE sender_id IN (?, ?) AND " +
                                                            "receiver_id IN (?, ?)")) {
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setInt(3, senderId);
            ps.setInt(4, receiverId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = retrieveMessage(rs);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return messages;
    }

    public static List<Message> findNew(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM messages WHERE sender_id = ? AND " +
                                                            "receiver_id = ? AND status_id = 1")) {
            ps.setInt(1, receiverId);
            ps.setInt(2, senderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = retrieveMessage(rs);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return messages;
    }

    public static List<Message> findByChat(int receiverId) {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM (SELECT sender_id, receiver_id, " +
                                                            "MAX(created_at) last FROM messages GROUP BY 1, 2 " +
                                                            "HAVING receiver_id = ? OR sender_id = ?) as latest JOIN " +
                                                            "messages ON created_at = latest.last AND " +
                                                            "(messages.sender_id = latest.sender_id OR messages.receiver_id = " +
                                                            "latest.receiver_id) ORDER BY created_at DESC")) {
            ps.setInt(1, receiverId);
            ps.setInt(2, receiverId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = retrieveMessage(rs);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return messages;
    }

    public static void create(Message message) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO messages(text, sender_id, receiver_id) " +
                                                            "VALUES (?, ?, ?)")) {
            ps.setString(1, message.getText());
            ps.setInt(2, message.getSenderId());
            ps.setInt(3, message.getReceiverId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(Message message) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("UPDATE messages SET status_id = ? WHERE id = ?")) {
            ps.setInt(1, message.getStatusId());
            ps.setInt(2, message.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    private static Message retrieveMessage(ResultSet rs) {
        Message message = new Message();
        try {
            message.setId(rs.getInt("id"));message.setText(rs.getString("text"));
            message.setCreatedAt(rs.getTimestamp("created_at"));
            message.setSenderId(rs.getInt("sender_id"));
            message.setReceiverId(rs.getInt("receiver_id"));
            message.setStatusId(rs.getInt("status_id"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return message;
    }
}
