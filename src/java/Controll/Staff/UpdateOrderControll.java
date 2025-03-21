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
                request.setAttribute("error", "list null or empty");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            Order o = new OrderDAO().getOrderByID(oid);
            double totalCost = new OrderDAO().getTotalCost(oid);

            request.setAttribute("freight", o.getFreight());
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("shipAddress", o.getShipAddress());
            request.setAttribute("shippedDate", o.getShippedDate());
            request.setAttribute("order", list);

            request.getRequestDispatcher("/update-order.jsp").forward(request, response);
        } catch (Exception ex) {
            log(ex.getMessage());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int oid = Integer.parseInt(request.getParameter("oid"));
            String shipAddress = request.getParameter("shipAddress");
            Date shippedDate = Date.valueOf(request.getParameter("shippedDate"));
            log(shipAddress);
            String status = request.getParameter("status");
            log(status);
            new OrderDAO().updateOrderShipAddress(oid, shipAddress);
            new OrderDAO().updateOrderStatus(oid, status);
            new OrderDAO().updateOrderShippedDate(oid, shippedDate);
            request.setAttribute("shipAddress", shipAddress);
            request.setAttribute("status", status);
            request.setAttribute("success", "Updated successfully");

            HashMap<String, OrderDetail> list = new OrderDetailDAO().getOrderDetailsByOrderID(oid);

            if (list == null || list.isEmpty()) {
                request.setAttribute("error", "list null or empty");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            Order o = new OrderDAO().getOrderByID(oid);
            log("3");
            double totalCost = new OrderDAO().getTotalCost(oid);

            request.setAttribute("freight", o.getFreight());
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("shipAddress", o.getShipAddress());
            request.setAttribute("shippedDate", o.getShippedDate());
            request.setAttribute("order", list);

            request.getRequestDispatcher("/update-order.jsp").forward(request, response);
        } catch (Exception ex) {
            log("run here");
            log(ex.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
