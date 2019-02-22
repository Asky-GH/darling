package kz.epam.darling.controller.command;

import kz.epam.darling.model.Country;
import kz.epam.darling.model.dao.CountryDAO;
import kz.epam.darling.util.JsonSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CountryCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Country> countries = CountryDAO.findAll(Integer.parseInt(request.getParameter("languageId")));
        try {
            JsonSender.send(response, countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
