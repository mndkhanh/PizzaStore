package Controll.Auth;

import Model.DAO.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import Model.DTO.Account;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpControll extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String accID = request.getParameter("accID");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (fullName.isEmpty() || accID.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();
        try {
            if (dao.isAccExist(accID)) {
                request.setAttribute("error", "Account ID already exists!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
                dispatcher.forward(request, response);
                return;
            }
            Account newAcc = new Account(accID, password, fullName, false);
            boolean isCreated = dao.createAccount(newAcc);

            if (isCreated) {
                response.sendRedirect("auth/signup.jsp?success=Account created successfully! Please login.");
            } else {
                request.setAttribute("error", "Failed to create account! Try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("auth/signup.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception ex) {
        }

    }
}
