<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Pizza Store</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            /* Custom styles */
            .navbar-brand img {
                height: 40px; /* Adjust logo height */
            }
            .name-search-bar {
                max-width: 300px; /* Limit search bar width */
            }
            .id-search-bar {
                max-width: 200px;    
            }
            .price-search-bar {
                max-width: 200px;
                input {
                    max-width: 100px;
                }
            }
            .user-greeting {
                margin-right: 10px; /* Spacing for user greeting */
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <header class="bg-dark text-white">
            <div class="container">
                <nav class="navbar navbar-expand-lg navbar-dark">
                    <!-- Logo -->
                    <a class="navbar-brand" href="home">PAZI STORE</a>

                    <!-- Navbar items -->
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <!-- Search Bar -->
                        <c:if test="${sessionScope.acc.isStaff == true}">
                            <form action="${pageContext.request.contextPath}/staff/idSearch" method="post" class="d-flex id-search-bar mx-auto">
                                <input class="form-control me-2 w-100" type="search" name="pid" value="${pid}" placeholder="Product ID" aria-label="Search">
                                <button class="btn btn-outline-light" type="submit">
                                    <i class="fas fa-search"></i>
                                </button>
                            </form>
                        </c:if>


                        <form action="${pageContext.request.contextPath}/nameSearch" method="post" class="d-flex name-search-bar mx-auto">
                            <input class="form-control me-2 w-100" type="search" name="searchValue" value="${searchValue}" placeholder="Search by name" aria-label="Search">
                            <button class="btn btn-outline-light" type="submit">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/priceSearch" method="post" class="d-flex price-search-bar mx-auto">
                            <input class="form-control me-2" type="text" name="min" value="${min}" placeholder="Min" >
                            <input class="form-control me-2" type="text" name="max" value="${max}" placeholder="Max" >
                            <button class="btn btn-outline-light" type="submit">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>

                        <!-- Account Greeting, Cart, and Login/Logout -->
                        <ul class="navbar-nav ms-auto">
                            <c:if test="${sessionScope.acc.isStaff != true}">
                                <li>
                                    <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
                                </li>
                                <li>
                                    <a class="nav-link" href="${pageContext.request.contextPath}/view">Products</a>
                                </li>
                            </c:if>
                                
                                <c:if test="${sessionScope.acc.isStaff == true}">
                                <li>
                                    <a class="nav-link" href="${pageContext.request.contextPath}/view">Dashboard</a>
                                </li>
                            </c:if>
                            <c:choose>
                                <c:when test="${not empty sessionScope.acc}">
                                    <li class="nav-item user-greeting">
                                        <span class="nav-link">Hello, ${sessionScope.acc.isStaff ? "Staff" : "User"} ${sessionScope.acc.fullName}</span>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                                    </li>

                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${pageContext.request.contextPath}/auth/login.jsp">Login</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${sessionScope.acc.isStaff != true}">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/user/viewcart">
                                        <i class="fas fa-shopping-cart"></i> Cart
                                        <span class="badge bg-danger">${sessionScope.cartSize}</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.acc.isStaff == true}">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/staff/create">
                                        Add Product
                                    </a>
                                </li>
                            </c:if>

                        </ul>
                    </div>
                </nav>
            </div>
        </header>


        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    </body>
</html>