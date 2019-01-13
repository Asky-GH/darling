package kz.epam.darling;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email != null && password != null && email.equals("admin@mail.ru") && password.equals("ADMIN")) {
                User user = new User();
                user.setId(1);
                user.setEmail(email);
                user.setPassword(password);
                request.getSession().setAttribute("user", user);
                response.sendRedirect("/");
                return;
            } else {
                request.setAttribute("errorMessage", "Invalid email or password!");
            }
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
