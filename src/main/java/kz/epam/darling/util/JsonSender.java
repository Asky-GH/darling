package kz.epam.darling.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonSender {
    public static void send(HttpServletResponse response, Object object) throws IOException {
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        writer.print(gson.toJson(object));
        writer.flush();
        writer.close();
    }
}
