package kz.epam.darling.controller.command;

import com.google.gson.Gson;
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
        int sender_id = Integer.parseInt(request.getParameter("sender_id"));
        int receiver_id = Integer.parseInt(request.getParameter("receiver_id"));
        List<Message> messages = MessageDAO.findNew(sender_id, receiver_id);
        if (messages.size() > 0) {
            for (Message message : messages) {
                message.setStatus_id(2);
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
