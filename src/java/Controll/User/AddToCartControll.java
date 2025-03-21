package Controll.User;

import Model.DAO.OrderDAO;
import Model.DAO.ProductDAO;
import Model.DTO.Account;
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

@WebServlet(name = "AddToCartControll", urlPatterns = {"/user/addToCart"})
public class AddToCartControll extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account account =(Account) session.getAttribute("acc");
            String accountID = account.getAccID();
            HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");

            if (cart == null) {
                cart = new HashMap<>();
            }

            String productID = request.getParameter("pid");
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductByID(productID);

            if (product == null) {
                request.setAttribute("error", "Product not found!");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            OrderDetail cartItem = cart.get(productID);
            if (cart.containsKey(productID)) {
                int newQuantity = cartItem.getQuantity() + 1;
                cartItem.setQuantity(newQuantity);
            } else {
                cart.put(productID, new OrderDetail(productID, 1));
            }

            session.setAttribute("cart", cart);
            new OrderDAO().synchronizeCart(accountID, cart);
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
