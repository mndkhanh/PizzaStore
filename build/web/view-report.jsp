<%@page import="Model.DAO.OrderDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="Model.DTO.OrderDetail"%>
<%@page import="Model.DTO.Order"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order Report</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <%@include file="shared/header.jsp" %>

        <div class="container mt-4">
            <h2 class="text-center">Order Report</h2>

            <form action="${pageContext.request.contextPath}/staff/viewreport" method="get" class="d-flex justify-content-end align-items-center gap-4 mt-5 mb-3">
                <div>
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate" value="${startDate}" class="form-control d-inline-block w-auto">
                </div>
                <div>
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate" value="${endDate}" class="form-control d-inline-block w-auto">
                </div>
                <button type="submit" class="btn btn-secondary">Create</button>
            </form>

            <table class="table table-striped text-center">
                <thead class="table-dark">
                    <tr>
                        <th>Order ID</th>
                        <th>Account Name</th>
                        <th>Deliver To</th>
                        <th>Order Date</th>
                        <th>Shipped Date</th>
                        <th>Total Cost</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Order> orderList = (List<Order>) request.getAttribute("orders");
                        if (orderList != null && !orderList.isEmpty()) {
                            OrderDAO dao = new OrderDAO();
                            for (Order order : orderList) {
                    %>
                    <tr>
                        <td><%= order.getOrderID()%></td>
                        <td><%= order.getAccountID()%></td>
                        <td><%= order.getShipAddress()%></td>
                        <td><fmt:formatDate value="<%= order.getOrderDate()%>" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="<%= order.getShippedDate()%>" pattern="yyyy-MM-dd"/></td>
                        <td>$<fmt:formatNumber value="<%= dao.getTotalCost(order.getOrderID())%>" type="number" minFractionDigits="2"/></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">No orders found.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <h4 class="text-end mt-3">Total Revenue: $<fmt:formatNumber value="${totalRevenue}" type="number" minFractionDigits="2"/></h4>
        </div>

        <div style="margin-top: 600px">
            <%@include file="shared/footer.jsp" %>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
