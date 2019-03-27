package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
import kz.epam.darling.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.*;

public class ImageDAO {
    private static final Logger LOGGER = LogManager.getLogger(ImageDAO.class.getName());
    private static final String CREATE_QUERY = "INSERT INTO images(user_id) VALUES(?)";
    private static final String FIND_BY_QUERY = "SELECT * FROM images WHERE id=?";
    private static final String FIND_BY_USER_QUERY = "SELECT * FROM images WHERE user_id=?";
    private static final String UPDATE_QUERY = "UPDATE images SET data=?, url=? WHERE user_id=?";

    public static void create(Image image) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
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
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_QUERY)) {
            ps.setInt(1, image_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   blob = rs.getBlob(Column.DATA);
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
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_USER_QUERY)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    image = new Image();
                    image.setId(rs.getInt(Column.ID));
                    image.setUrl(rs.getString(Column.URL));
                    image.setUser_id(rs.getInt(Column.USER_ID));
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
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
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
