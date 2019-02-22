package kz.epam.darling.controller.command.admin;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.model.Role;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;
import kz.epam.darling.model.dao.RoleDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserDAO.findAll(1);
        List<Role> roles = RoleDAO.findAll();
        request.setAttribute("users", users);
        request.setAttribute("roles", roles);
        try {
            request.getRequestDispatcher("/jsp/admin/users.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        switch (action) {
            case "change-role":
                changeRole(request, response);
                break;

            case "delete":
                delete(request, response);
                break;
        }
    }

    private void changeRole(HttpServletRequest request, HttpServletResponse response) {
        User user = UserDAO.findById(Integer.parseInt(request.getParameter("id")), 1);
        Role role = RoleDAO.findById(Integer.valueOf(request.getParameter("roleId")));
        user.setRole(role);
        UserDAO.update(user);
        redirect(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        UserDAO.delete(Integer.parseInt(request.getParameter("id")));
        redirect(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
