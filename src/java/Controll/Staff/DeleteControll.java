package Controll.Staff;

import Model.DAO.ProductDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteControll", urlPatterns = {"/staff/delete"})
public class DeleteControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productID = request.getParameter("pid");

        if (productID != null && !productID.isEmpty()) {
            try {
                ProductDAO productDAO = new ProductDAO();
                boolean isDeleted = productDAO.deleteProductByID(productID);

                if (isDeleted) {
                    response.sendRedirect(request.getContextPath() + "/view");
                } else {
                    request.setAttribute("error", "Error deleting product.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("error", "Error deleting product: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid Product ID.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to delete a product.";
    }
}
