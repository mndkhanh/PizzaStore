package Controll.Staff;

import Model.DAO.CategoryDAO;
import Model.DAO.ProductDAO;
import Model.DAO.SupplierDAO;
import Model.DTO.Category;
import Model.DTO.Product;
import Model.DTO.Supplier;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateProductControll", urlPatterns = {"/staff/create"})
public class CreateControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SupplierDAO supDAO = new SupplierDAO();
            CategoryDAO catDAO = new CategoryDAO();
            List<Category> categories = catDAO.getCategoryList();
            List<Supplier> suppliers = supDAO.getSupplierList();

            request.setAttribute("categories", categories);
            request.setAttribute("suppliers", suppliers);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while loading categories and suppliers.");
        }
        request.getRequestDispatcher("/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SupplierDAO supDAO = new SupplierDAO();
            CategoryDAO catDAO = new CategoryDAO();
            List<Category> categories = catDAO.getCategoryList();
            List<Supplier> suppliers = supDAO.getSupplierList();

            request.setAttribute("categories", categories);
            request.setAttribute("suppliers", suppliers);

            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String supplierID = request.getParameter("supplierID");
            String categoryID = request.getParameter("categoryID");
            String quantityPerUnit = request.getParameter("quantityPerUnit");
            String img = request.getParameter("img");

            String unitPriceString = request.getParameter("unitPrice");
            double unitPrice = 0.0;
            if (unitPriceString != null && !unitPriceString.isEmpty()) {
                try {
                    unitPrice = Double.parseDouble(unitPriceString);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid price format");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            }

            Product newProduct = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);
            ProductDAO productDAO = new ProductDAO();
            boolean success = productDAO.createProduct(newProduct);
            log(success ? "ok" : "not ok");

            if (success) {
                log("run here");
                request.setAttribute("success", "Created successfully");
                request.getRequestDispatcher("/create.jsp").forward(request, response);
            } else {
                log("run here");
                request.setAttribute("error", "Failed to create new product.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while creating the product.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            log(e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Create Product details servlet";
    }
}
