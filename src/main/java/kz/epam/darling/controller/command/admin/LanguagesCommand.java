package kz.epam.darling.controller.command.admin;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.dao.LanguageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LanguagesCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Language> languages = LanguageDAO.findAll();
        request.setAttribute("languages", languages);
        try {
            request.getRequestDispatcher("/jsp/admin/languages.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Language language = new Language();
        language.setLocale(request.getParameter("locale"));
        language.setName(request.getParameter("name"));
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                LanguageDAO.create(language);
                break;

            case "edit":
                language.setId(Integer.parseInt(request.getParameter("id")));
                LanguageDAO.update(language);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + "/admin/languages");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
