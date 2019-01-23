package kz.epam.darling.controller;

import kz.epam.darling.controller.command.CommandFactory;
import kz.epam.darling.model.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(FrontController.class.getName());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CommandFactory.getInstance().getCommand(req.getServletPath()).execute(req, resp);
        } catch (InterruptedException | SQLException | ClassNotFoundException applicationException) {
            try {
                ConnectionPool.getInstance().dispose();
            } catch (SQLException | ClassNotFoundException connectionPoolDisposeException) {
                LOGGER.info(connectionPoolDisposeException);
            }
            LOGGER.error(applicationException);
            throw new ServletException(applicationException);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
