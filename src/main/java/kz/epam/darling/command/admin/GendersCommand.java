package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.model.Gender;
import kz.epam.darling.model.Language;
import kz.epam.darling.dao.GenderDAO;
import kz.epam.darling.dao.LanguageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GendersCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Gender> genders = GenderDAO.find();
        List<Language> languages = LanguageDAO.findAll();
        List<Integer> ids = GenderDAO.findIds();
        request.setAttribute("genders", genders);
        request.setAttribute("languages", languages);
        request.setAttribute("ids", ids);
        try {
            request.getRequestDispatcher("/jsp/admin/genders.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Gender gender = new Gender();
        gender.setId(Integer.parseInt(request.getParameter("id")));
        gender.setLanguage(LanguageDAO.findById(Integer.parseInt(request.getParameter("languageId"))));
        gender.setType(request.getParameter("type"));
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                GenderDAO.create(gender);
                break;

            case "edit":
                GenderDAO.update(gender);
                break;
        }
        try {
            response.sendRedirect(request.getContextPath() + "/admin/genders");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
