package Controll.Staff;

import Model.DAO.OrderDAO;
import Model.DTO.Order;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewReportControll", urlPatterns = {"/staff/viewreport"})
public class ViewReportControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrderDAO orderDAO = new OrderDAO();
            Date startDate = Date.valueOf(LocalDate.of(0001, 1, 1)); 
            Date endDate = Date.valueOf(LocalDate.of(9999, 12, 31));

            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            if (startDateStr != null) {
                startDate = Date.valueOf(startDateStr);
            }
            if (endDateStr != null) {
                endDate = Date.valueOf(endDateStr);
            }
            List<Order> orders = orderDAO.getAllSuccessOrders(startDate, endDate);
            log(orders.toString());

            double totalRevenue = 0;
            for (Order o : orders) {
                totalRevenue += orderDAO.getTotalCost(o.getOrderID());
            }

            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("orders", orders);
            request.setAttribute("totalRevenue", totalRevenue);
            request.getRequestDispatcher("/view-report.jsp").forward(request, response);
        } catch (Exception ex) {
            log(ex.getMessage());
            log("run here ex");
        }
    }
}
