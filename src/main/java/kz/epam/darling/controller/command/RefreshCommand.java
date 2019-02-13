package kz.epam.darling.controller.command;

import com.google.gson.Gson;
import kz.epam.darling.model.Language;
import kz.epam.darling.model.Message;
import kz.epam.darling.model.dao.MessageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RefreshCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int senderId = Integer.parseInt(request.getParameter("senderId"));
        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        List<Message> messages = MessageDAO.findNew(senderId, receiverId);
        if (messages.size() > 0) {
            for (Message message : messages) {
                message.setStatusId(2);
                MessageDAO.update(message);
            }
            try {
                PrintWriter writer = response.getWriter();
                Gson gson = new Gson();
                writer.print(gson.toJson(messages));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
