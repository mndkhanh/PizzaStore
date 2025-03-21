package Controll.Staff;

import Model.DAO.CategoryDAO;
import Model.DAO.ProductDAO;
import Model.DAO.SupplierDAO;
import Model.DTO.Product;
import Model.DTO.Category;
import Model.DTO.Supplier;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateControll", urlPatterns = {"/staff/update"})
public class UpdateControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productID = request.getParameter("pid");
            ProductDAO productDAO = new ProductDAO();
            SupplierDAO supDAO = new SupplierDAO();
            CategoryDAO catDAO = new CategoryDAO();
            Product product = productDAO.getProductByID(productID);

            if (product != null) {
                List<Category> categories = catDAO.getCategoryList();
                List<Supplier> suppliers = supDAO.getSupplierList();

                request.setAttribute("product", product);
                request.setAttribute("categories", categories);
                request.setAttribute("suppliers", suppliers);
                request.getRequestDispatcher("/update.jsp?pid=" + productID).forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String supplierID = request.getParameter("supplierID");
            String categoryID = request.getParameter("categoryID");
            String quantityPerUnit = request.getParameter("quantityPerUnit");
            double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
            String img = request.getParameter("img");

            Product updatedProduct = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);

            ProductDAO productDAO = new ProductDAO();
            SupplierDAO supDAO = new SupplierDAO();
            CategoryDAO catDAO = new CategoryDAO();

            Product product = productDAO.getProductByID(productID);
            List<Category> categories = catDAO.getCategoryList();
            List<Supplier> suppliers = supDAO.getSupplierList();
            
            request.setAttribute("product", product);
            request.setAttribute("categories", categories);
            request.setAttribute("suppliers", suppliers);

            boolean success = productDAO.updateProduct(updatedProduct);
            if (success) {
                request.setAttribute("success", "Updated successfully");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "An error occurred while updating the product.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while updating the product.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
