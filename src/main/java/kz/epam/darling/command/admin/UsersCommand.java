package kz.epam.darling.command.admin;

import kz.epam.darling.command.Command;
import kz.epam.darling.constant.*;
import kz.epam.darling.model.Role;
import kz.epam.darling.model.User;
import kz.epam.darling.dao.UserDAO;
import kz.epam.darling.dao.RoleDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UsersCommand.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserDAO.findAll(Constant.DEFAULT_LANGUAGE_ID);
        List<Role> roles = RoleDAO.findAll();
        request.setAttribute(Attribute.USERS, users);
        request.setAttribute(Attribute.ROLES, roles);
        try {
            request.getRequestDispatcher(View.USERS).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter(Parameter.ACTION);
        switch (action) {
            case Action.CHANGE_ROLE:
                changeRole(request, response);
                break;

            case Action.DELETE:
                delete(request, response);
                break;
        }
    }

    private void changeRole(HttpServletRequest request, HttpServletResponse response) {
        User user = UserDAO.findById(Integer.parseInt(request.getParameter(Parameter.ID)), Constant.DEFAULT_LANGUAGE_ID);
        Role role = RoleDAO.findById(Integer.valueOf(request.getParameter(Parameter.ROLE_ID)));
        user.setRole(role);
        UserDAO.update(user);
        redirect(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        UserDAO.delete(Integer.parseInt(request.getParameter(Parameter.ID)));
        redirect(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + Route.USERS);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
