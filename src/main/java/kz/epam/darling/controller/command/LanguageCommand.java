package kz.epam.darling.controller.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.dao.LanguageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LanguageCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int languageId = Integer.parseInt(request.getParameter("languageId"));
        Language language = LanguageDAO.findById(languageId);
        request.getSession(false).setAttribute("language", language);
        try {
            String[] requestURI = request.getParameter("from").split("/");
            response.sendRedirect(request.getContextPath() + "/" + requestURI[requestURI.length - 1].split("\\.")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
