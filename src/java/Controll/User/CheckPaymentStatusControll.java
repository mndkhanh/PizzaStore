/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll.User;

import Model.DAO.OrderDAO;
import Model.DTO.Order;
import Model.DTO.Payment;
import Service.BankService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "CheckPaymentStatusControll", urlPatterns = {"/user/checkpaymentstatus"})
public class CheckPaymentStatusControll extends HttpServlet {

    private BankService service = new BankService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int oid = Integer.parseInt(request.getParameter("oid"));
            Payment payment = service.getPaymentByOID(oid);

            OrderDAO dao = new OrderDAO();
            String status = dao.getOrderByID(oid).getStatus();
            String isDataChangedToPaid = "NO";
            log(status);

            if (payment != null && status.equals("UNPAID")) {
                double requiredAmount = dao.getTotalCost(oid);
                double receivedAmount = payment.getAmount();
                if (receivedAmount < requiredAmount - 1) {
                    status = "FAILED";
                } else {
                    status = "PAID";
                    isDataChangedToPaid = "YES";
                }
                // ERASE CURRENT CART IN HTTP SESSION
                HttpSession session = request.getSession();
                session.setAttribute("cart", new HashMap<>());
                dao.updateOrderStatus(oid, status);
                log(status);
            }

            response.setContentType("application/json");

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("status", status);
            responseMap.put("isDataChangedToPaid", isDataChangedToPaid);

            Type objType = new TypeToken<HashMap<String, String>>() {
            }.getType();

            String json = new Gson().toJson(responseMap, objType);
            response.getWriter().write(json);
        } catch (Exception ex) {
            log(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
