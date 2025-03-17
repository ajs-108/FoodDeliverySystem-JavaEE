package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LogoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session.getAttribute("user") != null){
            session.invalidate();
        }
        response.getWriter().write("Thank You!!!");
    }
}
