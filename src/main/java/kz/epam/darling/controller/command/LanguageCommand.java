package kz.epam.darling.controller.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.LanguageDAO;
import kz.epam.darling.model.dao.UserDAO;

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
        User user = (User) request.getSession(false).getAttribute("principal");
        if (user != null) {
            user = UserDAO.findById(user.getId(), languageId);
            request.getSession(false).setAttribute("principal", user);
        }
        request.getSession(false).setAttribute("language", language);
        try {
            String[] requestURI = request.getParameter("from").split("/");
            String requestQuery = request.getParameter("query");
            String action = requestURI[requestURI.length - 1].split("\\.")[0];
            if (requestQuery.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/" + action);
            } else {
                response.sendRedirect(request.getContextPath() + "/" + action + "?" + requestQuery);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
