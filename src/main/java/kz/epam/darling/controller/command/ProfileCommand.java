package kz.epam.darling.controller.command;

import kz.epam.darling.model.Info;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.CountryDAO;
import kz.epam.darling.model.dao.GenderDAO;
import kz.epam.darling.model.dao.InfoDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ProfileCommand implements Command {
    private GenderDAO genderDAO = new GenderDAO();
    private CountryDAO countryDAO = new CountryDAO();
    private InfoDAO infoDAO = new InfoDAO();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException,
                                                                                        SQLException, ClassNotFoundException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String countryName = request.getParameter("countryName");
        if (firstName.isEmpty() || lastName.isEmpty() || gender == null || birthday.isEmpty() || countryName.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required!");
            request.getRequestDispatcher("jsp/profile.jsp").forward(request, response);
        } else {
            User user = (User) request.getSession(false).getAttribute("user");
            Info info = new Info();
            info.setFirstName(firstName);
            info.setLastName(lastName);
            info.setGender(genderDAO.findByName(gender));
            info.setBirthday(Date.valueOf(birthday));
            info.setCountry(countryDAO.findByName(countryName));
            info.setUserId(user.getId());
            infoDAO.create(info);
            user.setInfo(info);
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}
