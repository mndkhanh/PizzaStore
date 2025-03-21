package Controll.User;

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

@WebServlet(name = "RemoveFromCartControll", urlPatterns = {"/user/removeFromCart"})
public class RemoveFromCartControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
        HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");
        Account acc = (Account) session.getAttribute("acc");
        String accountID = acc.getAccID();
        
        
        if (cart != null) {
            String productID = request.getParameter("pid");

            if (productID != null && cart.containsKey(productID)) {
                cart.remove(productID);
                new OrderDAO().synchronizeCart(accountID, cart);
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect(request.getHeader("Referer")); 
        } catch(Exception ex) {
            
        }
    }
}
