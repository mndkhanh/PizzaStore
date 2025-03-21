<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product</title>
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
                <h1>Create New Product</h1>
            </div>
            <div class="row shadow-lg p-4 bg-white rounded">
                <form action="<%= request.getContextPath()%>/staff/create" method="post">
                    <div class="row">
                        <!-- Image Section -->
                        <div class="col-md-6 d-flex flex-column align-items-center">
                            <img src="" alt="Product Image" class="product-img mb-4" id="productImgPreview">
                            <div class="form-group w-100">
                                <label for="img">Upload Image URL:</label>
                                <input type="text" name="img" class="form-control" id="imgPart" required>
                            </div>
                        </div>

                        <!-- Form Section -->
                        <div class="col-md-6">
                            <div class="form-group mb-3">
                                <label for="productID">Product ID:</label>
                                <input type="text" name="productID" class="form-control" id="productID" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="productName">Product Name:</label>
                                <input type="text" name="productName" class="form-control" id="productName" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="categoryID">Category:</label>
                                <select name="categoryID" class="form-control">
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.getCategoryID()}">${category.getCategoryName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="supplierID">Supplier:</label>
                                <select name="supplierID" class="form-control">
                                    <c:forEach var="supplier" items="${suppliers}">
                                        <option value="${supplier.getSupplierID()}">${supplier.getCompanyName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="quantityPerUnit">Quantity Per Unit:</label>
                                <input type="text" name="quantityPerUnit" class="form-control" id="quantityPerUnit" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="unitPrice">Unit Price:</label>
                                <input type="number" step="0.01" name="unitPrice" class="form-control" id="unitPrice" required>
                            </div>
                            <button type="submit" class="btn btn-success mt-4">Create Product</button>
                        </div>
                    </div>
                    <c:if test="${not empty requestScope.success}">
                        <p class="text-success">${requestScope.success}</p>
                    </c:if>
                </form>
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