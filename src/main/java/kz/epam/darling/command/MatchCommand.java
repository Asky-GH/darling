package kz.epam.darling.command;

import kz.epam.darling.constant.Attribute;
import kz.epam.darling.constant.Parameter;
import kz.epam.darling.constant.View;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.UserDAO;
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
        try {
            int matchId;
            try {
                matchId = Integer.parseInt(request.getParameter(Parameter.ID));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Language language = (Language) request.getAttribute(Attribute.LANGUAGE);
            User match = UserDAO.findById(matchId, language.getId());
            request.setAttribute(Attribute.MATCH, match);
            request.getRequestDispatcher(View.MATCH).forward(request, response);
        } catch (ServletException | IOException e) {
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
