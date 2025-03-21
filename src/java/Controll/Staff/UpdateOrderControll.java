/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.Staff;

import Model.DAO.OrderDAO;
import Model.DAO.OrderDetailDAO;
import Model.DTO.Order;
import Model.DTO.OrderDetail;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mndkh
 */
@WebServlet(name = "UpdateOrderControll", urlPatterns = {"/staff/updateorder"})
public class UpdateOrderControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int oid = Integer.parseInt(request.getParameter("oid"));
            HashMap<String, OrderDetail> list = new OrderDetailDAO().getOrderDetailsByOrderID(oid);

            if (list == null || list.isEmpty()) {
                request.setAttribute("error", "Order details not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Order o = new OrderDAO().getOrderByID(oid);
            if (o == null) {
                request.setAttribute("error", "Order not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            double totalCost = new OrderDAO().getTotalCost(oid);

            request.setAttribute("status", o.getStatus());
            request.setAttribute("freight", o.getFreight());
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("shipAddress", o.getShipAddress());
            request.setAttribute("shippedDate", o.getShippedDate());
            request.setAttribute("order", list);

            request.getRequestDispatcher("/update-order.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            log("Invalid order ID format: " + e.getMessage());
            request.setAttribute("error", "Unexpected error");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception ex) {
            log("Error in doGet: " + ex.getMessage());
            request.setAttribute("error", "Unexpected error");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int oid = Integer.parseInt(request.getParameter("oid"));
            String shipAddress = request.getParameter("shipAddress");
            String shippedDateStr = request.getParameter("shippedDate");
            String status = request.getParameter("status");

            if (shipAddress == null) {
                shipAddress = "";
            }
            if (status == null) {
                request.setAttribute("error", "Status cannot be null.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            new OrderDAO().updateOrderShipAddress(oid, shipAddress);
            new OrderDAO().updateOrderStatus(oid, status);

            if (shippedDateStr != null && !shippedDateStr.isEmpty()) {
                new OrderDAO().updateOrderShippedDate(oid, Date.valueOf(shippedDateStr));
                request.setAttribute("shippedDate", shippedDateStr);
            }

            request.setAttribute("shipAddress", shipAddress);
            request.setAttribute("status", status);
            request.setAttribute("success", "Updated successfully");

            HashMap<String, OrderDetail> list = new OrderDetailDAO().getOrderDetailsByOrderID(oid);
            if (list == null || list.isEmpty()) {
                request.setAttribute("error", "Order details not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Order o = new OrderDAO().getOrderByID(oid);
            if (o == null) {
                request.setAttribute("error", "Order not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            double totalCost = new OrderDAO().getTotalCost(oid);

            request.setAttribute("freight", o.getFreight());
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("shipAddress", o.getShipAddress());
            request.setAttribute("order", list);

            request.getRequestDispatcher("/update-order.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            log("Invalid order ID format: " + e.getMessage());
            request.setAttribute("error", "Invalid order ID");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception ex) {
            log("Error in doPost: " + ex.getMessage());
            request.setAttribute("error", "Unexpected error");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
