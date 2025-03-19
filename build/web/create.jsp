<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Mobile</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <%@include file="shared/header.jsp" %>
        <div class="container mt-5">
            <h1>Create New Mobile</h1>
            <form action="<%= request.getContextPath()%>/staff/create" method="post">
                <div class="mb-3">
                    <label for="mobileID" class="form-label">Mobile ID:</label>
                    <input type="text" name="mobileID" class="form-control" id="mobileID" required>
                </div>
                <div class="mb-3">
                    <label for="mobileName" class="form-label">Mobile Name:</label>
                    <input type="text" name="mobileName" class="form-control" id="mobileName" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description:</label>
                    <textarea name="description" class="form-control" id="description" rows="3" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price:</label>
                    <input type="number" step="0.01" name="price" class="form-control" id="price" required>
                </div>
                <div class="mb-3">
                    <label for="productionDate" class="form-label">Production Date (YYYY-MM-DD):</label>
                    <input type="date" name="productionDate" class="form-control" id="productionDate" required>
                </div>
                <div class="mb-3">
                    <label for="quantity" class="form-label">Quantity:</label>
                    <input type="number" name="quantity" class="form-control" id="quantity" required>
                </div>
                <div class="mb-3">
                    <label for="onSale" class="form-label">On Sale:</label>
                    <select name="onSale" class="form-control" id="onSale">
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="img" class="form-label">Image URL:</label>
                    <input type="text" name="img" class="form-control" id="img" required>
                </div>
                <button type="submit" class="btn btn-primary">Create Mobile</button>
                <c:if test="${not empty param.success}">
                    <p class="text-success">${param.success}</p>
                </c:if>
            </form>

        </div>
        <%@include file="shared/footer.jsp" %>
    </body>
</html>