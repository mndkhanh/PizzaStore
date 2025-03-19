package Controll.Staff;

import Model.DAO.MobileDAO;
import Model.DTO.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "IDSearchControll", urlPatterns = {"/staff/idSearch"})
public class IDSearchControll extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String id = request.getParameter("id");

            MobileDAO mobileDAO = new MobileDAO();
            List<Product> list = mobileDAO.searchMobileByID(id);
            request.setAttribute("id", id);

            if (list == null) {
                request.setAttribute("error", "Not found!");
            } else {
                request.setAttribute("mobileList", list);
            }

            request.getRequestDispatcher("/view.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format!");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "An error occurred: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Fix: Gọi hàm xử lý trong GET
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles mobile search by ID";
    }
}
