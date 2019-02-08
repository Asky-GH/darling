package kz.epam.darling.model.dao;

import kz.epam.darling.model.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {
    public static List<Language> findAll() {
        List<Language> languages = new ArrayList<>();
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM languages");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Language language = retrieveLanguage(rs);
                languages.add(language);
            }
        } catch (SQLException e) {
//            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().releaseConnection(con);
        }
        return languages;
    }

    public static Language findById(int languageId) {
        Language language = null;
        Connection con = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM languages WHERE id = ?")) {
            ps.setInt(1, languageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    language = retrieveLanguage(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return language;
    }

    private static Language retrieveLanguage(ResultSet rs) {
        Language language = new Language();
        try {
            language.setId(rs.getInt("id"));
            language.setLocale(rs.getString("locale"));
            language.setName(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return language;
    }
}
