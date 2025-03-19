package Controll.Staff;

import Model.DAO.MobileDAO;
import Model.DTO.Product;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateControll", urlPatterns = {"/staff/create"})
public class CreateControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String mobileID = request.getParameter("mobileID");
            String mobileName = request.getParameter("mobileName");
            String description = request.getParameter("description");
            
            String priceString = request.getParameter("price");
            double price = 0.0;
            if (priceString != null && !priceString.isEmpty()) {
                try {
                    price = Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid price format");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            }
            
            String productionDateString = request.getParameter("productionDate");
            Date productionDate = null;
            if (productionDateString != null && !productionDateString.trim().isEmpty()) {
                try {
                    productionDate = Date.valueOf(productionDateString);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", "Invalid date format.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("error", "Production Date is required.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean onSale = Boolean.parseBoolean(request.getParameter("onSale"));
            String img = request.getParameter("img");

            Product newMobile = new Product(mobileID, description, price, mobileName, productionDate, quantity, onSale, img);
            MobileDAO mobileDAO = new MobileDAO();
            boolean success = mobileDAO.createMobile(newMobile);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/staff/create?success=Created successfully");
            } else {
                request.setAttribute("error", "Failed to create new mobile.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while creating the mobile.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Create Mobile details servlet";
    }
}
