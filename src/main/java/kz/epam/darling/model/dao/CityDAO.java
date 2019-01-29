package kz.epam.darling.model.dao;

import kz.epam.darling.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAO {
    private static final Logger LOGGER = LogManager.getLogger(CityDAO.class.getName());


    public static City findById(int city_id) {
        City city = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM cities WHERE id = ?")) {
            ps.setInt(1, city_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return city;
    }
}
