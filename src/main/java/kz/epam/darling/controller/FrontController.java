package kz.epam.darling.controller;

import kz.epam.darling.controller.command.Command;
import kz.epam.darling.controller.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(action);
        command.execute(req, resp);
    }
}
