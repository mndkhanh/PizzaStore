package Controll.Auth;

import Model.DAO.OrderDAO;
import Model.DTO.Account;
import Model.DTO.OrderDetail;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutControll", urlPatterns = {"/logout"})
public class LogoutControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");
                Account account = (Account) session.getAttribute("acc");
                new OrderDAO().synchronizeCart(account.getAccID(), cart);
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/home");
        } catch (Exception ex) {
            log(ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
