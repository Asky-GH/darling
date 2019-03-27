package kz.epam.darling.command;

import kz.epam.darling.constant.Parameter;
import kz.epam.darling.model.Country;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.util.JsonSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CountryCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CountryCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Country> countries;
            try {
                countries = CountryDAO.findAll(Integer.parseInt(request.getParameter(Parameter.LANGUAGE_ID)));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            JsonSender.send(response, countries);
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
