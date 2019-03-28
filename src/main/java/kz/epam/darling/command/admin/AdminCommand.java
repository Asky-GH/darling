package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AdminCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(View.ADMIN).forward(request, response);
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
