package kz.epam.darling.controller.command;

import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MatchCommand implements Command {
    private UserDAO userDAO = new UserDAO();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException, SQLException,
                                                                                        ClassNotFoundException {
        int match_id = Integer.parseInt(request.getParameter("id"));
        User match = userDAO.findById(match_id);
        request.setAttribute("match", match);
        request.getRequestDispatcher("jsp/match.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException, SQLException,
                                                                                        ClassNotFoundException {

    }
}
