package kz.epam.darling.command.auth;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        try {
            response.sendRedirect(request.getContextPath() + Route.INDEX);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
