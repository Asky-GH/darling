package kz.epam.darling.model.dao;

import kz.epam.darling.model.Gender;
import kz.epam.darling.util.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderDAO {
    private static final Logger LOGGER = LogManager.getLogger(GenderDAO.class.getName());


    static Gender findById(Integer id) {
        Gender gender;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders WHERE id = ?")) {
            ps.setInt(1, id);
            gender = retrieveGender(ps);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ApplicationException();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return gender;
    }

    public static Gender findByName(String name) {
        Gender gender;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM genders WHERE name = ?")) {
            ps.setString(1, name);
            gender = retrieveGender(ps);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ApplicationException();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return gender;
    }

    private static Gender retrieveGender(PreparedStatement ps) {
        Gender gender = null;
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                gender = new Gender();
                gender.setId(rs.getInt("id"));
                gender.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ApplicationException();
        }
        return gender;
    }
}
