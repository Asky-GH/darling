package kz.epam.darling.controller.command;

import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainCommand implements Command {
    private UserDAO userDAO = new UserDAO();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException,
                                                                                        SQLException, ClassNotFoundException {
        List<User> users = userDAO.findAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
