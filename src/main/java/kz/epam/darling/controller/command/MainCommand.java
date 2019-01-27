package kz.epam.darling.controller.command;

import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = UserDAO.findAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
