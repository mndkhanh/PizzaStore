
import Model.DAO.CategoryDAO;
import Model.DAO.ProductDAO;
import Model.DAO.SupplierDAO;
import Model.DTO.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String pid = request.getParameter("pid");

            ProductDAO productDAO = new ProductDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            SupplierDAO supplierDAO = new SupplierDAO();

            Product product = productDAO.getProductByID(pid);

            if (product != null) {
                String categoryName = categoryDAO.getCategoryNameByID(product.getCategoryID());
                String supplierName = supplierDAO.getSupplierNameByID(product.getSupplierID());

                request.setAttribute("product", product);
                request.setAttribute("categoryName", categoryName);
                request.setAttribute("supplierName", supplierName);
            } else {
                request.setAttribute("error", "Product not found!");
            }

            request.getRequestDispatcher("/detail.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "An error occurred: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
