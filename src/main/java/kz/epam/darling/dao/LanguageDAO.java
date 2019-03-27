package kz.epam.darling.dao;

import kz.epam.darling.constant.Column;
import kz.epam.darling.model.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {
    private static final Logger LOGGER = LogManager.getLogger(LanguageDAO.class.getName());
    private static final String FIND_ALL_QUERY = "SELECT * FROM languages";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM languages WHERE id=?";
    private static final String CREATE_QUERY = "INSERT INTO languages(locale, name) VALUES(?,?)";
    private static final String UPDATE_QUERY = "UPDATE languages SET locale=?, name=? WHERE id=?";

    public static List<Language> findAll() {
        List<Language> languages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_ALL_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Language language = retrieveLanguage(rs);
                languages.add(language);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return languages;
    }

    public static Language findById(int languageId) {
        Language language = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(FIND_BY_ID_QUERY)) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    language = retrieveLanguage(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return language;
    }

    public static void create(Language language) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(CREATE_QUERY)) {
            ps.setString(1, language.getLocale());
            ps.setString(2, language.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    public static void update(Language language) {
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, language.getLocale());
            ps.setString(2, language.getName());
            ps.setInt(3, language.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
    }

    private static Language retrieveLanguage(ResultSet rs) {
        Language language = new Language();
        try {
            language.setId(rs.getInt(Column.ID));
            language.setLocale(rs.getString(Column.LOCALE));
            language.setName(rs.getString(Column.NAME));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return language;
    }
}
