package kz.epam.darling.controller;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.command.CommandFactory;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.LanguageDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DarlingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Language> languages = LanguageDAO.findAll();
        request.setAttribute(Attribute.LANGUAGES, languages);
        CommandFactory.getInstance().getCommand(request.getServletPath()).execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
