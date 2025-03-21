/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.Auth;

import Model.DAO.AccountDAO;
import Model.DAO.OrderDAO;
import Model.DAO.OrderDetailDAO;
import Model.DTO.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mndkh
 */
@WebServlet(name = "LoginControll", urlPatterns = {"/login"})
public class LoginControll extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accID = request.getParameter("accID");
        String password = request.getParameter("password");
        try {
            AccountDAO dao = new AccountDAO();
            Account acc = dao.login(accID, password);
            if (acc != null) {
                HttpSession session = request.getSession();
                session.setAttribute("acc", acc);
                // acc is user then load the unpaid order up to "cart" in session scope
                if (!acc.isIsStaff()) {
                    session.setAttribute("cart", new OrderDAO().getOrderDetailsOfUnpaidOrderOfAccountID(accID));
                }
                response.sendRedirect("view");
            } else {
                request.setAttribute("error", "user or password not correct");
                request.setAttribute("accID", accID);
                request.setAttribute("password", password);
                request.getRequestDispatcher("auth/login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            log(ex.getMessage());
            request.setAttribute("error", "Internal server error. Try again!");
            request.setAttribute("accID", accID);
            request.setAttribute("password", password);
            request.getRequestDispatcher("auth/login.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
