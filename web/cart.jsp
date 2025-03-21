<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="Model.DAO.ProductDAO"%>
<%@page import="Model.DTO.OrderDetail"%>
<%@page import="java.util.Map"%>
<%@page import="Model.DTO.Product"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Shopping Cart</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .btn-remove { background: red; color: white; }
            .btn-quantity { background: #007bff; color: white; margin: 0 5px; }
        </style>
    </head>
    <body>

        <!-- Header -->
        <%@include file="shared/header.jsp" %>

        <div class="container mt-4">
            <h2 class="text-center">Your Cart</h2>
            <table class="table table-striped text-center">
                <thead class="table-dark">
                    <tr>
                        <th>Image</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) request.getAttribute("cart");
                        double totalAmount = (double) request.getAttribute("totalAmount");

                        if (cart != null && !cart.isEmpty()) {
                            for (OrderDetail detail : cart.values()) {
                                Product product = new ProductDAO().getProductByID(detail.getProductID());
                                int quantity = detail.getQuantity();
                                double totalPrice = product.getUnitPrice() * quantity;
                    %>
                    <tr>
                        <td><img src="<%= product.getImg()%>" alt="Product" width="50"></td>
                        <td><%= product.getProductName()%></td>
                        <td>$<fmt:formatNumber value="<%= product.getUnitPrice()%>" type="number" minFractionDigits="2"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/user/decreaseInCart?pid=<%= product.getProductID()%>" class="btn btn-quantity">-</a>
                            <%= quantity%>
                            <a href="${pageContext.request.contextPath}/user/addToCart?pid=<%= product.getProductID()%>" class="btn btn-quantity">+</a>
                        </td>
                        <td>$<fmt:formatNumber value="<%= totalPrice%>" type="number" minFractionDigits="2"/></td>
                        <td><a href="${pageContext.request.contextPath}/user/removeFromCart?pid=<%= product.getProductID()%>" class="btn btn-remove btn-sm">Remove</a></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">Your cart is empty.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>

            <div class="d-flex justify-content-between align-items-center mt-3">
                <div>
                    <h4>Total Amount: $<fmt:formatNumber value="<%= totalAmount%>" type="number" minFractionDigits="2"/></h4>
                    <a href="${pageContext.request.contextPath}/home" class="pe-4">Continue Shopping</a>
                </div>
                <form action="${pageContext.request.contextPath}/user/order" method="post" class="d-flex align-items-center g-4">
                    <div class="d-flex align-items-center me-2">
                        <span class="me-2">Ship to:</span>
                        <input class="form-control w-auto border-dark" style="min-width: 300px;" type="text" value="${shipAddress}" name="shipAddress" placeholder="Address..." aria-label="Shipping Address">
                    </div>
                    <input type="submit" value="Order" class="btn btn-primary">
                </form>
            </div>
        </div>

        <h2 class="text-center mt-5">Order History</h2>
        <div class="container mt-4">
            <%
                List<HashMap<String, OrderDetail>> historyOrders = (List<HashMap<String, OrderDetail>>) request.getAttribute("historyOrders");
                if (historyOrders != null) {
                    for (HashMap<String, OrderDetail> historyOrder : historyOrders) {
            %>
            <table class="table table-striped text-center">
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
                        if (!historyOrder.isEmpty()) {
                            for (OrderDetail order : historyOrder.values()) {
                                Product product = new ProductDAO().getProductByID(order.getProductID());
                    %>
                    <tr>
                        <td><img src="<%= product.getImg()%>" alt="Product" width="50"></td>
                        <td><%= product.getProductName()%></td>
                        <td>$<fmt:formatNumber value="<%= product.getUnitPrice()%>" type="number" minFractionDigits="2"/></td>
                        <td><%= order.getQuantity()%></td>
                        <td>$<fmt:formatNumber value="<%= product.getUnitPrice() * order.getQuantity()%>" type="number" minFractionDigits="2"/></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5">No past orders found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% }
                }%>
        </div>

        <div style="margin-top: 63vh;">
            <%@include file="shared/footer.jsp" %>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
