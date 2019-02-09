package kz.epam.darling.controller.command;

import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MatchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MatchCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Language language = (Language) request.getAttribute("language");
        int matchId = Integer.parseInt(request.getParameter("id"));
        User match = UserDAO.findById(matchId, language.getId());
        request.setAttribute("match", match);
        try {
            request.getRequestDispatcher("jsp/match.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
