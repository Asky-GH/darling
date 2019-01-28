package kz.epam.darling.controller.command;

import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(MainCommand.class.getName());


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserDAO.findAll();
        request.setAttribute("users", users);
        try {
            request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
