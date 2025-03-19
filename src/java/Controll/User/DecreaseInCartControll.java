package Controll.User;

import Model.DTO.Product;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DecreaseInCartControll", urlPatterns = {"/user/decreaseInCart"})
public class DecreaseInCartControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap<String, Product> cart = (HashMap<String, Product>) session.getAttribute("cart");

        if (cart != null) {
            String mobileID = request.getParameter("id");

            if (mobileID != null && cart.containsKey(mobileID)) {
                Product mobile = cart.get(mobileID);
                int newQuantity = mobile.getQuantity() - 1;

                if (newQuantity > 0) {
                    mobile.setQuantity(newQuantity);
                } else {
                    cart.remove(mobileID); 
                }
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect(request.getHeader("Referer")); 
    }
}
