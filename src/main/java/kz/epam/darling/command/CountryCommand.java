package kz.epam.darling.command;

import kz.epam.darling.model.Country;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.util.JsonSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CountryCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Country> countries;
            try {
                countries = CountryDAO.findAll(Integer.parseInt(request.getParameter("languageId")));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            JsonSender.send(response, countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
