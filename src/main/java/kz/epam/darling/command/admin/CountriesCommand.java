package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.model.Country;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.CountryDAO;
import kz.epam.darling.dao.LanguageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CountriesCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Country> countries = CountryDAO.find();
        List<Language> languages = LanguageDAO.findAll();
        List<Integer> ids = CountryDAO.findIds();
        request.setAttribute("countries", countries);
        request.setAttribute("languages", languages);
        request.setAttribute("ids", ids);
        try {
            request.getRequestDispatcher("/jsp/admin/countries.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Country country = new Country();
        country.setId(Integer.parseInt(request.getParameter("id")));
        country.setLanguage(LanguageDAO.findById(Integer.parseInt(request.getParameter("languageId"))));
        country.setName(request.getParameter("name"));
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                CountryDAO.create(country);
                break;

            case "edit":
                CountryDAO.update(country);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + "/admin/countries");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
