package kz.epam.darling.model.dao;

import kz.epam.darling.model.Info;
import kz.epam.darling.util.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoDAO {
    private static final Logger LOGGER = LogManager.getLogger(InfoDAO.class.getName());


    static Info findByUserId(int id) {
        Info info = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM info WHERE user_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    info = new Info();
                    info.setId(rs.getInt("id"));
                    info.setFirstName(rs.getString("first_name"));
                    info.setLastName(rs.getString("last_name"));
                    info.setGender(GenderDAO.findById(rs.getInt("gender_id")));
                    info.setBirthday(rs.getDate("birthday"));
                    info.setCountry(CountryDAO.findById(rs.getInt("country_id")));
                    info.setUserId(id);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ApplicationException();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return info;
    }

    public static void create(Info info) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO info(first_name, last_name, birthday, " +
                                                            "gender_id, country_id, user_id) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, info.getFirstName());
            ps.setString(2, info.getLastName());
            ps.setDate(3, info.getBirthday());
            ps.setInt(4, info.getGender().getId());
            ps.setInt(5, info.getCountry().getId());
            ps.setInt(6, info.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ApplicationException();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }
}
