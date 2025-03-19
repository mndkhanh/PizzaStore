package Controll.User;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Model.DTO.Product;

@WebServlet(name = "RemoveFromCartControll", urlPatterns = {"/user/removeFromCart"})
public class RemoveFromCartControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap<String, Product> cart = (HashMap<String, Product>) session.getAttribute("cart");

        if (cart != null) {
            String mobileID = request.getParameter("id");

            if (mobileID != null && cart.containsKey(mobileID)) {
                cart.remove(mobileID);
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect(request.getHeader("Referer")); 
    }
}
