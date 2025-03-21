<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Pizza Menu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>
    <body>
        <div class="container my-5">
            <h2 class="text-center mb-4">Welcome to PAZI Pizza Shop</h2>
            <c:if test="${sessionScope.acc.isStaff == true}">
                <div class="d-flex justify-content-end mb-5">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/staff/create">
                        Add Product
                    </a>
                </div>
            </c:if>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">

                <c:if test="${empty productList}">
                    <p class="col text-center text-primary">No products available</p>
                </c:if>
                <c:forEach var="product" items="${productList}">
                    <div class="col">
                        <div class="card h-100">
                            <img src="${product.img}" class="card-img-top" alt="${product.productName}" style="height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title text-primary">${product.productName}</h5>
                                <p class="card-text">${product.quantityPerUnit}</p>
                                <p class="text-danger fw-bold">$${product.unitPrice}</p>

                                <!-- Always show "View Details" button -->
                                <a href="${pageContext.request.contextPath}/detail?pid=${product.productID}" class="btn btn-primary">View Details</a>

                                <!-- Check if the user is staff (can delete or update) -->
                                <c:if test="${sessionScope.acc != null && sessionScope.acc.isStaff}">
                                    <a href="${pageContext.request.contextPath}/staff/update?pid=${product.productID}" class="btn btn-primary">Update</a>
                                    <a href="#" onclick="return confirmDelete('${pageContext.request.contextPath}/staff/delete?pid=${product.productID}');" class="btn btn-danger">Delete</a>
                                </c:if>

                                <!-- If the user is not staff, show Add to Cart button -->
                                <c:if test="${sessionScope.acc == null || !sessionScope.acc.isStaff}">
                                    <a href="${pageContext.request.contextPath}/user/addToCart?pid=${product.productID}" class="btn btn-success">Add to Cart</a>
                                </c:if>
                            </div>
                        </div>  
                    </div>
                </c:forEach>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

        <script>
                                        function confirmDelete(deleteUrl) {
                                            var result = confirm("Are you sure you want to delete this product?");
                                            if (result) {
                                                window.location.href = deleteUrl;
                                            }
                                            return false;
                                        }
        </script>
    </body>
</html>