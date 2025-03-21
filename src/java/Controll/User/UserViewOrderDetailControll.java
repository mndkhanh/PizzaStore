/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.User;

import Model.DAO.OrderDAO;
import Model.DAO.OrderDetailDAO;
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
@WebServlet(name = "UserViewOrderDetailControll", urlPatterns = {"/user/vieworder"})
public class UserViewOrderDetailControll extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            log("user view order is running");
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("acc");
            int oid = Integer.parseInt(request.getParameter("oid"));
            // Fetch order details from database
            OrderDAO orderDAO = new OrderDAO();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

            Order order = orderDAO.getOrderByID(oid);
            HashMap<String, OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderID(oid);

            // this only allow user to see their own list of order, and staff can see full
            if (!account.getAccID().equals(order.getAccountID()) && !account.isIsStaff()) {
                request.setAttribute("error", "You cannot freely see orders");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

            request.setAttribute("order", orderDetails);
            request.setAttribute("status", order.getStatus());
            request.setAttribute("freight", order.getFreight());
            request.setAttribute("totalCost", orderDAO.getTotalCost(oid));
            request.setAttribute("shipAddress", order.getShipAddress());
            request.setAttribute("AMOUNT", orderDAO.getTotalCost(oid));
            request.setAttribute("orderID", oid);

            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (Exception ex) {
            log(ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
