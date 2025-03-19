<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DTO.Product"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .result-box {
                height: 100vh;
                margin-top: 50px;
            }
            .product-img {
                max-width: 100%;
                max-height: 100%;
                border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <%@include file="shared/header.jsp" %>
        <div class="result-box container d-flex flex-column justify-content-center">
            <div class="row w-100">
                <div class="d-flex justify-content-end">
                    <a href="javascript:history.back()" class="btn btn-primary mt-3">Return</a>
                </div>
            </div>
            <div class="row">
                <h1>Product Details</h1>
            </div>

            <div class="row shadow-lg p-4 bg-white rounded">
                <%
                    Product product = (Product) request.getAttribute("product");
                    if (product != null) {
                %>
                <div class="col-md-6">
                    <img src="<%= product.getImg()%>" alt="Product Image" class="product-img">
                </div>
                <div class="col-md-6 d-flex flex-column justify-content-center">
                    <h2><%= product.getProductName()%></h2>
                    <p><strong>Product ID:</strong> <%= product.getProductID()%></p>
                    <p><strong>Category:</strong> <%= request.getAttribute("categoryName")%></p>
                    <p><strong>Supplier:</strong> <%= request.getAttribute("supplierName")%></p>
                    <p><strong>Quantity Per Unit:</strong> <%= product.getQuantityPerUnit()%></p>
                    <p><strong>Unit Price:</strong> $<%= product.getUnitPrice()%></p>
                </div>
                <% } else { %>
                <div class="alert alert-danger text-center">Product not found!</div>
                <% }%>
            </div>
        </div>
        <%@include file="shared/footer.jsp" %>
    </body>
</html>
