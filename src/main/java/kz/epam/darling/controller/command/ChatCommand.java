package kz.epam.darling.controller.command;

import kz.epam.darling.model.Message;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.MessageDAO;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChatCommand implements Command {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User sender = (User) request.getSession(false).getAttribute("user");
        int receiver_id = Integer.parseInt(request.getParameter("id"));
        User receiver = UserDAO.findById(receiver_id);
        List<Message> messages = MessageDAO.findByParticipants(sender.getId(), receiver_id);
        request.setAttribute("messages", messages);
        request.setAttribute("receiver", receiver);
        request.getRequestDispatcher("jsp/chat.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User sender = (User) request.getSession(false).getAttribute("user");
        int receiver_id = Integer.parseInt(request.getParameter("id"));
        String text = request.getParameter("text");
        Message message = new Message();
        message.setText(text);
        message.setSender_id(sender.getId());
        message.setReceiver_id(receiver_id);
        MessageDAO.create(message);
        doGet(request, response);
    }
}
