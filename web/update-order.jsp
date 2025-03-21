<%@page import="Model.DAO.OrderDAO"%>
<%@page import="Model.DTO.Order"%>
<%@page import="Model.DAO.ProductDAO"%>
<%@page import="Model.DTO.Product"%>
<%@page import="Model.DTO.OrderDetail"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Order</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .btn-save { background: #28a745; color: white; }
            .btn-remove { background: red; color: white; }
        </style>
    </head>
    <body>

        <%@include file="shared/header.jsp" %>
        <div class="container mt-4">
            <h2 class="text-center">Update Order</h2>
            <form action="${pageContext.request.contextPath}/staff/updateorder" method="post">
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
                            HashMap<String, OrderDetail> cart = (HashMap<String, OrderDetail>) request.getAttribute("order");
                            if (cart != null && !cart.isEmpty()) {
                                for (OrderDetail detail : cart.values()) {
                                    Product product = new ProductDAO().getProductByID(detail.getProductID());
                                    int quantity = detail.getQuantity();
                                    double totalPrice = product.getUnitPrice() * quantity;
                                    Order order = new OrderDAO().getOrderByID(detail.getOrderID());
                        %>
                        <tr>
                            <td><img src="<%= product.getImg()%>" alt="Product" width="50"></td>
                            <td><%= product.getProductName()%></td>
                            <td>$<%= String.format("%.2f", product.getUnitPrice())%></td>
                            <td><%= quantity%></td>
                            <td>$<%= String.format("%.2f", totalPrice)%></td>
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
                <div class="d-flex justify-content-between align-items-center">
                    <div>   
                        <h5>Freight $${freight}</h5>
                        <h4 class="text-primary">Total Cost $${totalCost}</h4>
                        <p class="text-primary">Ship to:</p>
                        <input class="form-control w-auto border-dark" style="min-width: 300px;" type="text" value="${shipAddress}" name="shipAddress" placeholder="Address..." aria-label="Shipping Address">
                        <p class="text-primary">Shipped Date:</p>
                        <input class="form-control w-auto border-dark" style="min-width: 300px;" type="date" value="${shippedDate}" name="shippedDate" placeholder="Shipped Date..." aria-label="Shipped Date">
                    </div>
                    <div>

                        <div>
                            <h5>Status:</h5>
                            <select name="status" class="form-select" style="width: 150px;">
                                <option value="UNPAID" ${status eq 'UNPAID' ? 'selected' : ''}>UNPAID</option>
                                <option value="PAID" ${status eq 'PAID' ? 'selected' : ''}>PAID</option>
                                <option value="SUCCESS" ${status eq 'SUCCESS' ? 'selected' : ''}>SUCCESS</option>
                                <option value="FAILED" ${status eq 'FAILED' ? 'selected' : ''}>FAILED</option>
                            </select>
                        </div>
                        <input name="oid" value="${param.oid}" hidden>
                    </div>
                </div>
                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-save btn-primary">Save Changes</button>
                </div>
                <c:if test="${not empty success}">
                    <p class="text-success">${success}</p>
                </c:if>
            </form>
        </div>
        <div style="margin-top: 500px">
            <%@include file="shared/footer.jsp" %>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
