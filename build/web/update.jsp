<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DTO.Product, Model.DTO.Category, Model.DTO.Supplier"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            .result-box {
                height: 100vh;
                margin-top: 50px;
            }
            .product-img {
                max-width: 100%;
                max-height: 300px;
                border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <%@include file="shared/header.jsp" %>
        <div class="result-box container d-flex flex-column justify-content-center">
            <div class="row w-100">
                <div class="d-flex justify-content-end">
                    <a href="${pageContext.request.contextPath}/view" class="btn btn-primary mt-3">Return</a>
                </div>
            </div>
            <div class="row">
                <h1>Update Product</h1>
            </div>

            <div class="row shadow-lg p-4 bg-white rounded">
                <%
                    Product product = (Product) request.getAttribute("product");
                    if (product != null) {
                %>
                <form action="<%= request.getContextPath()%>/staff/update" method="post">
                    <div class="row">
                        <!-- Image Section -->
                        <div class="col-md-6 d-flex flex-column align-items-center">
                            <img src="<%= product.getImg()%>" alt="Product Image" class="product-img mb-4" id="productImgPreview">
                            <div class="form-group w-100">
                                <label for="img">Upload New Image URL:</label>
                                <input type="text" name="img" class="form-control" id="imgPart" value="<%= product.getImg()%>" required>
                            </div>
                        </div>

                        <!-- Form Section -->
                        <div class="col-md-6">
                            <div class="form-group mb-3">
                                <label for="productName">Product Name:</label>
                                <input type="text" name="productName" class="form-control" id="productName" value="<%= product.getProductName()%>" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="categoryID">Category:</label>
                                <select name="categoryID" class="form-control">
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.getCategoryID()}" <c:if test="${category.getCategoryID() eq product.getCategoryID()}">selected</c:if>>
                                            ${category.getCategoryName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="supplierID">Supplier:</label>
                                <select name="supplierID" class="form-control">
                                    <c:forEach var="supplier" items="${suppliers}">
                                        <option value="${supplier.getSupplierID()}" <c:if test="${supplier.getSupplierID() eq product.getSupplierID()}">selected</c:if>>
                                            ${supplier.getSupplierName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="quantityPerUnit">Quantity Per Unit:</label>
                                <input type="text" name="quantityPerUnit" class="form-control" id="quantityPerUnit" value="<%= product.getQuantityPerUnit()%>" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="unitPrice">Unit Price:</label>
                                <input type="number" step="0.01" name="unitPrice" class="form-control" id="unitPrice" value="<%= product.getUnitPrice()%>" required>
                            </div>
                            <input type="hidden" name="productID" value="<%= product.getProductID()%>">
                            <button type="submit" class="btn btn-success mt-4">Update Product</button>
                        </div>
                    </div>

                    <c:if test="${not empty requestScope.success}">
                        <p class="text-success">${requestScope.success}</p>
                    </c:if>
                </form>
                <% } else { %>
                <div class="alert alert-danger text-center">Product not found!</div>
                <% }%>
            </div>
        </div>
        <%@include file="shared/footer.jsp" %>

        <script>
            // Update the image preview when URL changes
            document.getElementById("imgPart").addEventListener("input", (e) => {
                document.getElementById("productImgPreview").src = e.target.value;
            });
        </script>
    </body>
</html>