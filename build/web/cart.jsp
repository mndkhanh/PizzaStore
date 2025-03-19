<%@page import="java.util.Map"%>
<%@page import="Model.DTO.Product"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Shopping Cart</title>
        <style>
            .container { width: 80%; margin: auto; }
            table { width: 100%; min-height: 70vh; border-collapse: collapse; text-align: center; }
            th, td { padding: 10px; border: 1px solid #ddd; text-align: center; }
            .btn { padding: 5px 10px; background: red; color: white; border: none; cursor: pointer; text-decoration: none; }
            .btn-quantity { padding: 3px 8px; background: #007bff; color: white; border: none; cursor: pointer; text-decoration: none; border-radius: 3px; }
            img { width: 50px; height: auto; }
        </style>
    </head>
    <body>
        <%@include file="shared/header.jsp" %>

        <div class="container" style="margin-top: 50px;">
            <h2>Your Cart</h2>
            <table>
                <tr>
                    <th>Image</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
                <%
                    HttpSession sessionCart = request.getSession();
                    Map<String, Integer> cartQuantities = (Map<String, Integer>) sessionCart.getAttribute("cartQuantities");
                    Map<String, Product> cart = (Map<String, Product>) sessionCart.getAttribute("cart");
                    double totalAmount = 0;

                    if (cart != null && !cart.isEmpty()) {
                        for (Map.Entry<String, Product> entry : cart.entrySet()) {
                            Product product = entry.getValue();
                            int quantity = cartQuantities.getOrDefault(product.getProductID(), 1); // Default 1
                            double totalPrice = product.getUnitPrice() * quantity;
                            totalAmount += totalPrice;
                %>
                <tr>
                    <td><img src="<%= product.getImg()%>" alt="Product Image"></td>
                    <td><%= product.getProductName()%></td>
                    <td>$<%= String.format("%.2f", product.getUnitPrice())%></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/decreaseInCart?id=<%= product.getProductID()%>" class="btn-quantity">-</a>
                        <%= quantity%>
                        <a href="${pageContext.request.contextPath}/user/addToCart?id=<%= product.getProductID()%>" class="btn-quantity">+</a>
                    </td>
                    <td>$<%= String.format("%.2f", totalPrice)%></td>
                    <td><a href="${pageContext.request.contextPath}/user/removeFromCart?id=<%= product.getProductID()%>" class="btn">Remove</a></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6">Your cart is empty.</td>
                </tr>
                <% }%>
            </table>
            <h3>Total Amount: $<%= String.format("%.2f", totalAmount)%></h3>
            <br>
            <a href="home">Continue Shopping</a>
        </div>

        <%@include file="shared/footer.jsp" %>
    </body>
</html>
