package kz.epam.darling.dao;

import kz.epam.darling.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.*;

public class ImageDAO {
    private static final Logger LOGGER = LogManager.getLogger(ImageDAO.class.getName());


    public static void create(Image image) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO images(user_id) VALUES (?)")) {
            ps.setInt(1, image.getUser_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static Blob findById(int image_id) {
        Blob blob = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM images WHERE id = ?")) {
            ps.setInt(1, image_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   blob = rs.getBlob("data");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return blob;
    }

    public static Image findByUserId(int user_id) {
        Image image = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM images WHERE user_id = ?")) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    image = new Image();
                    image.setId(rs.getInt("id"));
                    image.setUrl(rs.getString("url"));
                    image.setUser_id(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return image;
    }

    public static void update(Image image, Part part) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("UPDATE images SET data = ?, url = ? WHERE user_id = ?")) {
            ps.setBlob(1, part.getInputStream());
            ps.setString(2, image.getUrl());
            ps.setInt(3, image.getUser_id());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }
}
