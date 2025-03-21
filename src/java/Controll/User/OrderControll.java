/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.User;

import Model.DAO.OrderDAO;
import Model.DTO.Account;
import Model.DTO.Order;
import Model.DTO.OrderDetail;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
@WebServlet(name = "OrderControll", urlPatterns = {"/user/order"})
public class OrderControll extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) session.getAttribute("cart");
            Account account = (Account) session.getAttribute("acc");
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.synchronizeCart(account.getAccID(), cart);

            HashMap<String, OrderDetail> order = orderDAO.getOrderDetailsOfUnpaidOrderOfAccountID(account.getAccID());
            int latestOrderID = orderDAO.getLatestOrderID(account.getAccID());
            double totalCost = orderDAO.getTotalCost(latestOrderID);
            double freight = orderDAO.getFreightByOID(latestOrderID);
            Order o = orderDAO.getOrderByID(latestOrderID);
            log(o.toString());

            String shipAddress = (String) request.getParameter("shipAddress");
            if(shipAddress == null) shipAddress = "";
            orderDAO.updateOrderShipAddress(latestOrderID, shipAddress);
            
            request.setAttribute("status", o.getStatus());
            request.setAttribute("shipAddress", shipAddress);
            request.setAttribute("freight", freight);
            request.setAttribute("order", order);
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("AMOUNT", totalCost);
            request.setAttribute("orderID", latestOrderID);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (Exception ex) {
            log(ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
