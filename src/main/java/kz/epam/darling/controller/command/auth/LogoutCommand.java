package kz.epam.darling.controller.command.auth;

import kz.epam.darling.controller.command.Command;
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

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
