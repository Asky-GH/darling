package kz.epam.darling.controller.command;

import com.google.gson.Gson;
import kz.epam.darling.model.City;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.dao.CityDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LocationCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int country_id = Integer.parseInt(request.getParameter("country_id"));
        Language language = (Language) request.getAttribute("language");
        List<City> cities = CityDAO.findByCountryId(country_id, language.getId());
        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();
            writer.print(gson.toJson(cities));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
