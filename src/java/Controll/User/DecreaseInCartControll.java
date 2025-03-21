package Controll.User;

import Model.DTO.OrderDetail;
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
        HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");

        if (cart != null) {
            String productID = request.getParameter("pid");

            if (productID != null && cart.containsKey(productID)) {
                OrderDetail detail = cart.get(productID);
                int newQuantity = detail.getQuantity() - 1;

                if (newQuantity > 0) {
                    detail.setQuantity(newQuantity);
                } else {
                    cart.remove(productID); 
                }
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect(request.getHeader("Referer")); 
    }
}
