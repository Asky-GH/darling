package kz.epam.darling.controller.command;

import kz.epam.darling.model.Message;
import kz.epam.darling.model.User;
import kz.epam.darling.model.dao.MessageDAO;
import kz.epam.darling.model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ChatCommand implements Command {
    private UserDAO userDAO = new UserDAO();
    private MessageDAO messageDAO = new MessageDAO();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException, SQLException,
                                                                                        ClassNotFoundException {
        User user = (User) request.getSession(false).getAttribute("user");
        int match_id = Integer.parseInt(request.getParameter("id"));
        User match = userDAO.findById(match_id);
        List<Message> messages = messageDAO.findByParticipants(user.getId(), match_id);
        request.setAttribute("messages", messages);
        request.setAttribute("match", match);
        request.getRequestDispatcher("jsp/chat.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException,
                                                                                        InterruptedException, SQLException,
                                                                                        ClassNotFoundException {
        User user = (User) request.getSession(false).getAttribute("user");
        int match_id = Integer.parseInt(request.getParameter("id"));
        String text = request.getParameter("text");
        Message message = new Message();
        message.setText(text);
        message.setUser_id(user.getId());
        messageDAO.create(message);
        message = messageDAO.findByUserId(message.getUser_id());
        messageDAO.relate(message, match_id);
        doGet(request, response);
    }
}
