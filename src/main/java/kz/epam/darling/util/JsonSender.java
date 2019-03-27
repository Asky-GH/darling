package kz.epam.darling.util;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonSender {
    private static final Logger LOGGER = LogManager.getLogger(JsonSender.class.getName());

    public static void send(HttpServletResponse response, Object object) {
        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();
            writer.print(gson.toJson(object));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
