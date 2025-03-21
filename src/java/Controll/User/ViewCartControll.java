/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.User;

import Model.DAO.OrderDAO;
import Model.DAO.ProductDAO;
import Model.DTO.Account;
import Model.DTO.Order;
import Model.DTO.OrderDetail;
import Model.DTO.Product;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mndkh
 */
@WebServlet(name = "ViewCartControll", urlPatterns = {"/user/viewcart"})
public class ViewCartControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");
            Account account = (Account) session.getAttribute("acc");

            double totalAmount = 0;
            if (cart != null && !cart.isEmpty()) {
                for (OrderDetail detail : cart.values()) {
                    Product product = new ProductDAO().getProductByID(detail.getProductID());
                    totalAmount += product.getUnitPrice() * detail.getQuantity();
                }
            } else {
                cart = new HashMap<>();
            }

            Order unpaidOrder = new OrderDAO().getUnpaidOrder(account.getAccID());
            String shipAddress = "";
            if (unpaidOrder != null) {
                shipAddress = unpaidOrder.getShipAddress();
            }
            request.setAttribute("shipAddress", shipAddress);
            request.setAttribute("cart", cart);
            request.setAttribute("totalAmount", totalAmount);

            List<HashMap<String, OrderDetail>> historyOrders = (List<HashMap<String, OrderDetail>>) new OrderDAO().getHistoryListByAccountID(account.getAccID());
            log(historyOrders.toString());

            request.setAttribute("historyOrders", historyOrders);

            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (Exception ex) {
            log(ex.getMessage());
        }
    }
}
