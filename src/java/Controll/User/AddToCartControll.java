package Controll.Staff;

import Model.DAO.MobileDAO;
import Model.DTO.Product;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/user/addToCart"})
public class AddToCartControll extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            HashMap<String, Product> cart = (HashMap<String, Product>) session.getAttribute("cart");
            if (cart == null) {
                cart = new HashMap<>();
            }

            String mobileID = request.getParameter("id");
            MobileDAO mobileDAO = new MobileDAO();
            Product mobile = mobileDAO.getMobileByID(mobileID);

            if (mobile == null) {
                request.setAttribute("error", "Product not found!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            if (!mobile.isOnSale()) {
                request.setAttribute("error", "This product is currently not for sale.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            if (mobile.getQuantity() <= 0) {
                request.setAttribute("error", "Out of stock!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            if (cart.containsKey(mobileID)) {
                Product cartItem = cart.get(mobileID);
                int newQuantity = cartItem.getQuantity() + 1;

                if (newQuantity > mobile.getQuantity()) {
                    request.setAttribute("error", "Not enough stock available! Current stock: " + mobile.getQuantity());
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                cartItem.setQuantity(newQuantity);
            } else {
                if (1 > mobile.getQuantity()) {
                    request.setAttribute("error", "Not enough stock available! Current stock: " + mobile.getQuantity());
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                mobile.setQuantity(1);
                cart.put(mobileID, mobile);
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getHeader("Referer"));
        } catch (Exception ex) {
            request.setAttribute("error", "An unexpected error occurred.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
