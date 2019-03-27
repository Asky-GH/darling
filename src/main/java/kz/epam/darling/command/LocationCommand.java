package kz.epam.darling.command;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.model.City;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.CityDAO;
import kz.epam.darling.util.JsonSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LocationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LocationCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                int countryId = Integer.parseInt(request.getParameter(Parameter.COUNTRY_ID));
                Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
                List<City> cities = CityDAO.findByCountryId(countryId, language.getId());
                JsonSender.send(response, cities);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
