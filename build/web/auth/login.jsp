<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
    </head>
    <body class="bg-light">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-4">
                    <div class="card shadow">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-4">Login</h3>

                            <!-- Error Message -->
                            <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                            <% }%>

                            <!-- Login Form -->
                            <form action="${pageContext.request.contextPath}/login" method="POST">
                                <div class="mb-3">
                                    <label for="accID" class="form-label">Account ID</label>
                                    <input type="text" class="form-control" value="${accID}" id="accID" name="accID" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" value="${password}" id="password" name="password" required>
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Login</button>
                            </form>

                            <!-- Sign Up Link -->
                            <p class="text-center mt-3">
                                Don't have an account? <a href="${pageContext.request.contextPath}/auth/signup.jsp">Sign Up</a>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    </body>
</html>
