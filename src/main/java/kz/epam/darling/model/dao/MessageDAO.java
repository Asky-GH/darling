package kz.epam.darling.model.dao;

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


    public static List<Message> findByParticipants(int sender_id, int receiver_id) {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM messages WHERE sender_id IN (?, ?) AND " +
                                                            "receiver_id IN (?, ?)")) {
            ps.setInt(1, sender_id);
            ps.setInt(2, receiver_id);
            ps.setInt(3, sender_id);
            ps.setInt(4, receiver_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setId(rs.getInt("id"));
                    message.setText(rs.getString("text"));
                    message.setCreated_at(rs.getTimestamp("created_at"));
                    message.setSender_id(rs.getInt("sender_id"));
                    message.setReceiver_id(rs.getInt("receiver_id"));
                    message.setStatus_id(rs.getInt("status_id"));
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

    public static List<Message> findNew(int sender_id, int receiver_id) {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM messages WHERE sender_id IN (?, ?) AND " +
                "receiver_id IN (?, ?) AND status_id = 1")) {
            ps.setInt(1, sender_id);
            ps.setInt(2, receiver_id);
            ps.setInt(3, sender_id);
            ps.setInt(4, receiver_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setId(rs.getInt("id"));
                    message.setText(rs.getString("text"));
                    message.setCreated_at(rs.getTimestamp("created_at"));
                    message.setSender_id(rs.getInt("sender_id"));
                    message.setReceiver_id(rs.getInt("receiver_id"));
                    message.setStatus_id(rs.getInt("status_id"));
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
            ps.setInt(2, message.getSender_id());
            ps.setInt(3, message.getReceiver_id());
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
            ps.setInt(1, message.getStatus_id());
            ps.setInt(2, message.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }
}
