<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Error Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f8d7da;
            }
            .error-container {
                text-align: center;
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <div class="error-container">
            <h1 class="text-danger">Oops! Something went wrong</h1>
            <p>${requestScope.error != null ? requestScope.error : "An unexpected error has occurred."}</p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-danger">Go to Home</a>
        </div>
    </body>
</html>
