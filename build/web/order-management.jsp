<%-- 
    Document   : order-management
    Created on : Mar 21, 2025, 3:26:42 PM
    Author     : mndkh
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.DAO.AccountDAO"%>
<%@page import="Model.DTO.Account"%>
<%@page import="Model.DTO.Order"%>
<%@page import="Model.DAO.OrderDAO"%>
<%@page import="Model.DAO.ProductDAO"%>
<%@page import="Model.DTO.Product"%>
<%@page import="Model.DTO.OrderDetail"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .order-table-container {
                max-width: 500px; /* Giảm chiều rộng bảng */
                margin: auto;
            }

            .order-table th, .order-table td {
                font-size: 10px; /* Giảm chữ */
                padding: 2px; /* Thu gọn khoảng cách */
                text-align: center;
            }

            .order-table img {
                width: 30px; /* Giảm kích thước ảnh */
                height: auto;
            }

            .order-info {
                font-size: 14px; /* Giảm chữ của Order ID */
                font-weight: bold;
                margin-bottom: 5px; /* Thu gọn khoảng cách */
            }

            .order-actions {
                display: flex;
                justify-content: flex-start;
                gap: 5px; /* Thu nhỏ khoảng cách giữa các nút */
            }

            .order-actions .btn {
                font-size: 10px; /* Giảm kích thước chữ của nút */
                padding: 2px 6px; /* Thu nhỏ nút */
            }
            .row-info {
                font-size: 10px; /* Giảm kích thước chữ của nút */
            }
        </style>
    </head>
    <body>
        <%@include file="shared/header.jsp" %>
        <h2 class="text-center mt-5">Orders Management</h2>
        <div class="container mt-4">
            <%
                HashMap<Integer, ArrayList<OrderDetail>> orders = (HashMap<Integer, ArrayList<OrderDetail>>) request.getAttribute("orders");
                if (orders != null) {
                    for (Integer oid : orders.keySet()) {
                        Order orderInfo = new OrderDAO().getOrderByID(oid);
                        Account account = new AccountDAO().getAccountByID(orderInfo.getAccountID());
            %>

            <!-- Order Info -->
            <div class="order-info">Order ID: <%= oid%></div>

            <!-- Order Actions -->
            <div class="order-actions mb-2">
                <a href="${pageContext.request.contextPath}/staff/updateorder?oid=<%= oid%>" class="btn btn-primary">Update Order</a>
                <a href="${pageContext.request.contextPath}/staff/vieworder?oid=<%= oid%>" class="btn btn-secondary">View Order</a>
            </div>

            <!-- Order Table -->
            <table class="table table-striped text-center order-table">
                <thead class="table-dark">
                    <tr>
                        <th>Image</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<OrderDetail> orderDetails = orders.get(oid);
                        if (!orderDetails.isEmpty()) {
                            for (OrderDetail o : orderDetails) {
                                Product product = new ProductDAO().getProductByID(o.getProductID());
                    %>
                    <tr>
                        <td><img src="<%= product.getImg()%>" alt="Product"></td>
                        <td><%= product.getProductName()%></td>
                        <td>$<fmt:formatNumber value="<%= product.getUnitPrice()%>" type="number" minFractionDigits="2"/></td>
                        <td><%= o.getQuantity()%></td>
                        <td>$<fmt:formatNumber value="<%= product.getUnitPrice() * o.getQuantity()%>" type="number" minFractionDigits="2"/></td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr>
                        <td colspan="5">No past orders found.</td>
                    </tr>
                    <% }%>  
                </tbody>
            </table>

            <!-- Account & Shipping Info -->
            <div class="row-info mb-5 d-flex justify-content-between">
                <p><strong>Account ID:</strong> <%= account.getAccID()%></p>
                <p><strong>Name:</strong> <%= account.getFullName()%></p>
                <p><strong>Deliver to:</strong> <%= orderInfo.getShipAddress()%></p>
                <p><strong>Total Cost:</strong> <%= new OrderDAO().getTotalCost(oid)%></p>
            </div>

            <% }
                }%>
        </div>

        <div style="margin-top: 63vh;">
            <%@include file="shared/footer.jsp" %>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
