package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
import kz.epam.darling.model.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {
    private static final Logger LOGGER = LogManager.getLogger(ProfileDAO.class.getName());
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM profile WHERE user_id=?";
    private static final String CREATE_QUERY = "INSERT INTO profile(first_name, last_name, birthday, gender_id, " +
                                                "country_id, city_id, user_id, image_id) VALUES(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE profile SET country_id=?, city_id=? WHERE id=?";

    public static Profile findByUserId(int userId, int languageId) {
        Profile profile = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_USER_ID_QUERY)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    profile = new Profile();
                    profile.setId(rs.getInt(Column.ID));
                    profile.setFirstName(rs.getString(Column.FIRST_NAME));
                    profile.setLastName(rs.getString(Column.LAST_NAME));
                    profile.setBirthday(rs.getDate(Column.BIRTHDAY));
                    profile.setGender(GenderDAO.findById(rs.getInt(Column.GENDER_ID), languageId));
                    profile.setCountry(CountryDAO.findById(rs.getInt(Column.COUNTRY_ID), languageId));
                    profile.setCity(CityDAO.findById(rs.getInt(Column.CITY_ID), languageId));
                    profile.setUserId(userId);
                    profile.setImage(ImageDAO.findByUserId(userId));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return profile;
    }

    public static void create(Profile profile) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setDate(3, profile.getBirthday());
            ps.setInt(4, profile.getGender().getId());
            ps.setInt(5, profile.getCountry().getId());
            ps.setInt(6, profile.getCity().getId());
            ps.setInt(7, profile.getUserId());
            ps.setInt(8, profile.getImage().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(Profile profile) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
            ps.setInt(1, profile.getCountry().getId());
            ps.setInt(2, profile.getCity().getId());
            ps.setInt(3, profile.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }
}
