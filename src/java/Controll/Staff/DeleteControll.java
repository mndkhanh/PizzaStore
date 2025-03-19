package Controll.Staff;

import Model.DAO.MobileDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteControll", urlPatterns = {"/staff/delete"})
public class DeleteControll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mobileID = request.getParameter("id");

        if (mobileID != null && !mobileID.isEmpty()) {
            try {
                MobileDAO mobileDAO = new MobileDAO();

                boolean isDeleted = mobileDAO.deleteMobile(mobileID);

                if (isDeleted) {
                    request.getRequestDispatcher("/view").forward(request, response);
                } else {
                    request.setAttribute("error", "Error deleting mobile.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("error", "Error deleting mobile: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid Mobile ID.");
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
        return "Servlet to delete a mobile.";
    }
}
